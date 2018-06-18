package v1.api

import javax.inject.Inject

import play.api.Logger
import play.api.libs.json.Json
import play.api.mvc._
import scala.concurrent.ExecutionContext

class JMSController @Inject() (cc: JMSControllerComponents)(implicit executionContext: ExecutionContext)
  extends JMSBaseController(cc) {

  private val logger = Logger(this.getClass)

  def show(id: String): Action[AnyContent] = jmsAction.async { implicit request =>
    logger.trace(s"show: id = $id")
    jmsResourceHandler.lookup(id).map { resourceModel =>
      Ok(Json.toJson(resourceModel))
    }
  }
}
