package net.opfietse.zrmiles
package web
package riders

import org.slf4j.LoggerFactory

import scala.concurrent._
import scala.util._

import akka.actor.{ ActorLogging, ActorRef }
import akka.pattern.ask
import spray.http.MediaTypes._
import spray.http.{ FormData, StatusCodes }
import spray.routing.HttpService

import db.riders._
import model._
import common.CommonHtml._
import db.riders.RidersActor._
import net.opfietse.zrmiles.util._

trait RidersRoute extends HttpService
    with ExecutionContextSupport
    with ActorAskPattern
    with SlickRidersActorProvider {

  def log = LoggerFactory.getLogger("RiderRoute")

  val ridersRoute = get {
    path("riders" / "riders.jsp") {
      pathEndOrSingleSlash {
        onComplete((ridersActor ? GetAllRiders).mapTo[Future[Seq[Rider]]].flatMap(riders => riders)) {
          case Success(riders) =>
            val html = Header + "<BODY>" + Menu + "<BR/><BR/>All riders in the database.<BR/><BR />" +
              makeRidersTable(riders) + instructions + "</BODY>" + Footer
            respondWithMediaType(`text/html`) {
              complete(html)
            }

          case Failure(t) =>
            log.error("Error retreiving riders", t)
            respondWithMediaType(`text/html`) {
              complete("Oops .....")
            }
        }
      }
    } ~
      path("riders" / "add.jsp") {
        pathEndOrSingleSlash {

          val html = Header + "<BODY>" + Menu + riderForm(None, None, None, None) + "</BODY>" + Footer
          respondWithMediaType(`text/html`) {
            complete(html)
          }
        }
      }
  } ~
    post {
      path("riders" / "add.jsp") {
        pathEndOrSingleSlash {
          formFields('firstName, 'lastName, 'emailAddress.?, 'streetAddress.?) { (fn, ln, ea, sa) =>
            complete(fn)
          }
        }
      }
    }

  //------ Add -------

  def welcomeNewRider(firstName: String, lastName: String, streetAddress: Option[String]) = {
    val name = s"Welcome, $firstName $lastName"
    val from = streetAddress match {
      case Some(address) => s" from $streetAddress"
      case None => ""
    }
  }

  def riderForm(firstName: Option[String], lastName: Option[String], emailAddress: Option[String], streetAddress: Option[String]) = {
    val firstNameFieldValue = firstName match {
      case Some(name) => name
      case None => ""
    }

    val lastNameFieldValue = ""
    val emailAddressFieldValue = ""
    val streetAddressFieldValue = ""

    // action="add.jsp"

    "<P>Please enter your data below.</P><FORM name=\"addRiderForm\" method=\"POST\">" +
      "<TABLE border=\"0\"><CAPTION><EM>Rider</EM></CAPTION>" +
      "<TR><TD>" +
      "First name *" +
      "</TD><TD>" +
      "<INPUT type=\"text\" size=\"20\" maxlength=\"20\" name=\"firstName\" value=\"" + firstNameFieldValue + "\">" +
      "</TD></TR>" +
      "<TR><TD>" +
      "Last name *" +
      "</TD><TD>" +
      "<INPUT type=\"text\" size=\"40\" maxlength=\"40\" name=\"lastName\" value=\"" + lastNameFieldValue + "\">" +
      "</TD></TR>" +
      "<TR><TD>" +
      "Email address" +
      "</TD><TD>" +
      "<INPUT type=\"text\" size=\"50\" maxlength=\"50\" name=\"emailAddress\" value=\"" + emailAddressFieldValue + "\">" +
      "</TD></TR>" +
      "<TR><TD>" +
      "Location" +
      "</TD><TD>" +
      "<INPUT type=\"text\" size=\"50\" maxlength=\"50\" name=\"streetAddress\" value=\"" + streetAddressFieldValue + "\">" +
      "</TD></TR>" +
      "<TR>" +
      "<TD colspan=\"2\" align=\"center\"><INPUT type=\"submit\" name=\"addRider\" value=\"Add\">" +
      "</TR>" +
      "</TABLE>" +
      "</FORM>" +
      "* = Required."
  }

  /*
    <% if (newRider.getStreetAddress() != null) { %>
    from <%= newRider.getStreetAddress() %>.<BR><BR>
  <BR><A href="../bikes/add.jsp?riderId=<%=newRider.getId()%>>Click here to enter your bike(s)</A>
<% }
}
  } else {
%>
<P>
Please enter your data below.
</P>
<FORM name="addRiderForm" method="POST">
<TABLE border="0">
  <CAPTION><EM>Rider</EM></CAPTION>
    <TR><TD>
      First name *
    </TD><TD>
      <INPUT type="text" size="20" maxlength="20" name="firstName"
             value="<% if (firstName != null) {%><%= firstName %><% } %>">
      </TD></TR>
      <TR><TD>
        Last name *
      </TD><TD>
        <INPUT type="text" size="40" maxlength="40" name="lastName"
               value="<% if (lastName != null) {%><%= lastName %><% } %>">
        </TD></TR>
        <TR><TD>
          Email address
        </TD><TD>
          <INPUT type="text" size="50" maxlength="50" name="emailAddress"
                 value="<% if (emailAddress != null) {%><%= emailAddress %><% } %>">
          </TD></TR>
          <TR><TD>
            Location
          </TD><TD>
            <INPUT type="text" size="50" maxlength="50" name="streetAddress"
                   value="<% if (streetAddress != null) {%><%= streetAddress %><% } %>">
            </TD></TR>
            <TR>
              <TD colspan="2" align="center"><INPUT type="submit" name="addRider" value="Add">
              </TR>
              </TABLE>
            </FORM>
            * = Required.
            <% } %>
            </BODY>
          </HTML>

*/

  //-------

  //  def makeRidersTable(riders: List[Riderr]): String = {
  //    val riderTableEntries = for (r <- riders) yield "<td>" + r.id + "</td>" + "<td>" + r.firstName + "</td>"
  //    "<table border=\"1\"> summary=\"Lists the riders currently in the database\"><CAPTION><EM>Riders</EM></CAPTION>" + riderTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  //  }

  val instructions = "<BR>Click on the <B>Id</B> to show/add the bikes for that rider.<BR>Click on the <B>last name</B> to CHANGE or DELETE that rider."

  def makeRidersTable(riders: Seq[Rider]): String = {
    val tableHeader = "<th>Id</th><th>First name</th><th>Last name</th><th>Email address</th><th>Location</th>"
    val riderTableEntries = for (r <- riders) yield "<td>" + makeAddBikeLink(r) + "</td>" +
      "<td>" + r.firstName + "</td>" +
      "<td>" + makeUpdateRiderLink(r) + "</td>" +
      "<td>" + r.emailAddress.getOrElse("") + "</td>" +
      "<td>" + r.streetAddress.getOrElse("") + "</td>"
    "<table border=\"1\" summary=\"Lists the riders currently in the database\"><CAPTION><EM>Riders</EM></CAPTION>" + tableHeader + riderTableEntries.mkString("<tr>", "</tr><tr>", "</tr>") + "</table>"
  }

  def makeAddBikeLink(rider: Rider) = "<a href=\"bikes/add.jsp?riderId=" + rider.id + "\">" + rider.id + "</a>"
  def makeUpdateRiderLink(rider: Rider) = "<a href=\"riders/update.jsp?riderId=" + rider.id + "\">" + rider.lastName + "</a>"
}
