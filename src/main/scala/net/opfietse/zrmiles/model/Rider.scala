package net.opfietse.zrmiles.model

case class Rider(
  id: Int,
  firstName: String,
  lastName: String,
  emailAddress: Option[String],
  streetAddress: Option[String],
  username: String,
  password: Option[String],
  role: Int
)

case class Riderr(
  id: Int,
  firstName: String,
  lastName: String,
  emailAddress: Option[String],
  streetAddress: Option[String],
  username: String,
  password: Option[String],
  role: Int
)

object RiderMarshalling {
  //  implicit val riderFormat = json
}
