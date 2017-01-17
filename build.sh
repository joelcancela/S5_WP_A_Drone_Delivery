#!/usr/bin/env bash
mvn clean package
mvn exec:java -pl scheduler -Dexec.args="$*"
mvn exec:java -pl visualiser -Dexec.args="$* scheduler.out"
#mvn exec:java -pl benchmark -Dexec.args="$* scheduler.out"