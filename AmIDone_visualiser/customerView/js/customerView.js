var orderId;
var jsonCustomer;

//Initialisation call
$('document').ready(function () {
    init();
});

var init = function () {
    setVars();
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

var setVars = function () {
    orderId = getQueryVariable("customerId");
};

var bindVars = function () {
    var clientNumber = document.getElementById("clientNumber").innerHTML = orderId;
    var orderNumber = document.getElementById("orderNumber").innerHTML = orderId;

};


///JSON-related functions

function getJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        jsonCustomer = JSON.parse(reader.result);
        console.log(jsonCustomer);
        fillTableFirst(jsonCustomer);
    };
    reader.readAsText(input.files[0]);
}


var fillTableFirst = function (json) {
    var trHTML = "";
    $.each(json.context, function (key, value) {
        trHTML += '<tr><td>' + key + '</td><td>' + value + '</td></tr>';
    });
    $('#ordersTable').append(trHTML);
};


function getCSV(event) {

}

function startSimulation() {

}