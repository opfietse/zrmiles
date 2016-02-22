package net.opfietse.zrmiles
package web
package riders

import org.slf4j.LoggerFactory

import scala.concurrent._
import scala.util._

import akka.actor.{ ActorLogging, ActorRef }
import akka.pattern.ask
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.routing.HttpService

import db.riders._
import model._
import common.CommonHtml._
import db.riders.RidersActor._
import net.opfietse.zrmiles.util._

trait RidersRoute extends HttpService
    with ExecutionContextSupport
    with ActorAskPattern
    with SlickRidersActorProvider {

  def log = LoggerFactory.getLogger("RiderRoute")

  val ridersRoute = get {
    path("riders" / "riders.jsp") {
      pathEndOrSingleSlash {
        onComplete((ridersActor ? GetAllRiders).mapTo[Future[Seq[Rider]]].flatMap(riders => riders)) {
          case Success(riders) =>
            val html = Header + "<BODY>" + Menu + "<BR/><BR/>All riders in the database.<BR/><BR />" +
              makeRidersTable(riders) + instructions + "</BODY>" + Footer
            respondWithMediaType(`text/html`) {
              complete(html)
            }

          case Failure(t) =>
            log.error("Error retreiving riders", t)
            respondWithMediaType(`text/html`) {
              complete("Oops .....")
            }
        }
      }
    }
  }

  //  def makeRidersTable(riders: List[Riderr]): String = {
  //    val riderTableEntries = for (r <- riders) yield "<td>" + r.id + "</td>" + "<td>" + r.firstName + "</td>"
  //    "<table border=\"1\"> summary=\"Lists the riders currently in the database\"><CAPTION><EM>Riders</EM></CAPTION>" + riderTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  //  }

  val instructions = "<BR>Click on the <B>Id</B> to show/add the bikes for that rider.<BR>Click on the <B>last name</B> to CHANGE or DELETE that rider."

  def makeRidersTable(riders: Seq[Rider]): String = {
    val tableHeader = "<th>Id</th><th>First name</th><th>Last name</th><th>Email address</th><th>Location</th>"
    val riderTableEntries = for (r <- riders) yield "<td>" + makeAddBikeLink(r) + "</td>" +
      "<td>" + r.firstName + "</td>" +
      "<td>" + makeUpdateRiderLink(r) + "</td>" +
      "<td>" + r.emailAddress.getOrElse("") + "</td>" +
      "<td>" + r.streetAddress.getOrElse("") + "</td>"
    "<table border=\"1\" summary=\"Lists the riders currently in the database\"><CAPTION><EM>Riders</EM></CAPTION>" + tableHeader + riderTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  }

  def makeAddBikeLink(rider: Rider) = "<a href=\"bikes/add.jsp?riderId=" + rider.id + "\">" + rider.id + "</a>"
  def makeUpdateRiderLink(rider: Rider) = "<a href=\"riders/update.jsp?riderId=" + rider.id + "\">" + rider.lastName + "</a>"
}
