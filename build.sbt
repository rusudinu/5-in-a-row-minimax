ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
libraryDependencies += "org.scala-lang" % "scala-reflect" % scalaVersion.value
libraryDependencies += "org.scala-lang.modules" %% "scala-parallel-collections" % "1.0.4"

lazy val root = (project in file("."))
  .settings(
    name := "fp-homework-4"
  )
