package net.opfietse.zrmiles.web.common

object CommonHtml {

  val Header = "<!DOCTYPE HTML PUBLIC \"-//w3c//dtd html 4.0 transitional//en\">" +
    "<HTML><HEAD>\n<META name=\"robots\" content=\"noindex,nofollow\" />\n" +
    "<LINK rel=\"StyleSheet\" href=\"../zrmiles.css\" type=\"text/css\" />\n" +
    "<TITLE>ZR_Riders - Mileage database - Home page</TITLE>\n</HEAD>"

  val Menu = "<A href=\"../home/index.jsp\">Home</A>&nbsp;&nbsp;\n" +
    "<A href=\"../riders/riders.jsp\">Riders</A>&nbsp;&nbsp;\n" +
    "<A href=\"../bikes/bikes.jsp\">Bikes</A>&nbsp;&nbsp;\n" +
    "<!-- <A href=\"../miles/thisyear.jsp\">Miles</A>&nbsp;&nbsp; -->\n" +
    "<A href=\"../reports/index.jsp\">Reports</A>&nbsp;&nbsp;\n" +
    "<A href=\"../riders/add.jsp\">Register</A>&nbsp;&nbsp;\n" +
    "<A href=\"../home/preferences.jsp\">Preferences</A>&nbsp;&nbsp;\n" +
    "<A href=\"../home/help.jsp\">&nbsp;Help&nbsp;</A>&nbsp;&nbsp;\n" +
    "<A href=\"../home/about.jsp\">&nbsp;About&nbsp;</A>"

  val Welcome = "<BR><BR>Welcome to the ZR_Riders mileage database.\n<BR><BR><BR>"

  val Footer = "</HTML>"
}
