export DISPLAY=
javac *.java -d .
jar -cfm Picture.jar .manifest.txt *.class *.ttf
java -jar Picture.jar
