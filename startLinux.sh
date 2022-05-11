mkdir target/classes
cp -R src/main/resources/ target/classes
javac -d target/classes -classpath "target/classes/libs/*" -sourcepath src/main/java "src/main/java/wordle/model/TxtDictionary.java"
java -classpath "target/classes;src/main/resources/libs/*" wordle.Main