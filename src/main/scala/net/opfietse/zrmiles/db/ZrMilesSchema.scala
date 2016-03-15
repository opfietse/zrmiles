package net.opfietse.zrmiles.db

import net.opfietse.zrmiles.model.{ Motorcycle, Rider }

import scala.concurrent.ExecutionContext.Implicits.global

import slick.driver.MySQLDriver.api._

object ZrMilesSchema {
  // Definition of the SUPPLIERS table
  //  class Riders(tag: Tag) extends Table[(Int, String, String, Option[String], Option[String], String, Option[String], Int)](tag, "ZR_RIDERS") {
  //    def id = column[Int]("ID", O.PrimaryKey) // This is the primary key column
  //    def firstName = column[String]("FIRST_NAME")
  //    def lastName = column[String]("LAST_NAME")
  //    def emailAddress = column[Option[String]]("EMAIL_ADDRESS")
  //    def streetAddress = column[Option[String]]("STREET_ADDRESS")
  //    def username = column[String]("USER_NAME")
  //    def password = column[Option[String]]("USER_PASSWORD")
  //    def role = column[Int]("USER_ROLE")
  //    // Every table needs a * projection with the same type as the table's type parameter
  //    def * = (id, firstName, lastName, emailAddress, streetAddress, username, password, role)
  //  }
  class Riders(tag: Tag) extends Table[Rider](tag, "ZR_RIDERS") {
    def id = column[Int]("ID", O.PrimaryKey) // This is the primary key column
    def firstName = column[String]("FIRST_NAME")
    def lastName = column[String]("LAST_NAME")
    def emailAddress = column[Option[String]]("EMAIL_ADDRESS")
    def streetAddress = column[Option[String]]("STREET_ADDRESS")
    def username = column[String]("USER_NAME")
    def password = column[Option[String]]("USER_PASSWORD")
    def role = column[Int]("USER_ROLE")
    // Every table needs a * projection with the same type as the table's type parameter
    def * = (id, firstName, lastName, emailAddress, streetAddress, username, password, role) <> (Rider.tupled, Rider.unapply _)
  }
  val riders = TableQuery[Riders]

  // Definition of the ZR_MOTORCYCLE table
  class Motorcycles(tag: Tag) extends Table[Motorcycle](tag, "ZR_MOTORCYCLES") {
    def id = column[Int]("ID", O.PrimaryKey)
    def riderId = column[Int]("RIDER_ID")
    def make = column[String]("MAKE")
    def model = column[String]("MODEL")
    def year = column[Int]("YEAR")
    def distanceUnit = column[Int]("DISTANCE_UNIT")
    def * = (id, riderId, make, model, year, distanceUnit) <> (Motorcycle.tupled, Motorcycle.unapply _)
    // A reified foreign key relation that can be navigated to create a join
    def rider = foreignKey("RIDER_FK", riderId, riders)(_.id)
  }
  val motorcycles = TableQuery[Motorcycles]

  // Definition of the COFFEES table
  class Miles(tag: Tag) extends Table[(Int, Int, java.sql.Date, Int, Option[Int], Option[String])](tag, "ZR_MILES") {
    def id = column[Int]("ID", O.PrimaryKey)
    def motorcycleId = column[Int]("MOTORCYCLE_ID")
    def milesDate = column[java.sql.Date]("MILES_DATE")
    def odometer = column[Int]("ODOMETER_READING")
    def correctionMiles = column[Option[Int]]("CORRECTION_MILES")
    def comment = column[Option[String]]("USER_COMMENT")
    def * = (id, motorcycleId, milesDate, odometer, correctionMiles, comment)
    // A reified foreign key relation that can be navigated to create a join
    def motorcycle = foreignKey("MOTORCYCLE_FK", motorcycleId, motorcycles)(_.id)
  }
  val miles = TableQuery[Miles]
}
