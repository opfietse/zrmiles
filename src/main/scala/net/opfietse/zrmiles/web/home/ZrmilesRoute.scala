package net.opfietse
package zrmiles
package web
package home

import spray.routing._
import spray.http.MediaTypes._

import common.CommonHtml._

trait ZrmilesRoute extends HttpService
with IndexRoute
with HomeIndexRoute {

  val routes = homeIndexRoute ~ indexRoute
}

trait HomeIndexRoute extends HttpService {
  val homeIndexRoute =
    path("home" / "index.jsp") {

      val html = Header + "<BODY>" + Menu + Welcome + "</BODY>" + Footer
      respondWithMediaType(`text/html`) {
        complete(html)
      }
    }
}
