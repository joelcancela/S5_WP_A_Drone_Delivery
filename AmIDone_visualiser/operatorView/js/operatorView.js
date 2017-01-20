var operatorJson;
var actualTime;
var dronesDepartures;
var interval;
var lastTime;
var detailsDroneIndex;
var detailsWarehousIndex;
var detailsOrderIndex;
var ordersRemain;

const TICK = 2000;

function init() {
    initValues();
}

function initValues(){
    actualTime = 0;
    dronesDepartures = [];
    lastTime=0;
    detailsDroneIndex=-1;
    detailsWarehousIndex=-1;
    detailsOrderIndex=-1;
    ordersRemain = [];
}


function getJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        operatorJson = JSON.parse(reader.result);
        generateDrones(operatorJson);
        generateWarehouses(operatorJson);
        generateOrders(operatorJson);

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
        if(detailsWarehousIndex!=-1)
            displayDetailsWarehouseIndex(detailsWarehousIndex);
        if(detailsOrderIndex!=-1)
            displayDetailsOrderIndex(detailsOrderIndex);

        generateDrones();
        generateWarehouses();
        generateOrders();
        tick();
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

    newContent="<thead><tr><th>Id</th><th>Coordinates</th></tr></thead><tbody>";
    for(var i=0;i<nbWarehouse;i++){
        newContent += "<tr style='cursor:  pointer;' id='warehouse"+i+"'><td>"+i+"</td><td>("+operatorJson.context.warehouses[i].x+" ; "+operatorJson.context.warehouses[i].y+")</td></tr>";
    }

    newContent += "</tbody>";
    document.getElementById("warehousesTable").innerHTML = newContent;

    for(var i=0;i<nbWarehouse;i++)
        document.getElementById("warehouse" + i).addEventListener("click", displayDetailsWarehouse);
}


function lastTickForAnOrder(index){
    var max=0;
    for(var i=0;i<operatorJson.deliveries[index].length;i++){
        if(operatorJson.deliveries[index][i].remaining > max){
            max = operatorJson.deliveries[index][i].remaining;
        }
    }
    return max;
}

function generateOrders(){
    var newContent;
    var nbOrders = operatorJson.context.deliveryPoints.length;

    newContent="<thead><tr><th>Id</th><th>Coordinates</th><th>Status</th></tr></thead><tbody>";
    for(var i=0; i< nbOrders; i++){
        var status = "In progress";
        var orderDone = "";

        if(lastTickForAnOrder(i)<=actualTime){
            status = "Completed"
            orderDone = "class='done'";
        }

        if(actualTime == 0)
            ordersRemain[i] = operatorJson.deliveries[i][actualTime].remaining;
        else if(operatorJson.deliveries[i][actualTime]!=undefined
                && operatorJson.deliveries[i][actualTime+1]!=undefined
                && operatorJson.deliveries[i][actualTime].remaining == 0){
            ordersRemain[i] = operatorJson.deliveries[i][actualTime+1].remaining;
        }

        newContent += "<tr style='cursor:  pointer;' "+orderDone+" id='order"+i+"'><td>"+i+"</td><td>("+operatorJson.context.deliveryPoints[i].x+" ; "+operatorJson.context.deliveryPoints[i].y+")</td><td>"+status+"</td></tr>";
    }

    newContent += "</tbody>";
    document.getElementById("ordersTable").innerHTML = newContent;

    for(var i=0;i<nbOrders;i++)
        document.getElementById("order" + i).addEventListener("click", displayDetailsOrder);
}

function unsetFocus(){
    for(var i=0; i < operatorJson.context.nbDrone; i++){
        unsetFocusDrone(i);
    }
    for(var i=0; i < operatorJson.context.warehouses.length; i++){
        unsetFocusWarehouse(i);
    }
    for(var i=0; i < operatorJson.context.deliveryPoints.length; i++){
        unsetFocusOrder(i);
    }
}


function displayDetailsOrder(evt) {
    var id = evt.target.parentElement.id;
    var index = parseInt(id.substring(5, 6));

    unsetFocus();

    displayDetailsOrderIndex(index);
    setFocusOrder(index);

    detailsOrderIndex = index;
    detailsWarehousIndex = -1;
    detailsDroneIndex = -1;
}

function displayDetailsWarehouse(evt) {
    var id = evt.target.parentElement.id;
    var index = parseInt(id.substring(9, 10));

    unsetFocus();

    displayDetailsWarehouseIndex(index);
    setFocusWarehouse(index);

    detailsWarehousIndex = index;
    detailsOrderIndex = -1;
    detailsDroneIndex = -1;
}


function displayDetailsDrone(evt) {
    var id = evt.target.parentElement.id;
    var index = parseInt(id.substring(5, 6));

    unsetFocus()

    displayDetailsDroneIndex(index);
    setFocusDrone(index);

    detailsDroneIndex = index;
    detailsOrderIndex = -1;
    detailsWarehousIndex = -1;
}

function displayDetailsOrderIndex(index) {
    var newContent = "";
    var inventory="";

    newContent += "<span style='text-align: :center;'><img class='img-responsive' src='../media/order.png' alt='Order'></span>";

    if(operatorJson.deliveries[index][actualTime]!=undefined) {
        var decalage = 0;
        if(ordersRemain[index]<=actualTime)
            decalage = ordersRemain[index];

        if(operatorJson.deliveries[index][actualTime+decalage]!=undefined)
            for (var key in operatorJson.deliveries[index][actualTime+decalage].inventory) {
                if (operatorJson.deliveries[index][actualTime+decalage].inventory.hasOwnProperty(key)) {
                    var val = operatorJson.deliveries[index][actualTime+decalage].inventory[key];
                    inventory += "<b>Product " + key + " </b>: " + val + "<br/>";
                }
            }
        else
            inventory = "COMPLETED";
    }else
        inventory = "COMPLETED";

    newContent += "<div class='table-responsive'><table class='table'>" +
        "<tr><th>ID</th><td>" + index + "</td></tr>" +
        "<tr><th>Coordinates</th><td>(" + operatorJson.context.deliveryPoints[index].x + " ; " + operatorJson.context.deliveryPoints[index].y + ")</td></tr>" +
        "<tr><th>Products list</th><td>" + inventory + "</td></tr>" +
        "<tr></tr></table></div>";

    document.getElementById("detailsContent").innerHTML = newContent;
}


function displayDetailsWarehouseIndex(index) {
    var newContent = "";
    var inventory="";

    newContent += "<span style='text-align: :center;'><img class='img-responsive' src='../media/warehouse.png' alt='Warehouse'></span>";

    if(operatorJson.warehouse[index][actualTime]!=undefined) {
        var remainingToJump = operatorJson.warehouse[index][actualTime].remaining;

        if(actualTime!=remainingToJump && actualTime<remainingToJump+actualTime && actualTime+1!=remainingToJump+actualTime || actualTime==0)
            remainingToJump = 0;

        for (var key in operatorJson.warehouse[index][actualTime+remainingToJump].inventory) {
            if (operatorJson.warehouse[index][actualTime+remainingToJump].inventory.hasOwnProperty(key)) {
                var val = operatorJson.warehouse[index][actualTime+remainingToJump].inventory[key];
                inventory += "<b>Product " + key + " </b>: " + val + "<br/>";
            }
        }

        newContent += "<div class='table-responsive'><table class='table'>" +
            "<tr><th>ID</th><td>" + index + "</td></tr>" +
            "<tr><th>Coordinates</th><td>(" + operatorJson.context.warehouses[index].x + " ; " + operatorJson.context.warehouses[index].y + ")</td></tr>" +
            "<tr><th>Inventory</th><td>" + inventory + "</td></tr>" +
            "<tr></tr></table></div>";
    }else{
        var lastChangeIndex = operatorJson.warehouse[index].length-1;
        for (var key in operatorJson.warehouse[index][lastChangeIndex].inventory) {
            if (operatorJson.warehouse[index][lastChangeIndex].inventory.hasOwnProperty(key)) {
                var val = operatorJson.warehouse[index][lastChangeIndex].inventory[key];
                inventory += "<b>Product " + key + " </b>: " + val + "<br/>";
            }
        }
        newContent += "<div class='table-responsive'><table class='table'>" +
            "<tr><th>ID</th><td>"+index+"</td></tr>" +
            "<tr><th>Coordinates</th><td>("+operatorJson.context.warehouses[index].x+" ; "+operatorJson.context.warehouses[index].y+")</td></tr>" +
            "<tr><th>Inventory</th><td>"+inventory+"</td></tr>" +
            "<tr></tr></table></div>";
    }

    document.getElementById("detailsContent").innerHTML = newContent;
}

function displayDetailsDroneIndex(index) {
    var newContent ="";
    var inventory="";

    newContent += "<span style='text-align: :center;'><img class='img-responsive' src='../media/flying.png' alt='Drone'></span>";

    if(operatorJson.drones[index][actualTime]!=undefined){
        for (var key in operatorJson.drones[index][actualTime].inventory) {
            if (operatorJson.drones[index][actualTime].inventory.hasOwnProperty(key)) {
                var val = operatorJson.drones[index][actualTime].inventory[key];
                inventory += "<b>Product " + key + " </b>: " + val + "<br/>";
            }
        }

        newContent += "<div class='table-responsive'><table class='table'>" +
            "<tr><th>ID</th><td>"+index+"</td></tr>" +
            "<tr><th>Departure</th><td>("+operatorJson.drones[index][actualTime].departure.x+" ; "+operatorJson.drones[index][actualTime].departure.y+")</td></tr>" +
            "<tr><th>Arrival</th><td>("+operatorJson.drones[index][actualTime].arrival.x+" ; "+operatorJson.drones[index][actualTime].arrival.y+")</td></tr>" +
            "<tr><th>Arrival in </th><td>"+operatorJson.drones[index][actualTime].remaining+"</td></tr>" +
            "<tr><th>Inventory</th><td>"+inventory+"</td></tr>" +
            "<tr></tr></table></div>";
    }else{
        inventory = "//";

        newContent += "<div class='table-responsive'><table class='table'>" +
            "<tr><th>ID</th><td>"+index+"</td></tr>" +
            "<tr><th>Departure</th><td>//</td></tr>" +
            "<tr><th>Arrival</th><td>//</td></tr>" +
            "<tr><th>Arrival in </th><td>//</td></tr>" +
            "<tr><th>Inventory</th><td>"+inventory+"</td></tr>" +
            "<tr></tr></table></div>";
    }

    document.getElementById("detailsContent").innerHTML = newContent;
}

function startSimulation() {
    if(lastTime==0)
        return;

    document.getElementById("inputs").style.display = 'none';
    document.getElementById("mainContent").classList.remove("hidden");

    initMap(operatorJson);
    interval = setInterval(generatesInfos, TICK);
    startMap();

}

$('document').ready(function () {
    init();
});