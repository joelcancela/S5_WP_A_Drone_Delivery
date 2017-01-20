var orderId;
var jsonCustomer;
var ticks;
var productsOrdered = [];
var numberOfProducts;
var interval;
var delivered = 0;

const tickTime = 2000;

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
    interval = setInterval(onTick, tickTime);
}

function initDisplay() {
    createJSONvars(jsonCustomer);
    fillTableFirstTime(jsonCustomer);
    ticks = jsonCustomer.deliveries[orderId].length;
}

function createJSONvars(json) {
    $.each(json.context.deliveryPoints[orderId].order, function (key, value) {
        var typeOfProduct = Object.keys(value)[0];
        var numberOfProducts = value[typeOfProduct];
        for (var i = 0; i < numberOfProducts; i++) {
            productsOrdered.push(typeOfProduct);
        }
    });
    numberOfProducts = productsOrdered.length;
}

function fillTableFirstTime(json) {
    var trHTML = "";
    $.each(productsOrdered, function (key, value) {
        trHTML += '<tr><td>' + 'Product type#' + value + '</td><td id=' + 'cell' + key + '>'
            + getMaxima(json)[key] + '</td></tr>';
    });
    $('#ordersTable').append(trHTML);
}


function getMaxima(json) {
    var maxima = [];
    var tick = json.deliveries[orderId];
    var remainingList = [];
    $.each(tick, function (key, value) {
        remainingList.push(value.remaining);
    });
    var previous = 0;
    $.each(remainingList, function (key, value) {
        if (previous == 0) {
            maxima.push(value);
        }
        previous = value;
    });
    if (maxima.length < numberOfProducts) {
        maxima = reparseQuantity(json, maxima);
    }

    return maxima;
}

function reparseQuantity(json, maxima) {
    var deltas = [];
    var newMaxima = [];
    $.each(maxima, function (key1, value1) {
        var previous;
        $.each(json.deliveries[orderId], function (key2, value2) {//tick
            // console.log("value2 " + value2.remaining);//REMAINING of tick
            // console.log("value1 " + value1);//Remaining in maxima
            if (key2 == value1) {
                var obj1 = previous.inventory;
                var objvalue1 = obj1[Object.keys(obj1)[0]];
                // console.log("elseobj1 " + objvalue1);

                var obj2 = value2.inventory;
                var objvalue2 = obj2[Object.keys(obj2)[0]];
                // console.log("elseobj2 " + objvalue2);

                var delta = objvalue1 - objvalue2;
                deltas.push(delta);
            }
            // if (value2.remaining == value1) {
            //     if (previous == undefined) {
            //         // console.log("value2.remaining "+value2.remaining+" == "+value1);
            //         //Look le delta et on push delta fois le remaining
            //
            //         var obj = value2.inventory;
            //         var objvalue = obj[Object.keys(obj)[key1]];
            //         console.log("previousUndefined " + objvalue);
            //         deltas.push(objvalue);
            //     }
            //     else {
            //         if (previous.remaining == 1) {
            //             var obj1 = previous.inventory;
            //             var objvalue1 = obj1[Object.keys(obj1)[0]];
            //             console.log("elseobj1 " + objvalue1);
            //
            //             var obj2 = value2.inventory;
            //             var objvalue2 = obj2[Object.keys(obj2)[0]];
            //             console.log("elseobj2 " + objvalue2);
            //
            //             var delta = objvalue1 - objvalue2;
            //             console.log("elseDelta " + delta);
            //
            //             deltas.push(delta);
            //
            //         }
            //     }
            // }
            previous = value2;
        });
    });
    $.each(deltas, function (key3, value3) {
        if (value3 > 0) {
            for (i = 0; i < value3; i++) {
                newMaxima.push(maxima[key3]);
            }
        }
        if (value3 == 0) {
            newMaxima.push(maxima[key3]);
        }
    });
    return newMaxima;
}


function onTick() {
    //DECREMENTS TABLE CELLS
    delivered = 0;
    $.each($('[id^="cell"]'), function (index, cell) {

        if (cell.innerText == "DELIVERED") {
            delivered++;
            if (delivered == numberOfProducts) {
                clearInterval(interval);
            }
            return true;
        }
        if (cell.innerText == 1) {
            cell.innerText = "DELIVERED";
        }
        if (cell.innerText > 1) {
            cell.innerText = cell.innerText - 1;
        }
    });

    //Update statusbar
    updateStatusbar();
}

function updateStatusbar() {

    var delivered = 0;
    $.each($('[id^="cell"]'), function (index, cell) {
        if (cell.innerText == "DELIVERED") {
            delivered++;
        }
    });

    var percentage = Math.ceil((delivered / numberOfProducts) * 100);

    $('#progressBar').css("width", function (i) {
        return percentage + "%";
    });

    $("#progressSpan").text(percentage + "%");

    if (percentage == 100) {
        $('#progressBar').addClass('progress-bar-success');

        $('#progressBar').removeClass('progress-bar-warning');
    }
}