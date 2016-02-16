package net.opfietse.zrmiles
package web
package riders

import akka.actor.ActorRef
import akka.pattern.ask
import spray.http.MediaTypes._
import spray.http.StatusCodes
import spray.routing.HttpService

import db.riders._
import model.Rider
import net.opfietse.zrmiles.util.{ ExecutionContextSupport, ActorAskPattern }
import common.CommonHtml._
import net.opfietse.zrmiles.db.riders.RidersActor.{ GetAllRidersAsString, GetAllRiders }

import scala.concurrent.{ ExecutionContext, Future }
import scala.util.{ Failure, Success }

//object RiderMagnet {
//  implicit def fromRiderFuture(future: Future[List[Rider]])(implicit ec: ExecutionContext) =
//    new CompletionMagnet {
//      type Result = String
//
//      def apply(): Result = Future {
//        "Moi"
//      }
//    }
//}

trait RidersRoute extends HttpService with ExecutionContextSupport with ActorAskPattern with SlickRidersActorProvider {
  //  val ridersActor: ActorRef

  //  def handleSuccess(f: Future[String]): Unit = {
  //    onSuccess(f) { responseData =>
  //      complete(_)
  //    }
  //  }

  def divide(a: Int, b: Int): Future[Int] = Future {
    a / b
  }

  def handleSuccess(f: Future[String]): String = {
    f.onSuccess {
      case responseData =>
        responseData
    }
    ""
  }

  //implicit val exectutionContext = context.dispatcher

  val ridersRoute = path("divide" / IntNumber / IntNumber) { (a, b) =>
    onComplete(divide(a, b)) {
      case Success(value) => complete(s"The result was $value")
      case Failure(ex) => complete(StatusCodes.InternalServerError, s"An error occurred: ${ex.getMessage}")
    }
  } ~
    path("moi") {
      val f = Future { "Mooi" }
      onSuccess(f) { response =>
        complete(response)
      }
    } ~
    path("riders") {
      val f: Future[List[Rider]] = (ridersActor ? GetAllRiders).mapTo[List[Rider]]
      onSuccess(f) { response =>
        complete(response.mkString(":"))
      }
    } ~
    path("riderss") {
      val f: Future[String] = (ridersActor ? GetAllRidersAsString).mapTo[String]
      onSuccess(f) { response =>
        complete(response)
      }
    }

  //  path("riders" / "riders.jsp") {
  //    val f = (ridersActor ? GetAllRiders).mapTo[String]
  //
  //    //    //handleSuccess(f)
  //    //    onSuccess(f) { responseData =>
  //    //      complete(_)
  //    //    }
  //
  //    onComplete(handleSuccess((ridersActor ? GetAllRiders).mapTo[String])) {
  //      case Success(riders: String) =>
  //        respondWithMediaType(`text/html`) {
  //          complete(Header + "<BODY>" + Menu + riders + "</BODY>" + Footer)
  //        }
  //    }
  //    //        onComplete((ridersActor ? GetAllRiders).mapTo[List[Rider]]) {
  //    //          case Success(riders: List[Rider]) =>
  //    //            respondWithMediaType(`text/html`) {
  //    //              complete(Header + "<BODY>" + Menu + makeRidersTable(riders) + "</BODY>" + Footer)
  //    //            }
  //    //      //    val html = Header + "<BODY>" + Menu + Welcome + "</BODY>" + Footer
  //    //      val riders = ridersActor ? GetAllRiders
  //    //
  //    //      respondWithMediaType(`text/html`) {
  //    //        complete(Header + "<BODY>" + Menu + makeRidersTable(riders) + "</BODY>" + Footer)
  //    //      }
  //    //    }
  //  }

  def makeRidersTable(riders: List[Rider]): String = {
    val riderTableEntries = for (r <- riders) yield "<td>" + r.id + "</td>" + "<td>" + r.firstName + "</td>"
    riderTableEntries.mkString("<tr>", "", "</tr>")
  }
}
