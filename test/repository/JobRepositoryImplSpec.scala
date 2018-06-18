package repository

import akka.actor.ActorSystem
import v1.dal.model.Job
import v1.dal.repostory.{JobRepositoryImpl, MysqlExecutionContextImpl}
import org.scalatest.BeforeAndAfter
import org.scalatestplus.play.PlaySpec
import org.scalatestplus.play.guice.GuiceOneAppPerTest
import play.api.Application
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.Await
import scala.concurrent.duration.DurationInt

class JobRepositoryImplSpec extends PlaySpec with GuiceOneAppPerTest with BeforeAndAfter {

  implicit val executionContext: MysqlExecutionContextImpl = new MysqlExecutionContextImpl(ActorSystem.create("job-repository-test"))

  "JobRepositoryImpl getAll" should {
    "returns list of all jobs stored in the database" in {
      val appToDatabaseConfigProvider = Application.instanceCache[DatabaseConfigProvider]

      val databaseConfigProvider = appToDatabaseConfigProvider(app)
      val repository = new JobRepositoryImpl(databaseConfigProvider)

      val job1 = Job(1, "test", "this is test job", "* * * * *", "sbt test")

      Await.result(repository.insert(job1), 1 second)
      val storedJobs = Await.result(repository.getAll, 1 second)

      assert(storedJobs.head == job1)
    }
  }
}
