package models

/**
  * @author Ruslan Gunawardana
  */
case class User(age: Int,
                sex: String,
                firstName: String,
                lastName: String,
                hobbies: Set[String],
                userType: Map[String, Float],
                active: Boolean)

