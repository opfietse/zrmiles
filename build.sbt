import sbtassembly.AssemblyPlugin.autoImport._
import sbtdocker.DockerPlugin.autoImport._
import sbtdocker.mutable.Dockerfile
import sbtdocker.DockerPlugin

name := "zrmiles"

organization := "net.opfietse"

version := "1.0"

scalaVersion := "2.11.7"

mainClass in Compile := Some("net.opfietse.zrmiles.main.Main")

scalacOptions := Seq(
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
    "io.spray"         %% "spray-caching"   % sprayVersion,
    "io.spray"         %% "spray-can"   % sprayVersion,
    "io.spray"         %% "spray-routing"   % sprayVersion
  )
}

enablePlugins(DockerPlugin)

docker <<= (docker dependsOn assembly)

dockerfile in docker := {
  val artifact = (outputPath in assembly).value
  val artifactTargetPath = s"/app/${artifact.name}"
  new Dockerfile {
    from("anapsix/alpine-java:jre8")
    add(artifact, artifactTargetPath)
    entryPoint("java", "-jar", artifactTargetPath)
  }
}
