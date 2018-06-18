package controllers

import v1.dal.model.Job
import v1.dal.repostory.JobRepository
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import org.scalatestplus.play.guice._
import play.api.libs.json._
import play.api.test.Helpers._
import play.api.test._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class JobsControllerSpec
  extends PlaySpec
    with GuiceOneAppPerTest
    with Injecting
    with MockitoSugar {

  "JobController GET" should {
    "returns list of all jobs available" in {

      val jobRepositoryMock = mock[JobRepository]

      val testJob = Job(1, "name", "description", "schedule", "command")

      when(jobRepositoryMock.getAll) thenReturn Future {
        Seq(testJob)
      }

      val jobsController = new JobsController(stubControllerComponents(), jobRepositoryMock)

      val jobs = jobsController.index().apply(FakeRequest(GET, "/"))

      status(jobs) mustBe OK
      contentType(jobs) mustBe Some("application/json")
      contentAsString(jobs) must include(Json.toJson(List(testJob)).toString())
    }
  }
}
