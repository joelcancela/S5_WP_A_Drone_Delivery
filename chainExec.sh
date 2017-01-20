#!/usr/bin/env bash
#Keep the cd as root project
#Don't forget to mvn clean install
mvn -q exec:java -pl scheduler -Dexec.args="$*"
echo ""
mvn -q exec:java -pl visualiser -Dexec.args="$* scheduler.out"
echo ""
mvn -q exec:java -pl benchmark -Dexec.args="$* scheduler.out"