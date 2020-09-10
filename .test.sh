export DISPLAY=
javac *.java -d .
jar -cfm Picture.jar .manifest.txt *.class *.ttf
java TestPic -jar Picture.jar