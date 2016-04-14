package net.opfietse.zrmiles
package db
package motorcycles

import net.opfietse.zrmiles.db.riders.RidersActor.GetRider
import net.opfietse.zrmiles.db.riders.SlickRidersActorProvider
import nl.wobble.zrmiles.common

import scala.concurrent.Future
import scala.collection.JavaConversions._

import akka.actor._
import akka.pattern.ask

import slick.driver.MySQLDriver.api._

import ZrMilesSchema._
import model._
import net.opfietse.zrmiles.util.{ ActorAskPattern, ActorRefFactorySupport }
import nl.wobble.zrmiles.dao._

object MotorcyclesActor {
  def props = Props[MotorcyclesActor]
  def name = "motorcycles-actor"

  sealed trait Command
  case object GetAllMotorcycles extends Command
  case object GetAllMotorcyclesWithRiders extends Command
  case class GetMotorcyclesForRiderId(riderId: Int) extends Command
  case class GetAddBikeStuff(riderId: Int) extends Command
  case class AddBike(riderId: Int, make: String, model: String, year: Option[Int], distanceUnit: Int)
}
class MotorcyclesActor extends Actor with ActorLogging
    with ActorAskPattern
    with ActorRefFactorySupport
    with SlickRidersActorProvider {
  import MotorcyclesActor._

  implicit val executionContext = context.dispatcher
  val actorRefFactory = context.system

  def receive: Receive = {
    //    case GetAllMotorcycles =>
    //      getAllMotorcyclesWithRiders
    //      val bikes = getAllMotorcycles
    //      sender ! bikes
    case GetAllMotorcyclesWithRiders =>
      val bikes = getAllMotorcyclesWithRiders
      sender ! bikes
    case GetMotorcyclesForRiderId(riderId) =>
      val bikes = getMotorcyclesForRider(riderId)
      sender ! bikes

    case GetAddBikeStuff(id) =>
      val stuff = getStuff(id)
      println("Stuff" + stuff)
      sender ! stuff
  }

  def getAllMotorcycles: Future[Seq[Motorcycle]] = {
    log.info("Run GetAllMotorcycles ++++++++++++")
    val db = Database.forConfig("mysql")

    try {
      val dbMotorcycles = for (m <- motorcycles) yield m
      val q = dbMotorcycles.result
      val f: Future[Seq[Motorcycle]] = db.run(q)

      val res = for (names <- f) yield names
      res
    } finally db.close
  }

  def getAllMotorcyclesWithRiders: Future[Seq[MotorcycleWithRider]] = {
    log.info("Run GetAllMotorcyclesWithRiders ++++++++++++")
    val db = Database.forConfig("mysql")

    try {
      val joinQuery: Query[(Rep[Int], Rep[Int], Rep[String], Rep[String], Rep[Option[Int]], Rep[Int], Rep[String], Rep[String]), (Int, Int, String, String, Option[Int], Int, String, String), Seq] = for {
        m <- motorcycles
        r <- m.rider
      } yield (m.id, m.riderId, m.make, m.model, m.year, m.distanceUnit, r.firstName, r.lastName)

      val q = joinQuery.result
      val f: Future[Seq[(Int, Int, String, String, Option[Int], Int, String, String)]] = db.run(q)

      f.map(s => makeM(s))
    } finally db.close
  }

  def getStuff(riderId: Int): Future[(Rider, List[Motorcycle])] = {
    val m = getMotorcyclesForRider(riderId)
    val rFuture = (ridersActor ? GetRider(riderId)).mapTo[Rider]
    for (
      r <- rFuture
    ) yield (r, m)
  }

  def getMotorcyclesForRider(riderId: Int): List[Motorcycle] = {
    log.info("Get motorcycles for rider with id {}", riderId)

    val javaBikes: java.util.ArrayList[nl.wobble.zrmiles.common.Motorcycle] = DaoFactory.getInstance().getMotorcyclesDao(new EnvDbInfo).findByRider(riderId, null).asInstanceOf[java.util.ArrayList[nl.wobble.zrmiles.common.Motorcycle]]

    val bikes: List[Motorcycle] = javaBikes.toList.map(bike => toScalaBike(bike))
    bikes
  }

  def addMotorcycle(
    riderId: Int,
    make: String,
    model: String,
    year: Option[Int],
    distanceUnit: Int
  ): Motorcycle = {

    require(!make.isEmpty, "Make cannot be empty")
    require(!model.isEmpty, "Model name cannot be empty")
    //require(!distanceUnit.isEmpty, "Distance unit cannot be empty")

    val bikesDao = DaoFactory.getInstance().getMotorcyclesDao(new EnvDbInfo)
    val newKey = bikesDao.
      add(toJavaBike(Motorcycle(0, riderId, make, model, year, distanceUnit)))

    toScalaBike(bikesDao.findById(newKey))
  }

  def toScalaBike(bike: nl.wobble.zrmiles.common.Motorcycle): Motorcycle = {
    val year: Option[Int] = /*if (bike.getYear == null) None else*/ Some(bike.getYear)
    Motorcycle(bike.getId, bike.getRiderId, bike.getMake, bike.getModel, year, bike.getDistanceUnit)
  }

  def toJavaBike(bike: Motorcycle): nl.wobble.zrmiles.common.Motorcycle = {
    val owner = ""
    new nl.wobble.zrmiles.common.Motorcycle(null, bike.riderId, bike.make, bike.model, bike.year.getOrElse(0),
      bike.distanceUnit, owner)
  }

  def makeM(s: Seq[(Int, Int, String, String, Option[Int], Int, String, String)]): Seq[MotorcycleWithRider] = {
    val res = for ((id: Int, riderId: Int, make: String, model: String, year: Option[Int], distanceUnit: Int, firstName: String, lastName: String) <- s) yield MotorcycleWithRider(id, riderId, make, model, year, distanceUnit, firstName, lastName)
    res
  }
}

trait MotorcyclesActorProvider {
  def motorcyclesActor: ActorRef
}

trait SlickMotorcyclesActorProvider extends MotorcyclesActorProvider with ActorRefFactorySupport {
  lazy val motorcyclesActor = actorRefFactory.actorOf(MotorcyclesActor.props, MotorcyclesActor.name)
}
