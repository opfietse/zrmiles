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

resolvers ++= Seq("Base64 Repo" at "http://dl.bintray.com/content/softprops/maven",
                  "Sonatype OSS Releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")

libraryDependencies ++= {
  val akkaVersion = "2.4.1"
  val sprayVersion = "1.3.3"
  Seq(
    "com.typesafe.akka"         %% "akka-actor"      % akkaVersion,
    "com.typesafe.akka"         %% "akka-slf4j"      % akkaVersion,
    "io.spray"                  %% "spray-caching"   % sprayVersion,
    "io.spray"                  %% "spray-can"       % sprayVersion,
    "io.spray"                  %% "spray-routing"   % sprayVersion,
    "joda-time"                 %  "joda-time"       % "2.9.2",
    "com.iheart"                %% "ficus"           % "1.2.0",
    "ch.qos.logback"            %  "logback-classic" % "1.1.4",
    "com.typesafe.slick"        %% "slick"           % "3.1.1",
    "mysql"                     %  "mysql-connector-java" % "5.1.31"
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
