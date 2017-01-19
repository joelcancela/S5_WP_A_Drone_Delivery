var operatorJson;
var actualTime;
var dronesDepartures;

function init() {
    initValues();
    getJson();
}

function initValues(){
    actualTime = 0;
    dronesDepartures = [];
}


var getJson = function () {
    $.getJSON("../log.json", function (json) {
        console.log(json);
        operatorJson = json;
        generateDrones(json);
    });
};

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

$('document').ready(function () {
    init();
});