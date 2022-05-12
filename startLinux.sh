mkdir -p target/classes
cp -R src/main/resources/ target/classes
javac -d target/classes -classpath "target/classes/resources/libs/*" -sourcepath src/main/java src/main/java/wordle/model/TxtDictionary.java