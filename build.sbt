name := """my-td-client-scala-sample"""

version := "1.0"

scalaVersion := "2.11.7"

// Change this to another test framework if you prefer
libraryDependencies ++= Seq(
  "org.msgpack" % "msgpack-core" % "0.8.7",
  "com.google.guava" % "guava" % "19.0",
  "com.treasuredata.client" % "td-client" % "0.7.19",
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)

// Uncomment to use Akka
//libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.11"

