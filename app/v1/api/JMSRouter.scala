package v1.api

import javax.inject.Inject

import play.api.routing.Router.Routes
import play.api.routing.SimpleRouter
import play.api.routing.sird._

class JMSRouter @Inject()(controller: JMSController) extends SimpleRouter {
  val prefix = "v1/job"

  def link(id: String): String = {
    import com.netaporter.uri.dsl._

    val url = prefix / id
    url.toString
  }

  override def routes: Routes = {
    case GET(p"/$id") => controller.show(id)
  }
}
