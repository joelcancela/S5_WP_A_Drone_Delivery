#!/usr/bin/env bash
mvn clean install
mvn -q exec:java -pl scheduler -Dexec.args="$*"
mvn -q exec:java -pl visualiser -Dexec.args="$* scheduler.out"
mvn -q exec:java -pl benchmark -Dexec.args="$* scheduler.out"