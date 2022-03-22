@echo off
set targetDirectory=target/classes/Wordle
set sourcepath=src/main/java;src/test/java

javac -d %targetDirectory% -cp src/test/resources/junit-4.13.2.jar -sourcepath %sourcepath% src/test/java/*.java

java -classpath %targetDirectory%;src/test/resources/junit-4.13.2.jar GameTest
