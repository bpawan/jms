package v1.api

import javax.inject.Inject

import v1.dal.model.JMSDatabaseModel
import play.api.mvc.{BaseController, ControllerComponents, DefaultActionBuilder}
import v1.job.JMSResourceModel

class JMSBaseController @Inject() (jmsControllerComponents: JMSControllerComponents)
  extends BaseController with RequestMarkerContext {
  override protected def controllerComponents: ControllerComponents = jmsControllerComponents

  def jmsAction: DefaultActionBuilder = jmsControllerComponents.actionBuilder

  def jmsResourceHandler: JMSResourceHandler = jmsControllerComponents.jmsResourceHandler
}
