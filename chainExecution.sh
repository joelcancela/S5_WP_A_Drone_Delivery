#!/usr/bin/env bash
#Don't forget to mvn clean install
cd scheduler
mvn -q exec:java -Dexec.args="../$*"
cd ../visualiser/
mvn -q exec:java  -Dexec.args="../$* ../scheduler.out"
cd ../benchmark/
mvn -q exec:java -Dexec.args="../$* ../scheduler.out"