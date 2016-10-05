package net.opfietse
package zrmiles
package web
package home

import spray.routing._
import spray.http.MediaTypes._
import spray.http._

import util.ExecutionContextSupport
import motorcycles.MotorcyclesRoute
import riders.RidersRoute
import common.CommonHtml._

trait ZrmilesRoute extends HttpService
    with LoginRoute
    with IndexRoute
    with HomeIndexRoute
    with RidersRoute
    with MotorcyclesRoute
    with ExecutionContextSupport {

  val routes = loginRoute ~ homeIndexRoute ~ optionalCookie(CredCookieName) { cc =>
    val credCookieValue = if (cc.isDefined) cc.get.content else "cookie not set"
    println(s"Credcookievalue $credCookieValue")
    if (credCookieValue != "known") {
      redirect("/login", StatusCodes.SeeOther)
    } else {
      indexRoute /*~ homeIndexRoute*/ ~ ridersRoute ~ motorcyclesRoute
    }
  }
}

trait HomeIndexRoute extends HttpService
    with SettingsProvider
    with CookieSupport {

  val homeIndexRoute =
    path("home" / "index.jsp") {
      parameters("username".?, "pasword".?) { (u, p) =>
        hostName { h =>
          optionalCookie(CredCookieName) { cc =>
            val credCookieValue = if (cc.isDefined) cc.get.content else "cookie not set"
            println(s"Credcookievalue $credCookieValue")
            if (credCookieValue != "known" & u.isEmpty) {
              redirect("/login", StatusCodes.SeeOther)
            } else {
              println("Else")
              val html = Header + "<BODY>" + Menu + Welcome + "Username: " + u + "<br />Cred cookie value: " + credCookieValue + "</BODY>" + Footer
              //        setCookie(HttpCookie("credentials", content = "loggedIn")) {
              setCookie(makeCredentialsCookie(u, p, h, cc, settings)) {
                redirect("/index.jsp", StatusCodes.SeeOther)
                //                respondWithMediaType(`text/html`) {
                //                  complete(html)
                //                }
              }
            }
          }
        }
      }
    } ~ pathEndOrSingleSlash {
      redirect("home/index.jsp", StatusCodes.SeeOther)
    }
}

object LoginDirectives {
}
