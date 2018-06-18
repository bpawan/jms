package v1.api

import javax.inject.Inject

import net.logstash.logback.marker.LogstashMarker
import play.api.http.{FileMimeTypes, HttpVerbs}
import play.api.i18n.{Langs, MessagesApi}
import play.api.mvc._
import play.api.{Logger, MarkerContext}

import scala.concurrent.{ExecutionContext, Future}

trait JMSRequestHeader extends MessagesRequestHeader with PreferredMessagesProvider

class JMSRequest[A](request: Request[A], val messagesApi: MessagesApi)
  extends WrappedRequest(request) with JMSRequestHeader

// Create a marker to log the request with the information about the request.
trait RequestMarkerContext {

  import net.logstash.logback.marker.Markers

  private def marker(tuple: (String, Any)): LogstashMarker = Markers.append(tuple._1, tuple._2)

  private implicit class RichLogStashMarker(marker1: LogstashMarker) {
    def &&(marker2: LogstashMarker): LogstashMarker = marker1.and(marker2)
  }

  implicit def requestHeaderToMarkerContext(implicit request: RequestHeader): MarkerContext = {
    MarkerContext {
      marker("id" -> request.id) && marker("host" -> request.host) && marker("remoteAddress" -> request.remoteAddress)
    }
  }
}

class JMSActionBuilder @Inject()(messagesApi: MessagesApi, playBodyParsers: PlayBodyParsers)
                                (implicit val executionContext: ExecutionContext)
  extends ActionBuilder[JMSRequest, AnyContent]
    with RequestMarkerContext
    with HttpVerbs {

  type JMSRequestBlock[A] = JMSRequest[A] => Future[Result]

  private val logger = Logger(this.getClass)

  override def parser: BodyParser[AnyContent] = playBodyParsers.anyContent

  override def invokeBlock[A](request: Request[A], block: JMSRequestBlock[A]): Future[Result] = {
    implicit val markerContext: MarkerContext = requestHeaderToMarkerContext(request)
    logger.trace(s"invokeBlock: ")

    val future = block(new JMSRequest[A](request, messagesApi))

    future.map { result =>
      request.method match {
        case GET | HEAD =>
          result.withHeaders("Cache-Control" -> s"max-age: 100")
        case _ => result
      }
    }
  }
}

case class JMSControllerComponents @Inject()(
                                    jMSActionBuilder: JMSActionBuilder,
                                    jmsResourceHandler: JMSResourceHandler,
                                    actionBuilder: DefaultActionBuilder,
                                    parsers: PlayBodyParsers,
                                    messagesApi: MessagesApi,
                                    langs: Langs,
                                    fileMimeTypes: FileMimeTypes,
                                    executionContext: ExecutionContext
                                  ) extends ControllerComponents

