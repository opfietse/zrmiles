package net.opfietse.zrmiles.web.home

import net.opfietse.zrmiles.SettingsImpl
import spray.http.HttpCookie

trait CookieSupport {
  val CredCookieName = "credentials"

  def makeCredentialsCookie(username: Option[String], password: Option[String], host: String, currentCredCookie: Option[HttpCookie], settings: SettingsImpl): HttpCookie = {
    println(s"U: $username, P: $password, H: $host")
    if (currentCredCookie.getOrElse(HttpCookie("tmp", content = "")).value == "known") {
      HttpCookie(CredCookieName, content = "known", domain = Some(host))
    } else {
      val cookieValue: String = if (username.getOrElse("") == settings.User.Username && password.getOrElse("") == settings.User.Password) "known" else "unknown"
      HttpCookie(CredCookieName, content = cookieValue, domain = Some(host))
    }
  }

}
