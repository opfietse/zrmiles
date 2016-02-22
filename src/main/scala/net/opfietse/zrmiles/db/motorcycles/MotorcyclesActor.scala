package net.opfietse.zrmiles
package db
package motorcycles

import scala.concurrent.Future
import akka.actor._

import slick.driver.MySQLDriver.api._

import ZrMilesSchema._
import model._
import util.ActorRefFactorySupport

object MotorcyclesActor {
  def props = Props[MotorcyclesActor]
  def name = "riders-actor"

  sealed trait Command
  case object GetAllMotorcycles extends Command
  case class GetMotorcyclesForRiderId(riderId: Int) extends Command
}

class MotorcyclesActor extends Actor with ActorLogging {
  import MotorcyclesActor._

  implicit val executionContext = context.dispatcher

  def receive: Receive = {
    case GetAllMotorcycles =>
      val bikes = getAllMotorcycles
      sender ! bikes
  }

  def getAllMotorcycles: Future[Seq[Motorcycle]] = {
    log.info("Run GetAllMotorcycles ++++++++++++")
    val db = Database.forConfig("mysql")

    try {
      //    val dbRidersFirstNames = for (r <- riders) yield Rider(r.id, r.firstName, r.lastName, r.streetAddress, r.emailAddress, r.username, r.password, r.role)
      val dbMotorcycles = for (m <- motorcycles) yield m
      val q = dbMotorcycles.result
      val f: Future[Seq[Motorcycle]] = db.run(q)

      val res = for (names <- f) yield names
      res
    } finally db.close
  }
}

trait MotorcyclesActorProvider {
  def motorcyclesActor: ActorRef
}

trait SlickMotorcyclesActorProvider extends MotorcyclesActorProvider with ActorRefFactorySupport {
  lazy val motorcyclesActor = actorRefFactory.actorOf(MotorcyclesActor.props, MotorcyclesActor.name)
}
