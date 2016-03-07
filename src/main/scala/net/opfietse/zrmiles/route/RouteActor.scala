package net.opfietse
package zrmiles
package route

import scala.concurrent._
import scala.concurrent.duration._

import akka.actor._
import akka.event.Logging._
import spray.http._
import spray.routing._
import spray.routing.directives.LogEntry

import web.home.ZrmilesRoute
import net.opfietse.zrmiles.db.riders.SlickRidersActorProvider

object RouteActor {
  def props = Props[RouteActor]
  def name = "zrmiles-route"
}
class RouteActor extends HttpServiceActor with ZrmilesRoute with SlickRidersActorProvider {
  implicit def executionContext = context.dispatcher

  implicit val timeout = 10 seconds

  def receive = runRoute(
    logRequestResponse(showRequestResponses _)(routes)
  )

  /**
   * Log each request and response.
   */
  def showRequestResponses(request: HttpRequest): Any => Option[LogEntry] = {
    case HttpResponse(status, _, _, _) => Some(LogEntry(s"${request.method} ${request.uri} ($status)", InfoLevel))
    case response => Some(LogEntry(s"${request.method} ${request.uri} $response", WarningLevel))
  }
}
