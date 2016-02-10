name := "zrmiles"

version := "1.0"

scalaVersion := "2.11.7"

scalacOptions := Seq(
  //"-encoding:",
  "utf8",
  "-target:jvm-1.8",
  "-feature",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  "-deprecation",
  "-Xlog-reflective-calls")

resolvers ++= Seq("Base64 Repo" at "http://dl.bintray.com/content/softprops/maven")

libraryDependencies ++= {
  val akkaVersion = "2.4.1"
  val sprayVersion = "1.3.3"
  Seq(
    "com.typesafe.akka"         %% "akka-actor"   % akkaVersion,
    "com.typesafe.akka"         %% "akka-slf4j"   % akkaVersion,
//    "com.typesafe.akka"         %% "akka-contrib"   % akkaVersion,
    "io.spray"         %% "spray-caching"   % sprayVersion,
    "io.spray"         %% "spray-can"   % sprayVersion,
//    "io.spray"         %% "spray-client"   % sprayVersion,
    "io.spray"         %% "spray-routing"   % sprayVersion
//    "io.spray"         %% "spray-json"   % sprayVersion,
//    "joda-time"        %% "joda-time"      % "2.0",
//      "org.joda"       %% "joda-convert"   % "1.1"
  )
}
