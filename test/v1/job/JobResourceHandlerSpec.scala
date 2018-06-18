package v1.job

import javax.inject.Provider

import v1.dal.model.Job
import v1.dal.repostory.JobRepository
import org.mockito.Mockito.when
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play.PlaySpec
import v1.api.JMSRouter

import scala.concurrent.Future

class JobResourceHandlerSpec extends PlaySpec with MockitoSugar {
  "JobResourceHandler lookup" should {
    "return valid job resource if job was provided by the repository" in {

      val routeProvider: Provider[JMSRouter] = mock[Provider[JMSRouter]]

      val jobRepositoryMock: JobRepository = mock[JobRepository]

      val resourceHandler: JobResourceHandler = new JobResourceHandler(routeProvider, jobRepositoryMock)

      val exampleJob = Job(1, "test job", "this is test job", "* * * * *", "ls -lah")

      val expectedResource = Future {
        Some(JobResource(
          exampleJob.id,
          s"http://localhost/job/${exampleJob.id}",
          exampleJob.name,
          exampleJob.description,
          exampleJob.schedule,
          exampleJob.command
        ))
      }

      when(jobRepositoryMock.get(exampleJob.id)) thenReturn Future {
        Some(exampleJob)
      }

      when(routeProvider.get.link(exampleJob.id.toString)) thenReturn s"job/${exampleJob.id.toString}"

      resourceHandler.lookup(exampleJob.id.toString) must equal(expectedResource)
    }
  }
}
