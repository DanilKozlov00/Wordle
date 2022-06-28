#!/bin/bash
tomcatDir=C:\Users\dkozlov.AD\apache-tomcat-9.0.62
mvn clean install
cd target/
mv wordle-1.0-SNAPSHOT.war ROOT.war
rm $tomcatDir/webapps ROOT.war
rm -r $tomcatDir/webapps/ROOT
mv ROOT.war $tomcatDir/webapps
cd $tomcatDir/bin
shutdown.bat
startup.bat

