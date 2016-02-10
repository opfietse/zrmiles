package net.opfietse
package zrmiles
package route

import akka.actor._
import spray.http._
import spray.routing._


import web.home.ZrmilesRoute

object RouteActor {
  def props = Props[RouteActor]
  def name = "zrmiles-route"
}
class RouteActor extends HttpServiceActor with ZrmilesRoute {
  val exectutionContext = context.dispatcher

  def receive = runRoute(routes)
}
