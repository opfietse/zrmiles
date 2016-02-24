package net.opfietse
package zrmiles
package web
package home

import net.opfietse.zrmiles.util.ExecutionContextSupport
import net.opfietse.zrmiles.web.riders.RidersRoute
import spray.routing._
import spray.http.MediaTypes._

import common.CommonHtml._

trait ZrmilesRoute extends HttpService
    with IndexRoute
    with HomeIndexRoute
    with RidersRoute
    with ExecutionContextSupport {

  val routes = indexRoute ~ homeIndexRoute ~ ridersRoute
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
