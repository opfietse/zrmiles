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
    } ~
      path("zrmiles.css") {
        val css = "BODY {\n  background-color: lightgrey;\n  color: black;\n  font-family: monospace;\n}\n\nTABLE {\n  /* border: ridge 2pt; */\n  /* border-collapse: separate; */\n  /* border-spacing: 1pt; */\n}\n\nTD {\n  /* border: ridge 2pt; */\n  padding: 0.3em;\n  /* border: inset 1pt; */\n}\n\nTH {\n  padding: 0.3em;\n  /* border: inset 1pt; */\n}\n\nTD.lastname {\n  color: black;\n  font-size: +1;\n  text-align: center;\n}\n\nTD.notComplete {\n  color: grey;\n  text-align: right;\n}\n\nTD.right {\n  text-align: right;\n}\n\nTD.center {\n  text-align: center;\n}\n\nA {\n  color: blue;\n  font-family: helvetica, sans-serif;\n  font-weight: bold;\n}\n\n.successText {\n  color: green;\n  font-family: helvetica, sans-serif;\n  font-weight: bold;\n}\n\n.errorText {\n  color: red;\n  font-family: helvetica, sans-serif;\n  font-weight: bold;\n}\n\n.floatRight {\n  float: right;\n}\n\n.yearHighlight {\n  font-weight: bold;\n}\n\n.helpHeader {\n  color: black;\n  font-family: monospace;\n  font-weight: bold;\n}\n\n.standOut {\n  color: red;\n}"
        respondWithMediaType(`text/css`) {
          complete(css)
        }
      }
}
