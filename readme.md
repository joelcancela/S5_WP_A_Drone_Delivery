### DroneDelivery Team A

Scripts :
-

* build.sh: script for maven build and chain execution <br>
```
# Works best when launching from project root

./build.sh [pathToFile.in]
```

* chainExec.sh: same as build.sh without mvn clean install <br>
```
# Works best when launching from project root

mvn clean install
./chainExec.sh [pathToFile.in]
```