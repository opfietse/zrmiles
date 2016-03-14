package net.opfietse.zrmiles.db

import slick.driver.MySQLDriver.api._

object Db {
  val db = Database.forConfig("mysql")
}
