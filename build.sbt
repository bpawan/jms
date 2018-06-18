name := """jis"""
organization := "bpawan.com"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies += guice

libraryDependencies += "com.netaporter" %% "scala-uri" % "0.4.16"
libraryDependencies += "net.logstash.logback" % "logstash-logback-encoder" % "5.1"
libraryDependencies += "net.codingwell" %% "scala-guice" % "4.2.1"


libraryDependencies += "mysql" % "mysql-connector-java" % "8.0.11"
libraryDependencies += "com.typesafe.play" %% "play-slick" % "3.0.3"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.3"



libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test
libraryDependencies += "org.mockito" % "mockito-core" % "2.18.3" % Test
libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "4.2.0" % "test")
scalacOptions in Test ++= Seq("-Yrangepos")

// Use the test config while running the tests.
javaOptions in Test += "-Dconfig.file=conf/application.test.conf"