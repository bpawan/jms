package v1.dal.repostory

import com.google.inject.Inject
import v1.dal.model.Job
import v1.dal.table.JobTable._
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import scala.concurrent.Future

trait JobRepository {
  def getAll: Future[Seq[Job]]
  def insert(job: Job): Future[Unit]
  def get(id: Long): Future[Option[Job]]
}

class JobRepositoryImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)(implicit ec: MysqlExecutionContextImpl)
  extends JobRepository with HasDatabaseConfigProvider[JdbcProfile] {

  import profile.api._

  def getAll: Future[Seq[Job]] = db.run(jobs.result)

  def insert(job: Job): Future[Unit] = {
    db.run(jobs += job).map {_ => ()}
  }

  def get(id: Long): Future[Option[Job]] = {
    db.run(jobs.filter(_.id === id).result.headOption)
  }
}
