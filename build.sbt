name := "Spark Window Streaming"
version := "1.0"
scalaVersion := "2.11.11"
libraryDependencies ++= {
  Seq(
    "org.apache.kafka" %% "kafka" % "1.0.0",
    "org.apache.kafka" % "kafka-clients" % "1.0.0",
    "org.apache.spark" %% "spark-core" % "2.2.1" % "provided",
    "org.apache.spark" %% "spark-sql" % "2.2.1",
    "org.apache.spark" %% "spark-streaming" % "2.2.1",
    "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.2.1"
  )
}