package net.opfietse.zrmiles
package db
package motorcycles

import scala.concurrent.Future
import akka.actor._

import slick.driver.MySQLDriver.api._

import ZrMilesSchema._
import model._
import util.ActorRefFactorySupport

object MotorcyclesActor {
  def props = Props[MotorcyclesActor]
  def name = "motorcycles-actor"

  sealed trait Command
  case object GetAllMotorcycles extends Command
  case object GetAllMotorcyclesWithRiders extends Command
  case class GetMotorcyclesForRiderId(riderId: Int) extends Command
}

class MotorcyclesActor extends Actor with ActorLogging {
  import MotorcyclesActor._

  implicit val executionContext = context.dispatcher

  def receive: Receive = {
    case GetAllMotorcycles =>
      getAllMotorcyclesWithRiders
      val bikes = getAllMotorcycles
      sender ! bikes
    case GetAllMotorcyclesWithRiders =>
      val bikes = getAllMotorcyclesWithRiders
      sender ! bikes
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

  //  def getAllMotorcyclesWithRiders: Future[Seq[(Int, Int, String, String, Int, Int, String, String)]] = {
  def getAllMotorcyclesWithRiders: Future[Seq[MotorcycleWithRider]] = {
    //  def getAllMotorcyclesWithRiders = {
    log.info("Run GetAllMotorcyclesWithRiders ++++++++++++")
    val db = Database.forConfig("mysql")

    try {
      //      val joinQuery: Query[(Rep[String], Rep[String]), (String, String), Seq] = for {
      //        c <- coffees if c.price > 9.0
      //        s <- c.supplier
      //      } yield (c.name, s.name)

      //      val joinQuery: Query[(Rep[Int], Rep[Int], Rep[String], Rep[String], Rep[Int], Rep[Int], Rep[String], Rep[String]), (Int, Int, String, String, Int, Int, String, String), Seq] = for {
      val joinQuery: Query[(Rep[Int], Rep[Int], Rep[String], Rep[String], Rep[Int], Rep[Int], Rep[String], Rep[String]), (Int, Int, String, String, Int, Int, String, String), Seq] = for {
        m <- motorcycles
        r <- m.rider
      } yield (m.id, m.riderId, m.make, m.model, m.year, m.distanceUnit, r.firstName, r.lastName)

      //      val dbMotorcycles = for (m <- motorcycles) yield m
      val q = joinQuery.result
      //      val f: Future[Seq[GetAllMotorcyclesWithRiders]] = db.run(q)
      val f: Future[Seq[(Int, Int, String, String, Int, Int, String, String)]] = db.run(q)

      //      f.map(println)
      //      val res = for ((id: Int, riderId: Int, make: String, model: String, year: Int, distanceUnit: Int, firstName: String, lastName: String) <- f) yield MotorcycleWithRider(id, riderId, make, model, year, distanceUnit, firstName, lastName)
      //      val res = f.map((id: Int, riderId: Int, make: String, model: String, year: Int, distanceUnit: Int, firstName: String, lastName: String) => MotorcycleWithRider(id, riderId, make, model, year, distanceUnit, firstName, lastName)
      //    names

      f.map(s => makeM(s))
      //      val res = for (names <- f) yield names
      //      res
    } finally db.close
  }

  def makeM(s: Seq[(Int, Int, String, String, Int, Int, String, String)]): Seq[MotorcycleWithRider] = {
    val res = for ((id: Int, riderId: Int, make: String, model: String, year: Int, distanceUnit: Int, firstName: String, lastName: String) <- s) yield MotorcycleWithRider(id, riderId, make, model, year, distanceUnit, firstName, lastName)
    res
  }
}

trait MotorcyclesActorProvider {
  def motorcyclesActor: ActorRef
}

trait SlickMotorcyclesActorProvider extends MotorcyclesActorProvider with ActorRefFactorySupport {
  lazy val motorcyclesActor = actorRefFactory.actorOf(MotorcyclesActor.props, MotorcyclesActor.name)
}
