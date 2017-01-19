var operatorJson;
var actualTime;
var dronesDepartures;
var interval;
var lastTime;
var detailsDroneIndex;

function init() {
    initValues();
}

function initValues(){
    actualTime = 0;
    dronesDepartures = [];
    lastTime=0;
    detailsDroneIndex=-1;
}


function getJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        operatorJson = JSON.parse(reader.result);
        generateDrones(operatorJson);
        generateWarehouses(operatorJson);

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
        if(detailsDroneIndex!=-1)
            displayDetailsDroneIndex(detailsDroneIndex);

        generateDrones();
        generateWarehouses();
        actualTime++;
    }

}

function generateDrones(){
    var newContent;
    var nbDrone = operatorJson.context.nbDrone;
    var droneDone = "";

    newContent="<thead><tr><th>Id</th><th>Departure</th><th>Departure Turn</th><th>Arrival position</th><th>Arrival Turn</th><th>Remaining turns</th></tr></thead><tbody>";

    for(var i=0;i<nbDrone;i++){
        if(actualTime==0){
            dronesDepartures[i] = 0;
        }else {
            if(operatorJson.drones[i][actualTime]!=undefined && operatorJson.drones[i][actualTime].remaining == 0){
                dronesDepartures[i] = actualTime;
                droneDone = "class='done'";
            }else
                droneDone = "";
        }
        if(operatorJson.drones[i][actualTime]!=undefined)
            newContent += "<tr style='cursor:  pointer;' "+droneDone+" id='drone"+i+"'><td>"+i+"</td>" +
                "<td>("+operatorJson.drones[i][actualTime].departure.x+" ; "+operatorJson.drones[i][actualTime].departure.y+")</td>" +
                "<td>"+dronesDepartures[i]+"</td><td>("+operatorJson.drones[i][actualTime].arrival.x+" ; "+operatorJson.drones[i][actualTime].arrival.y+")</td>" +
                "<td>"+(actualTime+operatorJson.drones[i][actualTime].remaining)+"</td><td>"+operatorJson.drones[i][actualTime].remaining+"</td></tr>";
        else
            newContent += "<tr style='cursor:  pointer;' id='drone"+i+"' class='finish'><td>"+i+"</td><td>//</td><td>//</td><td>//</td><td>//</td><td>//</td></tr>";
    }

    newContent += "</tbody>";
    document.getElementById("dronesTable").innerHTML = newContent;

    for(var i=0;i<nbDrone;i++)
        document.getElementById("drone" + i).addEventListener("click", displayDetailsDrone);
}

function generateWarehouses(){
    var newContent;
    var nbWarehouse = operatorJson.context.warehouses.length;
    var droneDone = "";

    newContent="<thead><tr><th>Id</th><th>Coordinates</th></tr></thead><tbody>";
    for(var i=0;i<nbWarehouse;i++){
        newContent += "<tr style='cursor:  pointer;' id='warehouse"+i+"'><td>"+i+"</td><td>("+operatorJson.context.warehouses[i].x+" ; "+operatorJson.context.warehouses[i].y+")</td>";
    }

    newContent += "</tbody>";
    document.getElementById("warehousesTable").innerHTML = newContent;

}

function displayDetailsDroneIndex(index) {
    var newContent;
    var inventory="";

    if(operatorJson.drones[index][actualTime]!=undefined){
        for (var key in operatorJson.drones[index][actualTime].inventory) {
            if (operatorJson.drones[index][actualTime].inventory.hasOwnProperty(key)) {
                var val = operatorJson.drones[index][actualTime].inventory[key];
                inventory += "<b>Product " + key + " </b>: " + val + "<br/>";
            }
        }

        newContent = "<div class='table-responsive'><table class='table'>" +
            "<tr><th>ID</th><td>"+index+"</td></tr>" +
            "<tr><th>Departure</th><td>("+operatorJson.drones[index][actualTime].departure.x+" ; "+operatorJson.drones[index][actualTime].departure.y+")</td></tr>" +
            "<tr><th>Arrival</th><td>("+operatorJson.drones[index][actualTime].arrival.x+" ; "+operatorJson.drones[index][actualTime].arrival.y+")</td></tr>" +
            "<tr><th>Arrival in </th><td>"+operatorJson.drones[index][actualTime].remaining+"</td></tr>" +
            "<tr><th>Inventory</th><td>"+inventory+"</td></tr>" +
            "<tr></tr></table></div>";
    }else{
        inventory = "//";

        newContent = "<div class='table-responsive'><table class='table'>" +
            "<tr><th>ID</th><td>"+index+"</td></tr>" +
            "<tr><th>Departure</th><td>//</td></tr>" +
            "<tr><th>Arrival</th><td>//</td></tr>" +
            "<tr><th>Arrival in </th><td>//</td></tr>" +
            "<tr><th>Inventory</th><td>"+inventory+"</td></tr>" +
            "<tr></tr></table></div>";
    }

    document.getElementById("detailsContent").innerHTML = newContent;
}

function displayDetailsDrone(evt) {
    var id = evt.target.parentElement.id;
    var index = parseInt(id.substring(5, 6));

    displayDetailsDroneIndex(index);

    detailsDroneIndex = index;
}

function startSimulation() {
    if(lastTime==0)
        return;

    document.getElementById("inputs").style.display = 'none';
    document.getElementById("mainContent").classList.remove("hidden");

    setTimeout(function(){
        initMap(operatorJson);
    }, 1400);
    interval = setInterval(generatesInfos, 2000);

}

$('document').ready(function () {
    init();
});