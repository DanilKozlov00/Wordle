mkdir -p target/classes
cp -R src/main/resources/ target/classes
javac -d target/classes -classpath "target/classes/resources/libs/*" -sourcepath src/main/java src/main/java/wordle/model/TxtDictionary.java
java -classpath "target/classes:target/classes/resources/libs/*" wordle.Main
words
leave
mines
lines
times
loves
exit