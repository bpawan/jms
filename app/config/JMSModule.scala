package config

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import v1.dal.repostory.{JobRepository, JobRepositoryImpl}
import play.api.{Configuration, Environment}

class JMSModule(environment: Environment, configuration: Configuration)
  extends AbstractModule with ScalaModule{
  override def configure(): Unit = {
    bind[JobRepository].to[JobRepositoryImpl]
  }
}
