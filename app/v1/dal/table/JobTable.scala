package v1.dal.table

import v1.dal.model.Job
import slick.jdbc.MySQLProfile.api._
import slick.lifted.Tag

class JobTable(tag: Tag) extends Table[Job](tag, "job") {
  def * = (id, name, description, schedule, command) <> (Job.tupled, Job.unapply)

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

  def name = column[String]("name")

  def description = column[String]("description")

  def schedule = column[String]("schedule")

  def command = column[String]("command")
}

object JobTable {
  val jobs = TableQuery[JobTable]
}