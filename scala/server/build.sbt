name := "starter"
organization := "org.starter"

scalaVersion := "2.13.8"

Test / parallelExecution := false

Compile / packageBin / mainClass := Some("Application")
Compile / run / mainClass := Some("Application")

val http4sVersion = "1.0-234-d1a2b53"
val circeVersion = "0.13.0"

libraryDependencies ++= Seq(
  // Config
  "com.github.pureconfig" %% "pureconfig" % "0.17.4",

  // Logging
  "com.typesafe.scala-logging" %% "scala-logging" % "3.9.5",
  "ch.qos.logback" % "logback-classic" % "1.4.7",

  // HTTP
  "org.http4s" %% "http4s-dsl" % http4sVersion,
  "org.http4s" %% "http4s-blaze-server" % http4sVersion,
  "org.http4s" %% "http4s-blaze-client" % http4sVersion,
  "org.http4s" %% "http4s-circe" % http4sVersion,
  "io.circe" %% "circe-generic" % circeVersion,

  // JSON
  "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.14.2",

  // Testing
  "org.scalatest" %% "scalatest" % "3.2.15" % Test,
  "org.mockito" %% "mockito-scala" % "1.17.12" % Test,
  "org.mockito" %% "mockito-scala-scalatest" % "1.17.12" % Test
)

enablePlugins(AssemblyPlugin)

assembly / artifact := {
  val art = (assembly / artifact).value
  art.withClassifier(Some("assembly"))
}

addArtifact(assembly / artifact, assembly)

// WARNING: Only valid for JDK 8
// Discard module-info.class files as they are unused in JDK 8
assembly / assemblyMergeStrategy := {
  case x if x.endsWith("module-info.class") => MergeStrategy.discard
  case x =>
    val oldStrategy = (assembly / assemblyMergeStrategy).value
    oldStrategy(x)
}
