/**
 * Created by Jeremy on 18/01/2017.
 */

const COLOR = {"BACKGROUND" : "#F0EDE5"};

const DIV_ID = "map";
const CANVAS_ID = "map_canvas";

const FRAME_PER_SECOND = 30;
const TICK = 1/FRAME_PER_SECOND;

var map;
var ctx, canvas;
var drones;
var paths = [];

var ticks = 0;
//var json_log;
var drones


function init() {
    canvas = document.getElementById(CANVAS_ID);
    ctx = canvas.getContext("2d");
    refreshSize();
    // Improve render
    ctx.mozImageSmoothingEnabled = true;
    ctx.webkitImageSmoothingEnabled = true;
    ctx.msImageSmoothingEnabled = true;
    ctx.imageSmoothingEnabled = true;
    window.onresize = refreshSize;
    init_drones();
    setInterval(render, TICK * 1000);
}

function init_drones() {
    
}


// ##################
// GET SET ADD REMOVE
// ##################

function addPath(departure, arrival, remainingTurn) {
    var remaining = remainingTurn;
    paths.push({"departure":departure, "arrival":arrival, "total":remaining, "remaining":remaining});
}

function removePath(departure,arrival) {
    paths.slide(paths.indexOf({"departure":departure, "arrival":arrival}), 1);
}


function addDrone(drone) {
    // Add a drone object: {departure:{x:_,y:_}, arrival:{x:_,y:_}, inventory:{0:_, 1:_,...}, remaining:_}
    drones.push(drone);
}

function setJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function(){
        var text = reader.result;
        var json_log = JSON.parse(text);
        drones = json_log.drones;
    };
    reader.readAsText(input.files[0]);
}

function setMap(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function(){
        map = [];
        var text = reader.result;
        var lines = text.split("\n");
        for(var i = 0; i < lines.length; i++){
            map.push([]);
            var cells = lines[i].split(",");
            for(var j = 0; j < cells.length; j++){
                map[i].push(cells[j].charAt(0));
            }
        }
        // SOME STUFF
    };
    reader.readAsText(input.files[0]);
}

// ##################
// LOOP FUNCTIONS
// ##################

function tick() {
    paths.forEach(function(e){
        e.remaining--;
        if (e.remaining < 0) {
            paths.forEach(function (item, index, object) {
                if (item === e) {
                    object.splice(index, 1);
                }
            });
        }
    });
    drones.forEach(function (e) {
        e.remaining--;
        if (e.remaining < 0) {
            paths.forEach(function (item, index, object) {
                if (item === e) {
                    object.splice(index, 1);
                }
            });
        }
    });
}

function render() {
    ticks++;
    if (ticks%FRAME_PER_SECOND == 0){
        tick();
        ticks = 0;
    }
    console.log("refresh");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = COLOR.BACKGROUND;
    ctx.fillRect(0,0, canvas.width, canvas.height);
    paths.forEach(function (e) {
        drawPath(e.departure.x, e.departure.y, e.arrival.x, e.arrival.y);
    });
    drawMap();
    drawDrones();
}

// ##################
// DISPLAY FUNCTIONS
// ##################

function drawDrones(path) {
    paths.forEach(function (e) {
        console.log("draw drone");
        ctx.beginPath();
        var x = path.departure.x + ((path.total - path.remaining) / path.total) * (path.arrival.x - path.departure.x);
        var y = path.departure.y + ((path.total - path.remaining) / path.total) * (path.arrival.y - path.departure.y);
        console.log(x + "," + y);
        ctx.arc(x * xCase + xCase / 2, y * yCase + yCase / 2, 20, 0, 2 * Math.PI, false);
        ctx.fillStyle = 'green';
        ctx.fill();
    });
}

function drawMap() {
    for (var y = 0; y < map.length; y++) {
        for (var x = 0; x < map[y].length; x++) {
            switch (map[y][x]) {
                case "W":
                    //ctx.drawImage(document.getElementById("warehouse"), x * xCase, y * yCase, xCase, yCase);
                    ctx.fillStyle = "#FF0000";
                    ctx.fillRect(x * xCase, y * yCase, xCase, yCase);
                    break;
                case "O":
                    //ctx.drawImage(document.getElementById("delivery-point"), x * xCase, y * yCase, xCase, yCase);
                    ctx.fillStyle = "#0000FF";
                    ctx.fillRect(x * xCase, y * yCase, xCase, yCase);
                    break;
            }

        }
    }
}


function drawPath(dx, dy, ax, ay) {
    //TODO: Pour le moment, en ligne droite
    ctx.beginPath();
    ctx.lineWidth="3";
    ctx.strokeStyle="#00B3FD";
    ctx.moveTo(dx * xCase + xCase/2, dy * yCase + yCase/2);
    ctx.lineTo(ax * xCase + xCase/2, ay * yCase + yCase/2);
    ctx.stroke();
}

function refreshSize() {
    /*var ratioW = 1600 / window.innerWidth, ratioH = 900 / window.innerHeight;
     if (ratioW > ratioH) {
     ctx.canvas.width = window.innerWidth * POURCENTAGE_CANVAS.LARGEUR;
     ctx.canvas.height = (900 / ratioW) * POURCENTAGE_CANVAS.HAUTEUR;
     } else {
     ctx.canvas.width = (1600 / ratioH) * POURCENTAGE_CANVAS.LARGEUR;
     ctx.canvas.height = window.innerHeight * POURCENTAGE_CANVAS.HAUTEUR;
     }
     xCase = canvas.width / 16;
     yCase = canvas.height / 9;*/
    ctx.canvas.width = parent.innerWidth;
    ctx.canvas.height = parent.innerHeight;
    xCase = canvas.width / map[0].length;
    yCase = canvas.height / map.length;
    render();
}

function distance(dx, dy, ax, ay) {
    return Math.sqrt(Math.pow(dx - ax, 2)+Math.pow(dy - ay, 2));
}