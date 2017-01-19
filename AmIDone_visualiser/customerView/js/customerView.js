var orderId;

var init = function () {
    setVars();
    bindVars();
    getJson();
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
};

var getJson = $.getJSON("../log.json", function(json) {
    console.log(json);
});

//Initialisation call
$('document').ready(function () {
    init();
});
