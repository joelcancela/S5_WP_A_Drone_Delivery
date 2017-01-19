var operatorJson;
var actualTime;
var dronesDepartures;
var interval;
var lastTime;

function init() {
    initValues();
}

function initValues(){
    actualTime = 0;
    dronesDepartures = [];
    lastTime=0;
}


function getJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        operatorJson = JSON.parse(reader.result);
        generateDrones(operatorJson);

        var nbDrone = operatorJson.context.nbDrone;

        for(var i=0;i<nbDrone;i++) {
            var y = 0;
            while(operatorJson.drones[i][y] != undefined){
                y++;
            }
            if(y>lastTime)
                lastTime=y;
        }
    };
    reader.readAsText(input.files[0]);
}

function generatesInfos(){
    if(actualTime>lastTime)
        clearInterval(interval);
    else{
        generateDrones();
        actualTime++;
    }

}

function generateDrones(){
    var newContent;
    var nbDrone = operatorJson.context.nbDrone;

    newContent="<tr><th>Id</th><th>Departure</th><th>Departure Turn</th><th>Arrival position</th><th>Arrival Turn</th><th>Remaining turns</th></tr>";

    for(var i=0;i<nbDrone;i++){
        if(actualTime==0){
            dronesDepartures[i] = 0;
        }else {
            if(operatorJson.drones[i][actualTime]!=undefined &&operatorJson.drones[i][actualTime].remaining == 0){
                dronesDepartures[i] = actualTime;
            }
        }
        if(operatorJson.drones[i][actualTime]!=undefined)
            newContent += "<tr style='cursor:  pointer;' id='drone"+i+"'><td>"+i+"</td><td>("+operatorJson.drones[i][actualTime].departure.x+" ; "+operatorJson.drones[i][actualTime].departure.y+")</td><td>"+dronesDepartures[i]+"</td><td>("+operatorJson.drones[i][actualTime].arrival.x+" ; "+operatorJson.drones[i][actualTime].arrival.y+")</td><td>"+(actualTime+operatorJson.drones[i][actualTime].remaining)+"</td><td>"+operatorJson.drones[i][actualTime].remaining+"</td></tr>";
        else
            newContent += "<tr style='cursor:  pointer;' id='drone"+i+"'><td>"+i+"</td><td>x</td><td>x</td><td>x</td><td>x</td><td>x</td></tr>";
    }

    document.getElementById("dronesTable").innerHTML = newContent;

    for(var i=0;i<nbDrone;i++)
        document.getElementById("drone" + i).addEventListener("click", displayDetails);
}

function displayDetails(evt) {
    var id = evt.target.parentElement.id;
    var index = parseInt(id.substring(5, 6));
    var newContent;

    newContent = "<div class='table-responsive'><table class='table'>" +
        "<tr><th>ID</th><td>"+index+"</td></tr>" +
        "<tr><th>Departure</th><td>("+operatorJson.drones[index][actualTime].departure.x+" ; "+operatorJson.drones[index][actualTime].departure.y+")</td></tr>" +
        "<tr><th>Arrival</th><td>("+operatorJson.drones[index][actualTime].arrival.x+" ; "+operatorJson.drones[index][actualTime].arrival.y+")</td></tr>" +
        "<tr><th>Inventory</th><td>"+operatorJson.drones[index][actualTime].inventory+"</td></tr>" +
        "<tr></tr></table></div>";

    document.getElementById("detailsContent").innerHTML = newContent
}

function startSimulation() {
    if(lastTime==0)
        return;

    document.getElementById("inputs").style.display = 'none';
    document.getElementById("mainContent").classList.remove("hidden");

    interval = setInterval(generatesInfos, 2000);
}

$('document').ready(function () {
    init();
});