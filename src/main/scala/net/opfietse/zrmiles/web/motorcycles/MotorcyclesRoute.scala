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
    path("bikes" / "bikes.jsp") {
      pathEndOrSingleSlash {
        onComplete((motorcyclesActor ? GetAllMotorcyclesWithRiders).mapTo[Future[Seq[MotorcycleWithRider]]].flatMap(motorcycles => motorcycles)) {
          case Success(bikes) =>
            val html = Header + "<BODY>" + Menu + "<BR/><BR/>All riders in the database.<BR/><BR />" +
              makeMotorcyclesTableW(bikes) + instructions + "</BODY>" + Footer
            respondWithMediaType(`text/html`) {
              complete(html)
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

  private val instructions = "<BR>Click on the <B>Id</B> to show/add the bikes for that rider.<BR>Click on the <B>last name</B> to CHANGE or DELETE that rider."

  def makeMotorcyclesTable(motorcycles: Seq[Motorcycle]): String = {
    val tableHeader = "<th>Id</th><th>Owner</th><th>Make</th><th>Model</th><th>Year</th><th>Odometer in</th><th>Current mileage</th>"
    val motorcycleTableEntries = for (m <- motorcycles) yield "<td>" + makeMilesLink(m) + "</td>" +
      "<td>" + m.riderId + "</td>" +
      "<td>" + m.make + "</td>" +
      "<td>" + m.model + "</td>" +
      "<td>" + m.year + "</td>" +
      "<td>" + m.distanceUnit + "</td>"

    "<table border=\"1\" summary=\"Lists the bikes currently in the database\"><CAPTION><EM>Bikes</EM></CAPTION>" +
      tableHeader + motorcycleTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  }

  def makeMotorcyclesTableW(motorcycles: Seq[MotorcycleWithRider]): String = {
    val tableHeader = "<th>Id</th><th>Owner</th><th>Make</th><th>Model</th><th>Year</th><th>Odometer in</th><th>Current mileage</th>"
    val motorcycleTableEntries = for (m <- motorcycles) yield "<td>" + makeMilesLink(m) + "</td>" +
      "<td>" + makeRiderLink(m) + "</td>" +
      "<td>" + m.make + "</td>" +
      "<td>" + makeMotorcycleLink(m) + "</td>" +
      "<td>" + m.year + "</td>" +
      "<td>" + m.distanceUnit + "</td>"

    "<table border=\"1\" summary=\"Lists the bikes currently in the database\"><CAPTION><EM>Bikes</EM></CAPTION>" +
      tableHeader + motorcycleTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  }

  def makeMilesLink(motorcycle: Motorcycle) = "<a href=\"../miles/showandadd.jsp?motorcycleId=" + motorcycle.id + "\">" + motorcycle.id + "</a>"
  def makeMilesLink(motorcycle: MotorcycleWithRider) = "<a href=\"../miles/showandadd.jsp?motorcycleId=" + motorcycle.id + "\">" + motorcycle.id + "</a>"
  def makeRiderLink(motorcycle: MotorcycleWithRider) = "<a href=\"../riders/update.jsp?riderId=" + motorcycle.riderId + "\">" + motorcycle.firstName + " " + motorcycle.lastName + "</a>"
  def makeMotorcycleLink(motorcycle: MotorcycleWithRider) = "<a href=\"update.jsp?motorcycleId=" + motorcycle.id + "\">" + motorcycle.model + "</a>"
}
