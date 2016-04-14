package net.opfietse
package zrmiles
package web
package home

import spray.routing._
import spray.http.MediaTypes._
import spray.http.StatusCodes

import util.ExecutionContextSupport
import motorcycles.MotorcyclesRoute
import riders.RidersRoute
import common.CommonHtml._

trait ZrmilesRoute extends HttpService
    with IndexRoute
    with HomeIndexRoute
    with RidersRoute
    with MotorcyclesRoute
    with ExecutionContextSupport {

  val routes = indexRoute ~ homeIndexRoute ~ ridersRoute ~ motorcyclesRoute
}

trait HomeIndexRoute extends HttpService {
  val homeIndexRoute =
    path("home" / "index.jsp") {
      val html = Header + "<BODY>" + Menu + Welcome + "</BODY>" + Footer
      respondWithMediaType(`text/html`) {
        complete(html)
      }
    } ~ pathEndOrSingleSlash {
      redirect("home/index.jsp", StatusCodes.TemporaryRedirect)
    }
}
