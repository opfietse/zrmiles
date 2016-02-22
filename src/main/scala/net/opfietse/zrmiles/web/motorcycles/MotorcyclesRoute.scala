package net.opfietse.zrmiles
package web
package motorcycles

import scala.concurrent._
import scala.util._

import akka.actor._
import akka.pattern.ask
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.routing.HttpService

import org.slf4j.LoggerFactory

import net.opfietse.zrmiles.db.motorcycles.SlickMotorcyclesActorProvider
import net.opfietse.zrmiles.web.common
import net.opfietse.zrmiles._
import net.opfietse.zrmiles.model._
import net.opfietse.zrmiles.web.common.CommonHtml._
import db.motorcycles.MotorcyclesActor._
import net.opfietse.zrmiles.util._

trait MotorcyclesRoute extends HttpService
    with ExecutionContextSupport
    with ActorAskPattern
    with SlickMotorcyclesActorProvider {

  private def log = LoggerFactory.getLogger("RiderRoute")

  val motorcyclesRoute = get {
    path("bikes") {
      pathEndOrSingleSlash {
        onComplete((motorcyclesActor ? GetAllMotorcycles).mapTo[Future[Seq[Motorcycle]]]) {
          case Success(response) =>
            onComplete(response) {
              case Success(riders) =>
                val html = Header + "<BODY>" + Menu + "<BR/><BR/>All riders in the database.<BR/><BR />" +
                  makeMotorcyclesTable(riders) + instructions + "</BODY>" + Footer
                respondWithMediaType(`text/html`) {
                  complete(html)
                }

              case Failure(t) =>
                log.error("Error retreiving motorcycles", t)
                respondWithMediaType(`text/html`) {
                  complete("Oops .....")
                }
            }
          case Failure(t) =>
            log.error("Error retreiving motorcycles", t)
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

  private val instructions = "<BR>Click on the <B>Id</B> to show/add the bikes for that rider.<BR>Click on the <B>last name</B> to CHANGE or DELETE that rider."

  def makeMotorcyclesTable(motorcycles: Seq[Motorcycle]): String = {
    val tableHeader = "<th>Id</th><th>Owner</th><th>Make</th><th>Model</th><th>Year</th><th>Odometer in</th><th>Current mileage</th>"
    val motorcycleTableEntries = for (m <- motorcycles) yield "<td>" + makeMilesLink(m) + "</td>" +
      "<td>" + m.riderId + "</td>" +
      "<td>" + m.make + "</td>" +
      "<td>" + m.model + "</td>" +
      "<td>" + m.year.getOrElse("") + "</td>" +
      "<td>" + m.distanceUnit + "</td>"

    "<table border=\"1\" summary=\"Lists the bikes currently in the database\"><CAPTION><EM>Bikes</EM></CAPTION>" +
      tableHeader + motorcycleTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  }

  def makeMilesLink(motorcycle: Motorcycle) = "<a href=\"miles/showandadd.jsp?motorcycleId=" + motorcycle.id + "\">" + motorcycle.id + "</a>"
  //  def makeAddBikeLink(rider: Rider) = "<a href=\"bikes/add.jsp?riderId=" + rider.id + "\">" + rider.id + "</a>"
  //  def makeUpdateRiderLink(rider: Rider) = "<a href=\"riders/update.jsp?riderId=" + rider.id + "\">" + rider.lastName + "</a>"
}
