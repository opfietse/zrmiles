package net.opfietse
package zrmiles
package web.home

import spray.routing._
import spray.http._

trait ZrmilesRoute extends HttpService with IndexRoute {

  val routes = indexRoute
}

trait IndexRoute extends HttpService {
  val indexRoute =
    path("index.jsp") {
      complete(<html><body>Moi</body></html>)
    }
}
