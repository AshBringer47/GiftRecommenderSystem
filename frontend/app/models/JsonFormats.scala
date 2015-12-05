package models

/**
  * @author Ruslan Gunawardana
  */
object JsonFormats {
  import play.api.libs.json.Json

  // Generates Writes and Reads for Feed and User thanks to Json Macros
  implicit val userFormat = Json.format[User]
  implicit val itemFormat = Json.format[Item]
  implicit val suggestionsFormat = Json.format[Suggestions]
}
