ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.3.7"
)

lazy val root = (project in file("."))
  .settings(
    name := "fp-homework-4"
  )
