resolvers += Resolver.bintrayIvyRepo("sbt", "sbt-plugin-releases")

addSbtPlugin("org.jetbrains" % "sbt-ide-settings" % "1.1.0")

addSbtPlugin("com.geirsson"      % "sbt-ci-release"  % "1.5.5")
addSbtPlugin("com.typesafe"      % "sbt-mima-plugin" % "0.8.1")
addSbtPlugin("org.scalameta"     % "sbt-scalafmt"    % "2.4.2")

addSbtPlugin("com.typesafe" % "sbt-mima-plugin" % "0.8.1")

// Required::  sbt-travisci is an sbt plugin to integrate with Travis CI.
addSbtPlugin("com.dwijnand" % "sbt-travisci" % "1.2.0")
