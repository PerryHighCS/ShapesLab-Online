export DISPLAY=
javac *.java -d . Picture.java
java Picture -D java.awt.headless=true
