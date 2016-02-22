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

  def handleSuccess(f: Future[String]): String = {
    f.onSuccess {
      case responseData =>
        responseData
    }
    ""
  }

  val ridersRoute = get {
      path("riders") {
        onComplete((ridersActor ? GetAllRiders).mapTo[Future[Seq[Rider]]]) {
          case Success(response) =>
            onComplete(response) {
              case Success(riders) =>
                val html = Header + "<BODY>" + Menu + makeRidersTable(riders) + "</BODY>" + Footer
                respondWithMediaType(`text/html`) {
                  complete(html)
                }

              case Failure(t) =>
                log.error("Error retreiving riders", t)
                respondWithMediaType(`text/html`) {
                  complete("Oops .....")
                }
            }
          case Failure(t) =>
            log.error("Error retreiving riders", t)
            respondWithMediaType(`text/html`) {
              complete("Oops .....")
            }
        }
      } ~
      path("riderstable") {
        //        val f: Future[List[Riderr]] = (ridersActor ? GetAllRiders).mapTo[List[Riderr]]
        onComplete((ridersActor ? GetAllRiders).mapTo[List[Riderr]]) {
          case Success(response) =>
            val html = Header + "<BODY>" + Menu + makeRidersTable(response) + "</BODY>" + Footer
            respondWithMediaType(`text/html`) {
              complete(html)
            }

        }
      } ~
      path("riderstables") {
        val f: Future[List[Riderr]] = (ridersActor ? GetAllRiders).mapTo[List[Riderr]]
        onSuccess(f) { response =>
          //complete(makeRidersTable(response))
          val html = Header + "<BODY>" + Menu + makeRidersTable(response) + "</BODY>" + Footer
          respondWithMediaType(`text/html`) {
            complete(html)
          }

        }
      } ~
      path("namess") {
        onComplete((ridersActor ? GetFirstNames).mapTo[Future[Seq[String]]]) {
          case Success(response) =>
            val html = Header + "<BODY>" + Menu + response.toString /*response.mkString(" : ")*/ + "</BODY>" + Footer
            respondWithMediaType(`text/html`) {
              complete(html)
            }
          case Failure(t) =>
            log.error("Error retreiving names", t)
            respondWithMediaType(`text/html`) {
              complete("Oops .....")
            }
        }
      } ~
      path("names") {
        onComplete((ridersActor ? GetFirstNames).mapTo[Future[Seq[String]]]) {
          case Success(response) =>
            onComplete(response) {
              case Success(riders) =>
                val html = Header + "<BODY>" + Menu + riders.mkString(" : ") + "</BODY>" + Footer
                respondWithMediaType(`text/html`) {
                  complete(html)
                }

              case Failure(t) =>
                log.error("Error retreiving names", t)
                respondWithMediaType(`text/html`) {
                  complete("Oops .....")
                }
            }
          case Failure(t) =>
            log.error("Error retreiving names", t)
            respondWithMediaType(`text/html`) {
              complete("Oops .....")
            }
        }
      } ~
      path("riderss") {
        val f: Future[String] = (ridersActor ? GetAllRidersAsString).mapTo[String]
        onSuccess(f) { response =>
          complete(response)
        }
      }
  }

  def makeRidersTable(riders: List[Riderr]): String = {
    val riderTableEntries = for (r <- riders) yield "<td>" + r.id + "</td>" + "<td>" + r.firstName + "</td>"
    "<table border=\"1\">" + riderTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  }

  def makeRidersTable(riders: Seq[Rider]): String = {
    val riderTableEntries = for (r <- riders) yield "<td>" + r.id + "</td>" +
      "<td>" + r.firstName + "</td>" +
      "<td>" + r.lastName + "</td>" +
      "<td>" + r.emailAddress.getOrElse("") + "</td>" +
      "<td>" + r.streetAddress.getOrElse("") + "</td>"
    "<table border=\"1\">" + riderTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  }
}
