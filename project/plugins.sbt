addSbtPlugin("io.spray" % "sbt-revolver" % "0.8.0")

addSbtPlugin("se.marcuslonnberg" % "sbt-docker" % "1.2.0")

addSbtPlugin("org.scalariform" % "sbt-scalariform" % "1.6.0")

resolvers += Resolver.url(
  "bintray-sbt-plugin-releases",
  url("http://dl.bintray.com/content/sbt/sbt-plugin-releases"))(
    Resolver.ivyStylePatterns)

addSbtPlugin("me.lessis" % "bintray-sbt" % "0.1.1")

resolvers += Classpaths.sbtPluginReleases

addSbtPlugin("au.com.onegeek" %% "sbt-dotenv" % "1.1.33")
