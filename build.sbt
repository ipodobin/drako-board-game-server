name := "drako-board-game"

organization := "ipodobin"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.13.8"

lazy val root = (project in file("."))
  .settings(
    name := "drako-board-game",
    idePackagePrefix := Some("org.drako")
  )

libraryDependencies ++= {
  val akkaVersion = "2.6.19"
  val akkaHttpVersion = "10.2.9"
  val logbackVersion = "1.2.11"
  val scalaTestVersion = "3.2.12"
  val junitVersion = "4.13.2"
  Seq(
    "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
    "com.typesafe.akka" %% "akka-actor" % akkaVersion,
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-core" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
    "com.typesafe.akka" %% "akka-serialization-jackson" % akkaVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-testkit" % akkaVersion % Test,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % akkaVersion % Test,
    // LOGGING
    "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    // TESTING
    "org.scalatest" %% "scalatest" % scalaTestVersion % Test,
    "junit" % "junit" % junitVersion % Test
  )
}

fork in run := true
