name := "piconot" // you can change this!

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= 
  Seq( "org.scalatest" %% "scalatest" % "2.2.6" % "test",
       "org.scalacheck" %% "scalacheck" % "1.13.0" % "test" )

unmanagedClasspath in (Compile, runMain) += baseDirectory.value / "resources"
