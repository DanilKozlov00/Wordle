@echo off
set targetDirectory=target/classes/

set sourcepath=src/main/java/

javac -d %targetDirectory%  src/main/java/wordle/*.java
javac -d %targetDirectory% -cp src/test/resources/libs/junit-4.13.2.jar -sourcepath %sourcepath% src/test/java/wordleTest/*.java

java -classpath %targetDirectory%;src/test/resources/libs/junit-4.13.2.jar;src/test/resources/libs/hamcrest-core-1.3.jar  org.junit.runner.JUnitCore wordleTest.GameTest
