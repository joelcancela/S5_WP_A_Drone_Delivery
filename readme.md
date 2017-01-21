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

Simulation (Visualisation) :
-

* If you launch one of the scripts, the visualiser will generate a ``log.json`` at the root of the project, and automatically open a webpage in your web browser.
If this doesn't happen, go to the ``AmIDone_visualiser`` folder and open ``index.html``.

* When arriving at the index, you'll have to choose between two views :
    * The client view
        - Just enter the order number (for the simulation, we considered that the client number is the same as the order number)
    * The operator view

* For both views, you'll have to load the ``log.json`` generated, to load the context for the simulation.
* Just press start and that's it !

Notes :
-

* All the modules in this project have a dependency with the ```common module```, so if you want to execute them separately don't forget to use the ```common module``` too.