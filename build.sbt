name := "LING581"

version := "1.0"

scalaVersion := "2.11.4"

//assemblyJarName in assembly := "MED.jar"

libraryDependencies ++= Seq(
  "commons-codec" % "commons-codec" % "1.1",
  "edu.arizona.sista" % "processors" % "3.3",
  "edu.arizona.sista" % "processors" % "3.3" classifier "models",
  "org.apache.commons" % "commons-math3" % "3.3"
)

