export DISPLAY=
set -e
javac *.java -d .
jar -cfe /tmp/TestPic.jar TestPic *.class *.ttf
java -jar /tmp/TestPic.jar
