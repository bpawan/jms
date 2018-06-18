package v1.dal.repostory

import akka.actor.ActorSystem
import com.google.inject.Inject
import play.api.libs.concurrent.CustomExecutionContext

import scala.concurrent.ExecutionContext

trait MysqlExecutionContext extends ExecutionContext

class MysqlExecutionContextImpl @Inject()(system: ActorSystem)
  extends CustomExecutionContext(system, "mysql-executor") with MysqlExecutionContext