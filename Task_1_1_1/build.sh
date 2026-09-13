#!/usr/bin/env bash

rm -rf out doc app.jar
mkdir out doc

# Pass the found files via xargs with a \0 delimiter.
find src/main/java -name "*.java" -print0 | xargs -0 javac -d out

javadoc -d doc -sourcepath src/main/java -subpackages ru -quiet

jar --create --file out/app.jar --main-class ru.nsu.tsydenov.Main -C out .

java -jar out/app.jar