package controllers

import javax.inject.{Inject, Singleton}

import exception.GiftsServiceException
import org.slf4j.{Logger, LoggerFactory}
import play.api.Play.current
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.mvc._
import play.modules.reactivemongo.MongoController

import scala.concurrent.Future

/**
  * @author Ruslan Gunawardana
  */
@Singleton
class Gifts @Inject()(ws: WSClient) extends Controller with MongoController {

  private final val logger: Logger = LoggerFactory.getLogger(classOf[Gifts])

  import models.JsonFormats._
  import models._

  private val serviceUrl = (current.configuration getString "giftsRecommenderBackEnd.url").get

  def findGifts = Action.async(parse.json) {
    _.body.validate[User].map { user =>
      val addUserRequestJson = Json.obj(
        "task" -> "addUser",
        "data" -> Json.obj("userProfile" -> user)
      )
      val serviceRequestHolder = (ws url serviceUrl).withHeaders("Content-Type" -> "application/json")
      serviceRequestHolder.post(addUserRequestJson).flatMap { response =>
        if (response.status != OK)
          Future successful Status(response.status)
        else {
          val json = response.json
          val result = (json \ "result").as[String]
          if (result == "Success") {
            val listRequest = makeListRequest(Json.obj(
              "filter" -> Json.obj("minPrice" -> 0, "maxPrice" -> 1000),
              "userId" -> json \ "userId" \ "data"
            ))
            listRequest.map { i =>
              if (i == 0) NoContent
              else {
                //TODO
                Ok("TODO")
              }
            }
          }
          else
            Future successful InternalServerError
        }
      }
    }.getOrElse(Future successful BadRequest("invalid json"))
  }

  private def getSuggestionsRequest(data: JsValue) = {
    val makeListJson = Json.obj(
      "task" -> "makeList",
      "data" -> data
    )
    val serviceRequestHolder = (ws url serviceUrl).withHeaders("Content-Type" -> "application/json")
    serviceRequestHolder.post(makeListJson).map { response =>
      if (response.status != OK)
        Status(response.status)
      else {
        val json = response.json
        val result = (json \ "result").as[String]
        if (result == "Success") {
          (json \ "data" \ "numberOfPages").as[Int]
        }
        else
          InternalServerError
      }
    }
  }

  private def makeListRequest(data: JsValue): Future[Int] = {
    val makeListJson = Json.obj(
      "task" -> "makeList",
      "data" -> data
    )
    val serviceRequestHolder = (ws url serviceUrl).withHeaders("Content-Type" -> "application/json")
    serviceRequestHolder.post(makeListJson).map { response =>
      if (response.status != OK)
        throw new GiftsServiceException(response)
      else {
        val json = response.json
        val result = (json \ "result").as[String]
        if (result == "Success") {
          (json \ "data" \ "numberOfPages").as[Int]
        }
        else
          throw new GiftsServiceException(response)
      }
    }
  }
}
