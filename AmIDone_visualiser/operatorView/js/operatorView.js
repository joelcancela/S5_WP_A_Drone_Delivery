var operatorJson;
var actualTime;
var dronesDepartures;

function init() {
    initValues();
}

function initValues(){
    actualTime = 0;
    dronesDepartures = [];
}


function getJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        operatorJson = JSON.parse(reader.result);
        generateDrones(operatorJson);
    };
    reader.readAsText(input.files[0]);
}


function generateDrones(){
    var newContent;
    var nbDrone = operatorJson.context.nbDrone;

    newContent="<tr><th>Id</th><th>Departure</th><th>Departure Turn</th><th>Arrival position</th><th>Arrival Turn</th><th>Remaining turns</th></tr>";

    for(var i=0;i<nbDrone;i++){
        if(actualTime==0){
            dronesDepartures[i] = 0;
        }else {
            if(operatorJson.drones[i][actualTime].timeRemaining == 0){
                dronesDepartures[i] = actualTime;
            }
        }
        newContent += "<tr style='cursor:  pointer;'><td>"+i+"</td><td>("+operatorJson.drones[i][actualTime].departure.x+" ; "+operatorJson.drones[i][actualTime].departure.y+")</td><td>"+dronesDepartures[i]+"</td><td>("+operatorJson.drones[i][actualTime].arrival.x+" ; "+operatorJson.drones[i][actualTime].arrival.y+")</td><td>"+(actualTime+operatorJson.drones[i][actualTime].timeRemaining)+"</td><td>"+operatorJson.drones[i][actualTime].timeRemaining+"</td></tr>";
    }

    document.getElementById("dronesTable").innerHTML = newContent;
}

function startSimulation() {
    document.getElementById("inputs").style.display = 'none';
    document.getElementById("mainContent").classList.remove("hidden");
}

$('document').ready(function () {
    init();
});