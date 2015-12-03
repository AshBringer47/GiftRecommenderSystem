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

  private val serviceUrl = (current.configuration getString "giftsRecommenderBackEnd.url").get

  def findGifts = Action.async(parse.json) {
    _.body.validate[User].map {
      user =>
        val addUserRequestJson = Json.obj(
          "task" -> "addUser",
          "data" -> Json.obj("userProfile" -> user)
        )
        val serviceRequestHolder = (ws url serviceUrl).withHeaders("Content-Type" -> "application/json")
        serviceRequestHolder.post(addUserRequestJson).map {
          response =>
            if (response.status != OK)
              Status(response.status)
            else {
              val json = response.json
              val result = (json \ "result").as[String]
              if (result == "Success")
                makeListRequest(Json.obj(
                  "filter" -> Json.obj("minPrice" -> 0, "maxPrice" -> 1000),
                  "userId" -> json \ "data" \ "userId"
                ))
              else
                InternalServerError
            }
        }
    }.getOrElse(Future successful BadRequest("invalid json"))
  }

  private def makeListRequest(data: JsValue) : Status = {
    val makeListJson = Json.obj(
      "task" -> "makeList",
      "data" -> data
    )
    val serviceRequestHolder = (ws url serviceUrl).withHeaders("Content-Type" -> "application/json")
    serviceRequestHolder.post(makeListJson).map {
      response =>
        if (response.status != OK)
          Status(response.status)
        else {
          val json = response.json
          val result = (json \ "result").as[String]
          if (result == "Success") {
            val pages = (json \ "data" \ "numberOfPages").as[Int]

          }
          else
            InternalServerError
        }
    }
  }

  private def getSuggestionsRequest(data: JsValue) : Status = {
    val makeListJson = Json.obj(
      "task" -> "makeList",
      "data" -> data
    )
    val serviceRequestHolder = (ws url serviceUrl).withHeaders("Content-Type" -> "application/json")
    serviceRequestHolder.post(makeListJson).map {
      response =>
        if (response.status != OK)
          Status(response.status)
        else {
          val json = response.json
          val result = (json \ "result").as[String]
          if (result == "Success") {
            val pages = (json \ "data" \ "numberOfPages").as[Int]

          }
          else
            InternalServerError
        }
    }
  }
}
