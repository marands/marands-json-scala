inThisBuild(
  Seq(
    organization := "io.marands",
    homepage := Some(url("https://github.com/marands/marands-json-scala")),
    licenses := Seq("Apache 2" -> new URL("http://www.apache.org/licenses/LICENSE-2.0.txt")),
    scmInfo := Some(
      ScmInfo(
        url("https://github.com/marands/marands-json-scala"),
        "git@github.com:marands/marands-json-scala.git"
      )
    ),
    developers := List(
      Developer(
        "marands",
        "Majid Hatami",
        "info@marands.io",
        url("https://github.com/marands")
      )
    ),
  )
)

// *****************************************************************************
// Projects
// *****************************************************************************

lazy val `marands-json-scala` =
  project
    .in(file("."))
//    .disablePlugins(MimaPlugin)
    .aggregate(
      `marands-json`,
      `akka-http-jackson`
    )
    .settings(commonSettings)
    .settings(
      Compile / unmanagedSourceDirectories := Seq.empty,
      Test / unmanagedSourceDirectories := Seq.empty,
      publishArtifact := false,
    )

lazy val `marands-json` =
  project
    //.enablePlugins(AutomateHeaderPlugin)
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq(
        dependencies.akkaHttp,
        dependencies.akkaHttpJacksonJava,
        dependencies.jacksonModuleScala,
        dependencies.akkaStream   % Provided,
        dependencies.scalaReflect % scalaVersion.value,
        dependencies.scalaTest    % Test,
      ),
    )


lazy val `akka-http-jackson` =
  project
    //.enablePlugins(AutomateHeaderPlugin)
    .settings(commonSettings)
    .settings(
      libraryDependencies ++= Seq(
        dependencies.akkaHttp,
        dependencies.akkaHttpJacksonJava,
        dependencies.jacksonModuleScala,
        dependencies.scalaReflect % scalaVersion.value,
        dependencies.akkaStream   % Provided,
        dependencies.scalaTest    % Test,
      ),
    )

lazy val dependencies =
  new {
    object Version {
      val akka               = "2.6.10"
      val akkaHttp           = "10.2.2"
      val jacksonModuleScala = "2.12.0"
      val scalaTest          = "3.2.3"
    }

    val akkaHttp            = "com.typesafe.akka"                     %% "akka-http"             % Version.akkaHttp
    val akkaHttpJacksonJava = "com.typesafe.akka"                     %% "akka-http-jackson"     % Version.akkaHttp
    val akkaStream          = "com.typesafe.akka"                     %% "akka-stream"           % Version.akka
    val jacksonModuleScala  = "com.fasterxml.jackson.module"          %% "jackson-module-scala"  % Version.jacksonModuleScala

    val scalaTest           = "org.scalatest"                         %% "scalatest"             % Version.scalaTest
    val scalaReflect        = "org.scala-lang"                        %  "scala-reflect"
  }


lazy val commonSettings =
  Seq(
    scalaVersion := "2.13.4",
    organizationName := "Marands Inc",
    startYear := Some(2020),
    scalacOptions ++= Seq(
      "-unchecked",
      "-deprecation",
      "-language:_",
      "-target:jvm-1.8",
      "-encoding",
      "UTF-8",
    ),
    Compile / unmanagedSourceDirectories := Seq((Compile / scalaSource).value),
    Test / unmanagedSourceDirectories := Seq((Test / scalaSource).value),
    pomIncludeRepository := (_ => false)
  )
