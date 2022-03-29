@echo off
set targetDirectory=target/classes/

set sourcepath=src/main/java/

javac -d %targetDirectory%  src/main/java/wordle/*.java
javac -d %targetDirectory% -cp src/test/resources/junit-4.13.2.jar -sourcepath %sourcepath% src/test/java/wordleTest/*.java

java -classpath %targetDirectory%;src/test/resources/junit-4.13.2.jar wordleTest.GameTest
