package net.opfietse.zrmiles.web

import spray.http.MediaTypes._
import spray.routing.HttpService

trait IndexRoute extends HttpService {
  val html = "<!DOCTYPE HTML PUBLIC \"-//w3c//dtd html 4.0 transitional//en\">\n<HTML>\n<HEAD>\n<META name=\"robots\" content=\"noindex,nofollow\">\n<LINK rel=\"StyleSheet\" href=\"zrmiles.css\" type=\"text/css\">\n<TITLE>ZR_Riders - Mileage database - Home page</TITLE>\n</HEAD>\n<BODY>\n<A href=\"home/index.jsp\">Please go here ...</A>\n</BODY>\n</HTML>"

  val indexRoute =
    path("index.jsp") {
      respondWithMediaType(`text/html`) {
        complete(html)
      }
    }
}
