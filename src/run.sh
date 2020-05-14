#! /bin/sh
# rebuild if necessary
rm -f *.class
rm -f output.txt
javac *.java
# do action with arguments
java Main $@
rm -f *.class