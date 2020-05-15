@echo off

cd framework
javac *.java
jar cfm framework.jar manifest.mf *.class
mv framework.jar ../deploy

# src
cd ../../../../../../

# import edu.uci.swe262p_progstyles.week7.nineteen.framework.ITermFreqApp;
javac edu\uci\swe262p_progstyles\week7\nineteen\app\*.java

cd edu\uci\swe262p_progstyles\week7\nineteen\app
jar cf app1.jar App1.class
jar cf app2.jar App2.class
mv app1.jar app2.jar ../deploy

cd ..
find . -name '*.class' -delete

# ?
# Error: Could not find or load main class edu.uci.swe262p_progstyles.week7.nineteen.framework.Framework
# Caused by: java.lang.ClassNotFoundException: edu.uci.swe262p_progstyles.week7.nineteen.framework.Framework
# ?
java -jar \deploy\framework.jar

# src
cd ../../../../../../
java -jar edu\uci\swe262p_progstyles\week7\nineteen\deploy\framework.jar
