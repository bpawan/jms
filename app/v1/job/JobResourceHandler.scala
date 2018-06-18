package v1.job

import javax.inject.{Inject, Provider}

import v1.dal.model.Job
import v1.dal.repostory.JobRepository
import v1.api.{JMSResourceHandler, JMSRouter}

import scala.concurrent.{ExecutionContext, Future}

class JobResourceHandler @Inject()(routerProvider: Provider[JMSRouter], jobRepository: JobRepository)
                                  (implicit ec: ExecutionContext) extends JMSResourceHandler {

  private def toJobResource(job: Job): JobResource = {
    JobResource(job.id, routerProvider.get.link(job.id.toString), job.name, job.description, job.schedule, job.command)
  }

  override def lookup(id: String): Future[Option[JobResource]] = {
    val jobFuture = jobRepository.get(Integer.parseInt(id))
    jobFuture map { mayBeJob =>
      mayBeJob.map(toJobResource)
    }
  }
}
