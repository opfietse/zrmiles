package net.opfietse.zrmiles.db.riders

import scala.concurrent._
import scala.concurrent.duration._
import scala.util._
import scala.collection.JavaConversions._

import akka.actor._
import org.joda.time.DateTime

import slick.driver.MySQLDriver.api._

import net.opfietse.zrmiles.db.Db
import net.opfietse.zrmiles.db.riders.RidersActor._
import net.opfietse.zrmiles.util.ActorRefFactorySupport
import net.opfietse.zrmiles.db.ZrMilesSchema._
import net.opfietse.zrmiles.model._
import nl.wobble.zrmiles.dao._

object RidersActor {
  def props = Props[RidersActor]
  def name = "riders-actor"

  sealed trait Command
  case object GetAllRidersAsString extends Command
  case object GetAllRiders extends Command
  case object GetAllRidersSlick extends Command
  case object GetFirstNames extends Command
  case object GetTime extends Command
  case class AddRider(firstName: String, lastName: String, emailAddress: Option[String], streetAddress: Option[String]) extends Command
  case class GetRider(id: Int) extends Command
}

class RidersActor extends Actor with ActorLogging {

  implicit val executionContext = context.dispatcher

  def receive: Receive = {
    case GetAllRidersAsString =>
      val riders = getAllRidersAsString
      sender ! riders
    case GetAllRiders =>
      val riders = getAllRiders
      sender ! riders
    case GetAllRidersSlick =>
      val riders = getAllRidersSlick
      sender ! riders
    case GetFirstNames =>
      val names = getAllAsRider
      sender ! names
    case GetRider(id) =>
      val rider = getRider(id)
      sender ! rider

    case AddRider(firstName, lastName, emailAddress, streetAddress) =>
      try {
        val newRider = addRider(firstName, lastName, emailAddress, streetAddress)
        sender ! newRider
      } catch {
        case e: Exception => sender ! Status.Failure(e)
      }
  }

  def getTime = {
    val now = DateTime.now()
    now.toString()
  }

  def getAllRiders: List[Rider] = {
    log.info("GetAllRiders ++++++++++++")
    val javaRiders: java.util.ArrayList[nl.wobble.zrmiles.common.Rider] = DaoFactory.getInstance().getRidersDao(new EnvDbInfo).findAll("ID").asInstanceOf[java.util.ArrayList[nl.wobble.zrmiles.common.Rider]]

    val riders: List[Rider] = javaRiders.toList.map(rider => toScalaRider(rider))
    riders
  }

  def getRider(id: Int): Rider = {
    log.info("Get rider with id {}", id)
    val rider: nl.wobble.zrmiles.common.Rider = DaoFactory.getInstance().getRidersDao(new EnvDbInfo).findById(id)
    toScalaRider(rider)
  }

  def toScalaRider(rider: nl.wobble.zrmiles.common.Rider): Rider = {
    val emailAddress = if (rider.getEmailAddress == null)
      None
    else
      Some(rider.getEmailAddress)

    val streetAddress = if (rider.getStreetAddress == null)
      None
    else
      Some(rider.getStreetAddress)

    val password = if (rider.getUserPassword == null)
      None
    else
      Some(rider.getUserPassword)

    Rider(rider.getId, rider.getFirstName, rider.getLastName, emailAddress, streetAddress, rider.getUserName, password, rider.getUserRole)
  }

  def toJavaRider(rider: Rider): nl.wobble.zrmiles.common.Rider = {
    new nl.wobble.zrmiles.common.Rider(rider.id, rider.firstName, rider.lastName, rider.emailAddress.getOrElse(null),
      rider.streetAddress.getOrElse(null), rider.username, rider.password.getOrElse(null), rider.role)
  }

  def getAllRidersSlick: Future[Seq[Rider]] = {
    log.info("GetAllRidersSlick ++++++++++++")
    val db = Db.db

    try {
      //    val dbRidersFirstNames = for (r <- riders) yield Rider(r.id, r.firstName, r.lastName, r.streetAddress, r.emailAddress, r.username, r.password, r.role)
      val dbRiders = for (r <- riders) yield r
      val q = dbRiders.result
      val f: Future[Seq[Rider]] = db.run(q)

      val res = for (names <- f) yield names
      res
    } finally db.close
  }

  def addRider(
    firstName: String,
    lastName: String,
    emailAddress: Option[String],
    streetAddress: Option[String]
  ): Rider = {

    require(!firstName.isEmpty, "First name cannot be empty")
    require(!lastName.isEmpty, "Last name cannot be empty")

    val ridersDao = DaoFactory.getInstance().getRidersDao(new EnvDbInfo)
    val newKey = ridersDao.
      add(toJavaRider(Rider(0, firstName, lastName, emailAddress, streetAddress, "X", None, 0))) //.asInstanceOf[Int]]

    toScalaRider(ridersDao.findById(newKey))
  }

  def addRiderSlick(firstName: String, lastName: String, emailAddress: Option[String], streetAddress: Option[String]) = {
    val db = Db.db

    try {
      //      val maxIdQuery = riders.map(_.id)
      //
      //      val currentMaxId /*: slick.lifted.Rep[Option[Int]]*/ = maxIdQuery.max.result
      //      println(currentMaxId.statements)
      val insert = riders += Rider(1500, firstName, lastName, None, streetAddress, "X", None, 0)
      val newRider = db.run(insert)
      println("N: " + newRider)
      for (a <- newRider) yield a
    } finally db.close
  }

  def getAllRidersAsString = {
    println("Run GetAllRidersAsString ++++++++++++")
    //    getAllRiders.mkString(",")
  }

  def getAllAsRider: Future[Seq[String]] = {
    log.info("get all names from db  ===============================")
    val db = Database.forConfig("mysql")

    try {
      val dbRidersFirstNames = for (r <- riders) yield r.firstName
      val q = dbRidersFirstNames.result
      val f: Future[Seq[String]] = db.run(q)

      val res = for (names <- f) yield names
      res
    } finally db.close
  }

}

trait RidersActorProvider {
  def ridersActor: ActorRef
}

trait SlickRidersActorProvider extends RidersActorProvider with ActorRefFactorySupport {
  lazy val ridersActor = actorRefFactory.actorOf(RidersActor.props, RidersActor.name)
}
