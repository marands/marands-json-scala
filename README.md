# marands-json #

[![Build Status](https://travis-ci.com/marands/marands-json-scala.svg?branch=main)](https://travis-ci.org/marands/marands-json-scala)
[![Maven Central](https://img.shields.io/maven-central/v/io.marands/akka-http-circe_2.12.svg)](https://maven-badges.herokuapp.com/maven-central/io.marands/akka-http-circe_2.12)


marands-json, also provides JSON (un)marshalling support for [Akka HTTP](https://github.com/akka/akka-http) via the following JSON libraries:
-[x] [Jackson](https://github.com/FasterXML/jackson) via [Scala Module](https://github.com/FasterXML/jackson-module-scala) by default

## Installation

The artifacts are published to Maven Central.

``` scala
libraryDependencies ++= Seq(
  "io.marands" %% "akka-http-jackson" % "0.1.0",
  ...
)
```

Special Thanks to  ***Daniel Ciocîrlan** @ [Rock The Jvm](https://rockthejvm.com/)*
If you want to learn Scala or advance your skills, The best Scala Instructor & teacher which I have ever seen.


## License ##

This code is open source software licensed under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).

