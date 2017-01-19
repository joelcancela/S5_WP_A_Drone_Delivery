var orderId;
var jsonCustomer;
var ticks;

//Initialisation call
$('document').ready(function () {
    init();
});

var init = function () {
    orderId = getQueryVariable("customerId");
    bindVars();
};

function getQueryVariable(variable) {
    var query = window.location.search.substring(1);
    var vars = query.split('&');
    for (var i = 0; i < vars.length; i++) {
        var pair = vars[i].split('=');
        if (decodeURIComponent(pair[0]) == variable) {
            return decodeURIComponent(pair[1]);
        }
    }
    console.log('Query variable %s not found', variable);
}

var bindVars = function () {
    var clientNumber = document.getElementById("clientNumber").innerHTML = orderId;
    var orderNumber = document.getElementById("orderNumber").innerHTML = orderId;

};


///JSON-related functions
//When Json in input
function getJson(event) {
    var input = event.target;
    var reader = new FileReader();
    reader.onload = function () {
        jsonCustomer = JSON.parse(reader.result);
        console.log(jsonCustomer);
    };
    reader.readAsText(input.files[0]);
}

//When press start
function startSimulation() {
    document.getElementById("inputs").style.display = 'none';
    document.getElementById("mainContent").classList.remove("hidden");
    initDisplay();
}

function initDisplay() {
    fillTableFirstTime(jsonCustomer);
    ticks = jsonCustomer.deliveries[orderId].length;
}

function fillTableFirstTime(json) {
    var trHTML = "";
    $.each(json.context.deliveryPoints[orderId].order, function (key, value) {
        var typeOfProduct = Object.keys(value)[0];
        var numberOfProducts = value[typeOfProduct];
        for (var i = 0; i < numberOfProducts; i++) {
            trHTML += '<tr><td>' + 'Product type#' + typeOfProduct + '</td><td>' + parseRemainingTurns(json, key, i+1) + '</td></tr>';
        }
    });
    $('#ordersTable').append(trHTML);
}

function parseRemainingTurns(json, typeOccurence, occurrence) {
    var tick = json.deliveries[orderId];
    var turns = 0;
    $.each(tick, function (key, value) {//foreach tick
        //0 31 1 35
        var tickKey = value.inventory[typeOccurence];
        // console.dir(tickKey);
        var tickValue = tickKey[Object.keys(tickKey)[0]];
        //console.log(key+"product"+product+" occurence"+occurrence+" valuetick"+ tickValue);
        if (tickValue < occurrence) {
            return turns;
        }
        turns++;
    });
    return turns;
}


//TODO

function getCSV(event) {

}

function onTick() {

}

function simulation() {
    interval = setInterval(onTick, 2000);
    clearInterval(interval);
}