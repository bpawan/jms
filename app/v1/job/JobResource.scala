package v1.job

import play.api.libs.json.{Json, Writes}

sealed trait JMSResourceModel
case class JobResource(id: Long, link: String, name: String, description: String, schedule: String, command: String) extends JMSResourceModel

object JobResource {
  implicit val jobWrites: Writes[JobResource] = (resource: JobResource) => Json.obj(
    "id" -> resource.id,
    "link" -> resource.link,
    "name" -> resource.name,
    "description" -> resource.description,
    "schedule" -> resource.schedule,
    "command" -> resource.command
  )
}
