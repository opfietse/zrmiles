package net.opfietse.zrmiles

import akka.actor._
import com.typesafe.config.Config
import net.ceedubs.ficus.Ficus._

import scala.concurrent.duration.FiniteDuration

class SettingsImpl(rootConfig: Config) extends Extension {
  def this(system: ExtendedActorSystem) = this(system.settings.config)

  private val config = rootConfig.getConfig("net.opfietse.zrmiles")

  object Timeout {
    val AskTimeout = config.as[FiniteDuration]("ask-timeout")
    println(s"Timeout: $AskTimeout")
  }
}

object Settings extends ExtensionId[SettingsImpl] with ExtensionIdProvider {
  override def lookup = Settings

  override def createExtension(system: ExtendedActorSystem) =
    new SettingsImpl(system.settings.config)

  def apply(context: ActorContext): SettingsImpl = apply(context.system)
}
