package net.opfietse.zrmiles.model

case class Motorcycle(
  id: Int,
  riderId: Int,
  make: String,
  model: String,
  year: Option[Int],
  distanceUnit: Int
)

case class MotorcycleWithRider(
  id: Int,
  riderId: Int,
  make: String,
  model: String,
  year: Option[Int],
  distanceUnit: Int,
  firstName: String,
  lastName: String
)
