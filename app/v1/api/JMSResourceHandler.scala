package v1.api

import v1.job.JobResource

import scala.concurrent.Future

trait JMSResourceHandler {
  def lookup(identifier: String): Future[Option[JobResource]]
}
