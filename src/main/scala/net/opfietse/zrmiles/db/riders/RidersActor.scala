package net.opfietse.zrmiles.db.riders

import akka.actor._
import net.opfietse.zrmiles.db.Db
import net.opfietse.zrmiles.db.riders.RidersActor._
import net.opfietse.zrmiles.util.ActorRefFactorySupport
import org.joda.time.DateTime

import slick.driver.MySQLDriver.api._

import net.opfietse.zrmiles.db.ZrMilesSchema._
import net.opfietse.zrmiles.db.ZrMilesSchema.Riders
import net.opfietse.zrmiles.model.{ Riderr, Rider }

import scala.concurrent._
import scala.concurrent.duration._
import scala.util.{ Success, Failure }

object RidersActor {
  def props = Props[RidersActor]
  def name = "riders-actor"

  sealed trait Command
  case object GetAllRidersAsString extends Command
  case object GetAllRiders extends Command
  case object GetFirstNames extends Command
  case object GetTime extends Command
  case class AddRider(firstNAme: String, lastName: String, emailAddress: Option[String], streetAddress: Option[String])
}

class RidersActor extends Actor with ActorLogging {

  implicit val executionContext = context.dispatcher

  def receive: Receive = {
    case GetTime =>
      log.info("Time requested **********************************")
      getAllDbRiders
      val riders = getTime
      sender ! riders
    case GetAllRidersAsString =>
      val riders = getAllRidersAsString
      sender ! riders
    case GetAllRiders =>
      //      getAllDbRiders
      val riders = getAllRiders
      sender ! riders
    case GetFirstNames =>
      val names = getAllAsRider
      sender ! names

    case AddRider(firstName, lastName, emailAddress, streetAddress) =>
      val newRider = addRider(firstName, lastName, emailAddress, streetAddress)
    //      sender ! rider
  }

  def getTime = {
    val now = DateTime.now()
    now.toString()
  }

  def getAllRiders: Future[Seq[Rider]] = {
    log.info("GetAllRiders ++++++++++++")
    val db = Db.db

    try {
      //    val dbRidersFirstNames = for (r <- riders) yield Rider(r.id, r.firstName, r.lastName, r.streetAddress, r.emailAddress, r.username, r.password, r.role)
      val dbRidersFirstNames = for (r <- riders) yield r
      val q = dbRidersFirstNames.result
      val f: Future[Seq[Rider]] = db.run(q)

      val res = for (names <- f) yield names
      res
    } finally db.close
  }

  def addRider(firstName: String, lastName: String, emailAddress: Option[String], streetAddress: Option[String]) = {
    val db = Db.db

    try {
      //      riders += Rider(0, firstName, lastName, emailAddress, streetAddress)
    } finally db.close
  }

  def getAllRidersAsString = {
    println("Run GetAllRidersAsString ++++++++++++")
    //    getAllRiders.mkString(",")
  }

  def getAllDbRiders /*: Future[Seq[Rider]]*/ = {
    log.info("get from db  ===============================")
    //    val db = Database.forConfig("mysql")
    //    try {
    //      //      //      db.run(riders.result).map((id, firstName, lastName, emailAddress, streetAddress, username, password, role) => Rider(iid, firstName, lastName, emailAddress, streetAddress, username, password, role))
    //
    //      //db.run(riders.result).map((id, firstName, lastName, emailAddress, streetAddress, username, password, role)).forEach( => Rider(iid, firstName, emailAddress, streetAddress, username, password, role))
    //
    //      //      val ridersFromDb = db.run(riders.result).map(_.foreach {
    //      //        //        println("In foreach ++++++++++")
    //      //        case (id, firstName, lastName, emailAddress, streetAddress, username, password, role) => println("Rider: " + firstName)
    //      //        case _ => println("something else ...........................")
    //      //      })
    //      //ridersFromDb
    //      //
    //      //      val riderss: Seq[Rider] = for (r <- ridersFromDb) yield Rider(
    //      //        r.id.asInstanceOf[Int],
    //      //        r.firstName.toString(),
    //      //        r.lastName.toString(),
    //      //        r.emailAddress.toString(),
    //      //        r.streetAddress.toString(),
    //      //        r.username.toString(),
    //      //        r.password.toString(),
    //      //        r.role.asInstanceOf[Int]
    //      //      )
    //      //      riders
    //      //
    //      //      val q = for (r <- riders) yield (r.id, r.firstName, r.lastName, r.emailAddress, r.streetAddress, r.username, r.password, r.role)
    //      //      val q = for (r <- riders) yield r
    //      //      val a = q.result
    //      //      val f: Future[Seq[Rider]] = db.run(a)
    //      //      f
    //
    //      Await.result(
    //        db.run(riders.result).map(_.foreach {
    //          case (id, firstName, lastName, emailAddress, streetAddress, username, password, role) =>
    //            println("  " + firstName + "\t" + lastName + "\t" + emailAddress + "\t" + streetAddress + "\t" + role)
    //          case _ => log.info("something else")
    //        }), Duration.Inf
    //      )
    //
    //    } finally db.close
  }

  //  def getAllAsRider: Seq[String] = {
  def getAllAsRider: Future[Seq[String]] = {
    log.info("get all names from db  ===============================")
    val db = Database.forConfig("mysql")

    try {
      val dbRidersFirstNames = for (r <- riders) yield r.firstName
      val q = dbRidersFirstNames.result
      val f: Future[Seq[String]] = db.run(q)

      //      f
      //      val res = f.onSuccess {
      //        case rs =>
      //      }
      //

      val res = for (names <- f) yield names
      res
      //      val a = Future { "abcd " }
      //      f.map(name => name)

      //      val res: Seq[String] = f onComplete {
      //        case Success(names) =>
      //          val n = for (name <- names) yield name
      //          println(s"N: $n")
      //          n
      //        case Failure(t) => throw t
      //      }
      //
      //      println(s"Res: $res")
      //
      //      Seq("Mark")
    } finally db.close
  }

}

trait RidersActorProvider {
  def ridersActor: ActorRef
}

trait SlickRidersActorProvider extends RidersActorProvider with ActorRefFactorySupport {
  lazy val ridersActor = actorRefFactory.actorOf(RidersActor.props, RidersActor.name)
}
