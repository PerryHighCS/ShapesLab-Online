export DISPLAY=
javac *.java -d . Picture.java
jar -cfm Picture.jar .manifest.txt *.class *.ttf
java TestPic -jar Picture.jar