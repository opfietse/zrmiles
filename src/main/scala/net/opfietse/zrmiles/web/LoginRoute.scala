package net.opfietse.zrmiles.web

//import spray.http.MediaTypes._
import net.opfietse.zrmiles.SettingsProvider
import spray.routing.HttpService
//import net.opfietse.zrmiles.Settings
import net.opfietse.zrmiles.web.home.CookieSupport
//import spray.http.StatusCodes

trait LoginRoute extends HttpService
    with SettingsProvider
    with CookieSupport {
  val login = "<HTML>\n<HEAD>\n<META name=\"robots\" content=\"noindex,nofollow\">\n" +
    "<LINK rel=\"StyleSheet\" href=\"../zrmiles.css\" type=\"text/css\">\n" +
    "<TITLE>ZR_Riders - Mileage database - Login page</TITLE>\n</HEAD>\n<BODY>\n" +
    "<A href=\"../home/index.jsp\">Home</A>&nbsp;&nbsp;\n<A href=\"../riders/riders.jsp\">Riders</A>&nbsp;&nbsp;\n" +
    "<A href=\"../bikes/bikes.jsp\">Bikes</A>&nbsp;&nbsp;\n" +
    "<!-- <A href=\"../miles/thisyear.jsp\">Miles</A>&nbsp;&nbsp; -->\n" +
    "<A href=\"../reports/index.jsp\">Reports</A>&nbsp;&nbsp;\n<A href=\"../riders/add.jsp\">Register</A>&nbsp;&nbsp;\n" +
    "<A href=\"../home/preferences.jsp\">Preferences</A>&nbsp;&nbsp;\n" +
    "<A href=\"../home/help.jsp\">&nbsp;Help&nbsp;</A>&nbsp;&nbsp;\n" +
    "<A href=\"../home/about.jsp\">&nbsp;About&nbsp;</A>\n\n<BR><BR>Welcome to the ZR_Riders mileage database.\n" +
    "<BR><BR><BR>\n\n\n<FORM name=\"login\" method=\"POST\">\n" +
    "Username <INPUT type=\"text\" size=\"10\" maxlength=\"10\" name=\"username\" value=\"\"><BR>\n" +
    "Password <INPUT type=\"password\" size=\"12\" name=\"pasword\" value=\"\">\n" +
    "<INPUT type=\"submit\" name=\"changeYear\" value=\"Login\">\n</FORM>\n</BODY>\n</HTML>"

  import spray.http.MediaTypes._
  import spray.http.StatusCodes

  val loginRoute =
    path("login") {
      get {
        respondWithMediaType(`text/html`) {
          complete(login)
        }
      } ~ post {
        formFields('username.?, 'pasword.?) { (u, p) =>
          hostName { h =>
            optionalCookie(CredCookieName) { cc =>
              setCookie(makeCredentialsCookie(u, p, h, cc, settings)) {
                redirect("/home/index.jsp", StatusCodes.SeeOther)
              }
            }
          }
        }
      }
    }
}
