name := "Bakka"

version := "0.1"

scalaVersion := "2.10.0"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies +=
  "com.typesafe.akka" %% "akka-actor" % "2.1.0"

scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature", "-language:implicitConversions")
