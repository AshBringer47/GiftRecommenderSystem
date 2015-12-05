package exception

import play.api.libs.ws.WSResponse

/**
  * @author Ruslan Gunawardana
  */
class GiftsServiceException(response: WSResponse) extends Exception {

}
