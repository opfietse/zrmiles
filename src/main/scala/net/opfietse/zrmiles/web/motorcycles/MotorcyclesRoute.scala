package net.opfietse.zrmiles
package web
package motorcycles

import net.opfietse.zrmiles.db.riders.RidersActor.{ GetRider, GetAllRiders }

import scala.concurrent._
import scala.util._

import akka.actor._
import akka.pattern.ask
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.routing.HttpService

import org.slf4j.LoggerFactory

import net.opfietse.zrmiles.db.riders.SlickRidersActorProvider
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
    with SlickMotorcyclesActorProvider
    with SlickRidersActorProvider {

  private def log = LoggerFactory.getLogger("RiderRoute")

  val motorcyclesRoute = get {
    path("bikes" / "bikes.jsp") {
      pathEndOrSingleSlash {
        onComplete((motorcyclesActor ? GetAllMotorcyclesWithRiders).mapTo[Future[Seq[MotorcycleWithRider]]].flatMap(motorcycles => motorcycles)) {
          case Success(bikes) =>
            val html = Header + "<BODY>" + Menu + "<BR/><BR/>All bikes in the database.<BR/><BR />" +
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
    } ~
      path("bikes" / "add.jsp") {
        parameters('riderId) { riderId =>
          onComplete((motorcyclesActor ? GetAddBikeStuff(riderId.toInt)).mapTo[Future[(Rider, List[Motorcycle])]].flatMap(tup => tup)) {
            case Success((rider, bikes)) =>
              val html = Header + "<BODY>" + Menu + "<BR /><BR />" + makeAddBikeText(rider) + "<BR /><BR />" +
                bikeForm(None, None, None, 0) + "<BR />" + makeMotorcyclesTableForRider(bikes, rider.firstName) + instructions + "</BODY>" + Footer
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
      } ~
      post {
        path("bikes" / "add.jsp") {
          parameters('riderId) { riderId =>
            pathEndOrSingleSlash {
              formFields('make, 'model, 'year.?.as[Option[Int]], 'distanceUnit.as[Int]) { (make, model, year, distanceUnit) =>
                onComplete((motorcyclesActor ? AddBike(riderId.toInt, make, model, year, distanceUnit)).mapTo[Motorcycle]) {
                  case Success(newBike) =>
                    onComplete((motorcyclesActor ? GetAddBikeStuff(riderId.toInt)).mapTo[Future[(Rider, List[Motorcycle])]].flatMap(tup => tup)) {
                      case Success((rider, bikes)) =>
                        val html = Header + "<BODY>" + Menu + "<BR/><BR/>" + makeAddBikeText(rider) + "<BR/><BR />" +
                          bikeForm(None, None, None, 0) + instructions + "</BODY>" + Footer
                        respondWithMediaType(`text/html`) {
                          complete(html)
                        }

                      case Failure(t) =>
                        log.error("Error retreiving riders", t)
                        respondWithMediaType(`text/html`) {
                          complete("Oops .....")
                        }
                    }
                  //                    val html = Header + "<BODY>" + Menu + welcomeNewRider(newRider) + "</BODY>" + Footer
                  //                    respondWithMediaType(`text/html`) {
                  //                      complete(html)
                  //                    }
                  case Failure(f) =>
                    log.error("Error adding bike", f)
                    val html = Header + "<BODY>" + Menu + "<br /><span class=\"errorText\">" + f.getMessage + "</span><br />" + bikeForm(Some(make), Some(model), year, distanceUnit) + "</BODY>" + Footer
                    respondWithMediaType(`text/html`) {
                      complete(html)
                    }
                }
              }
            }
          }
        }
      }
  }

  private val instructions = "Click on the <B>Id</B> to show/add the mileage for that bike.<BR>\nClick on the <B>Model</B> field to CHANGE or DELETE the entry\nClick on the <B>Owner</B> field to CHANGE or DELETE that rider"

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

  def makeMotorcyclesTableForRider(motorcycles: List[Motorcycle], rider: String): String = {
    val tableHeader = "<th>Id</th><th>Owner</th><th>Make</th><th>Model</th><th>Year</th><th>Odometer in</th><th>Current mileage</th>"
    val motorcycleTableEntries = for (m <- motorcycles) yield "<td>" + makeMilesLink(m) + "</td>" +
      "<td>" + rider + "</td>" +
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
  def makeMotorcycleLink(motorcycle: Motorcycle) = "<a href=\"update.jsp?motorcycleId=" + motorcycle.id + "\">" + motorcycle.model + "</a>"

  def makeAddBikeText(rider: Rider): String = {
    val quote = if (rider.lastName.toLowerCase.endsWith("s")) "'" else "'s"
    "Enter data for " + rider.firstName + " " + rider.lastName + quote + " bike below."
  }

  def bikeForm(make: Option[String], model: Option[String], year: Option[Int], distanceUnit: Int): String = {
    val makeFieldValue = make match {
      case Some(make) => make
      case None => ""
    }
    val modelFieldValue = model match {
      case Some(model) => model
      case None => ""
    }
    val yearFieldValue = year match {
      case Some(year) => year.toString
      case None => ""
    }

    "<FORM name=\"addBikeForm\" method=\"POST\">" +
      "<TABLE border=\"0\">" +
      "<CAPTION><EM>Bike</EM></CAPTION>" +
      "<TR><TD>" +
      "Make *" +
      "</TD><TD>" +
      "<INPUT type=\"text\" size=\"40\" maxlength=\"40\" name=\"make\"" +
      "value=\"" + makeFieldValue + "\">" +
      "</TD></TR>" +
      "<TR><TD>" +
      "Model *" +
      "</TD><TD>" +
      "<INPUT type=\"text\" size=\"50\" maxlength=\"50\" name=\"model\"" +
      "value=\"" + modelFieldValue + "\">" +
      "</TD></TR>" +
      "<TR><TD>" +
      "Year" +
      "</TD><TD>" +
      "<INPUT type=\"text\" size=\"4\" maxlength=\"4\" name=\"year\"" +
      "value=\"" + yearFieldValue + "\">" +
      "</TD></TR>" +
      "<TR><TD>" +
      "Odometer in" +
      "</TD><TD>" +
      "<SELECT name=\"distanceUnit\">" +
      makeOption("0", "Miles", distanceInMiles(distanceUnit)) +
      makeOption("1", "Kilometers", distanceInMiles(distanceUnit)) +
      "</SELECT>" +
      "</TD></TR>" +
      "<TR>" +
      "<TD colspan=\"2\" align=\"center\"><INPUT type=\"submit\" name=\"addMotorcycle\" value=\"Add\">" +
      "</TR>" +
      "</TABLE>" +
      "</FORM>" +
      "* = Required.<BR><BR>"
  }

  def makeOption(value: String, caption: String, selected: Boolean): String = {
    val selectedAsString = if (selected) "selected" else ""
    "<OPTION " + selectedAsString + " value=\"" + value + "\">" + caption + "</OPTION>"
  }

  def distanceInMiles(distanceUnit: Int): Boolean = distanceUnit == 0
}
