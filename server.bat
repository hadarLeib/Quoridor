:: Script to compile the java code, and run the server ::

@echo off
cls

:: Compile the java, including the .jar files in the cmd
javac -cp ".;./lib/*" .\java\*.java -d .\bin\

:: Run the server
java -cp "./bin;./lib/*" .\java\QuoridorServer.java
