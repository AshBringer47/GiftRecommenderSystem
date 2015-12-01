name := """gift-recommender-system"""

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.7"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

pipelineStages := Seq(uglify, digest, gzip)

pipelineStages in Assets := Seq()

pipelineStages := Seq(uglify, digest, gzip)

DigestKeys.algorithms += "sha1"

UglifyKeys.uglifyOps := { js =>
  Seq((js.sortBy(_._2), "concat.min.js"))
}


resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots/"

libraryDependencies ++= Seq(
  ws,
  "com.google.inject" % "guice" % "4.0",
  "javax.inject" % "javax.inject" % "1",
  "org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23",

  "org.webjars" % "bootstrap" % "3.3.6",
  "org.webjars" % "jquery-easing" % "1.3-2",
  "org.webjars" % "jqbootstrapvalidation" % "1.3.6",

  "org.webjars" % "angularjs" % "1.3.15",
  "org.webjars" % "angular-ui-bootstrap" % "0.13.0",
  "org.webjars" % "ngInfiniteScroll" % "1.2.1",

  "org.webjars" % "font-awesome" % "4.5.0",
  "org.webjars" % "animate.css" % "3.3.0",
  "org.webjars" % "retinajs" % "1.3.0",
  "org.webjars.bower" % "owl.carousel" % "2.0.0-beta.3",
  "org.webjars.bower" % "magnific-popup" % "1.0.0",

  "org.mockito" % "mockito-core" % "1.10.19" % "test",
  "jp.t2v" %% "play2-auth"        % "0.14.1",
  "jp.t2v" %% "play2-auth-social" % "0.14.1", // for social login
  "jp.t2v" %% "play2-auth-test"   % "0.14.1" % "test",
  play.Play.autoImport.cache // only when you use default IdContainer
)
