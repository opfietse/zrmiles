package net.opfietse
package zrmiles
package main

import akka.actor._
import akka.io._

import spray.can._
import spray.can.Http._

import route.RouteActor

trait ZrmilesApp extends App {

  implicit val system = ActorSystem("zrmiles")

  val service = system.actorOf(RouteActor.props, RouteActor.name)

  val httpHost = "0.0.0.0"
  val httpPort = Option(System.getenv("PORT")).getOrElse("8080").toInt

  IO(Http) ! Bind(listener = service, interface = httpHost, port = httpPort)
}

object Main extends App with ZrmilesApp {
}
