var operatorJson;

function init() {
    $.getJSON("../log.json", function(json) {
        operatorJson = json;
    });


}

function generateDrones(){
    var newContent;
    var nbDrone = operatorJson.context.nbDrone;

    newContent="<tr><th>Id</th><th>Departure</th><th>Departure Turn</th><th>Actual position</th><th>Actual Turn</th><th>Remaining turns</th></tr>";

    for(var i=0;i<nbDrone;i++){

    }

    document.getElementById("dronesTable").innerHTML = newContent;
}