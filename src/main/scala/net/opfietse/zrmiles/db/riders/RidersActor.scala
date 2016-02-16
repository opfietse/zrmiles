package net.opfietse.zrmiles.db.riders

import akka.actor._
import net.opfietse.zrmiles.db.riders.RidersActor.{ GetAllRidersAsString, GetAllRiders }
import net.opfietse.zrmiles.util.ActorRefFactorySupport

import slick.driver.MySQLDriver.api._

import net.opfietse.zrmiles.db.ZrMilesSchema._
import net.opfietse.zrmiles.db.ZrMilesSchema.Riders
import net.opfietse.zrmiles.model.Rider

import scala.concurrent.Future

object RidersActor {
  def props = Props[RidersActor]
  def name = "riders-actor"

  sealed trait Command
  case object GetAllRidersAsString extends Command
  case object GetAllRiders extends Command
}

class RidersActor extends Actor with ActorLogging {

  def receive: Receive = {
    case GetAllRidersAsString =>
      val riders = getAllRiders.mkString(",")
      sender ! riders
    case GetAllRiders =>
      val riders = getAllRiders
      sender ! riders
  }

  def getAllRiders = {
    List(Rider(1, "Mark", "Reuvekamp", Some("email"), Some("street"), "username", Some("password"), 10), Rider(2, "Tom", "Witt", Some("email"), Some("street"), "username", Some("password"), 10))
  }

  //
  //  def getAllRiders /*: Future[Seq[Rider]]*/ = {
  //    val db = Database.forConfig("mysql")
  //    try {
  //      //      //      db.run(riders.result).map((id, firstName, emailAddress, streetAddress, username, password, role) => Rider(iid, firstName, emailAddress, streetAddress, username, password, role))
  //
  //      //db.run(riders.result).map((id, firstName, lastName, emailAddress, streetAddress, username, password, role)).forEach( => Rider(iid, firstName, emailAddress, streetAddress, username, password, role))
  //
  //      //      val ridersFromDb: Seq[Riders] = db.run(riders.result)
  //      //
  //      //      val riders: Seq[Rider] = for (r <- ridersFromDb) yield Rider(r.id, r.firstName, r.lastName, r.emailAddress, r.streetAddress, r.username, r.password, r.role)
  //      //      riders
  //
  //      //      val q = for (r <- riders) yield (r.id, r.firstName, r.lastName, r.emailAddress, r.streetAddress, r.username, r.password, r.role)
  //      //      val q = for (r <- riders) yield r
  //      //      val a = q.result
  //      //      val f: Future[Seq[Rider]] = db.run(a)
  //      //      f
  //
  //      db.run(riders.result).map(_.foreach {
  //        case (id, firstName, lastName, emailAddress, streetAddress, username, password, role) =>
  //          println("  " + lastName + "\t" + emailAddress + "\t" + streetAddress + "\t" + role)
  //      })
  //
  //    } finally db.close
  //  }
}

trait RidersActorProvider {
  def ridersActor: ActorRef
}

trait SlickRidersActorProvider extends RidersActorProvider with ActorRefFactorySupport {
  lazy val ridersActor = actorRefFactory.actorOf(RidersActor.props, RidersActor.name)
}
