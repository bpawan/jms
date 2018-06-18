package v1.dal.model

sealed trait JMSDatabaseModel
case class Job(id: Long, name: String, description: String, schedule: String, command: String) extends JMSDatabaseModel
