export DISPLAY=
set -e
javac *.java -d .
jar -cfe /tmp/Picture.jar Picture *.class *.ttf
java -jar /tmp/Picture.jar
