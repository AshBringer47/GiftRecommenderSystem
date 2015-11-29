package controllers

import javax.inject.Inject
import javax.inject.Singleton

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.api.Play.current
import play.api.libs.json._
import play.modules.reactivemongo.MongoController
import play.modules.reactivemongo.json.collection.JSONCollection

import scala.concurrent.Future

/**
 * @author Ruslan Gunawardana
 */
@Singleton
class Gifts @Inject() (ws: WSClient) extends Controller with MongoController {

  def collection: JSONCollection = db.collection[JSONCollection]("users")

  import models._
  import models.JsonFormats._

  def findGifts(user: User) = Action.async(parse.json) {
    val backendUrl = (current.configuration getString "giftsRecommenderBackEnd.url").get
    val addUserTask = Json.obj(
      "task" -> "addUser",
      "data" -> Json.obj(
        "userProfile" -> user
        )
      )
    val s = (ws url backendUrl)
      .withHeaders("Accept" -> "application/json")
      .withRequestTimeout(10000)
      .post(addUserTask)
    //Ok("s")
  }
}
