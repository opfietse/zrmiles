package net.opfietse.zrmiles.util

import scala.concurrent.duration._

import akka.actor.ActorRefFactory
import akka.util.Timeout

trait ActorAskPattern {
  import akka.pattern.ask
  implicit val AskTimeout = Timeout(15 seconds)
}

trait ActorRefFactorySupport {
  implicit def actorRefFactory: ActorRefFactory
}

trait ExecutionContextSupport {
  import scala.concurrent.ExecutionContext
  implicit def executionContext: ExecutionContext
}
