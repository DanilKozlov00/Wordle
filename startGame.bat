javac -d target/classes -classpath "src/main/resources/libs/*" -sourcepath src/main/java "src/main/java/wordle/*.java"
xcopy /S /Y /D src\main\resources\ target\classes
java -classpath "target/classes;src/main/resources/libs/*" wordle.Main