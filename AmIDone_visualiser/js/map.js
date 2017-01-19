/**
 * Created by Jeremy on 18/01/2017.
 */

const COLOR = {"BACKGROUND": "#F0EDE5", "PATH": "#00B3FD"};

const DIV_ID = "map";
const CANVAS_ID = "map_canvas";

const FRAME_PER_SECOND = 60;
const FRAME_FREQ = 1 / FRAME_PER_SECOND;

const TIME_BETWEEN_TICK = 2;

const RENDER_BEFORE_TICK = FRAME_PER_SECOND * TIME_BETWEEN_TICK;

var map;
var ctx, canvas;
var drones;
var paths = [];

var xCase, yCase;

var ticks = -1;
var renderSinceTick = -1;


function initMap(json_log) {
    drones = json_log.drones;
    var contextIn = json_log.context;
    buildMap(contextIn.map.rows, contextIn.map.cols, contextIn.warehouses, contextIn.deliveryPoints);
    canvas = document.getElementById(CANVAS_ID);
    ctx = canvas.getContext("2d");
    refreshSize();
    // Improve render
    ctx.mozImageSmoothingEnabled = true;
    ctx.webkitImageSmoothingEnabled = true;
    ctx.msImageSmoothingEnabled = true;
    ctx.imageSmoothingEnabled = true;
    window.onresize = refreshSize;
    //init_drones();
    setInterval(render, FRAME_FREQ * 1000);
}


// ##################
// GET SET ADD REMOVE
// ##################

function addPath(departure, arrival, remaining) {
    var remainingTicks = remaining * RENDER_BEFORE_TICK;
    paths.push({"departure": departure, "arrival": arrival, "total": remainingTicks, "remaining": remainingTicks});
}

function removePath(departure, arrival, total, remaining) {
    paths.slide(paths.indexOf({"departure": departure, "arrival": arrival, "total": total, "remaining": remaining}), 1);
}


function addDrone(drone) {
    // Add a drone object: {departure:{x:_,y:_}, arrival:{x:_,y:_}, inventory:{0:_, 1:_,...}, remaining:_}
    drones.push(drone.append("total", 0));

}

function setJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        var text = reader.result;
        var json_log = JSON.parse(text);
        /*drones = json_log.drones;
        var contextIn = json_log.context;
        buildMap(contextIn.map.rows, contextIn.map.cols, contextIn.warehouses, contextIn.deliveryPoints);*/
        initMap(JSON.parse(text));
    };
    reader.readAsText(input.files[0]);
}

function buildMap(rows, cols, warehouses, deliveryPoints) {
    map = [];
    for (var i = 0; i < rows; i++) {
        map.push([]);
        for (var j = 0; j < cols; j++) {
            map[i].push('');
        }
    }
    warehouses.forEach(function (w) {
        map[w.y][w.x] = 'W';
    });
    deliveryPoints.forEach(function (dp) {
        map[dp.y][dp.x] = 'O';
    });
}

function setMap(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        map = [];
        var text = reader.result;
        var lines = text.split("\n");
        for (var i = 0; i < lines.length; i++) {
            map.push([]);
            var cells = lines[i].split(",");
            for (var j = 0; j < cells.length; j++) {
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
    ticks++;
    drones.forEach(function (d) {
        if (d[ticks] == undefined)
            return;
        if (ticks == 0 || d[ticks - 1].remaining == 0) {
            addPath(d[ticks].departure, d[ticks].arrival, d[ticks].remaining);
        }
    });


}

function render() {
    renderSinceTick++;
    paths.forEach(function (e) {
        e.remaining--;
        if (e.remaining < 0) {
            paths.forEach(function (item, index, object) {
                if (item === e)
                    object.splice(index, 1);
            });
        }
    });
    if (renderSinceTick % RENDER_BEFORE_TICK == 0) {
        tick();
        renderSinceTick = 0;
    }
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    ctx.fillStyle = COLOR.BACKGROUND;
    ctx.fillRect(0, 0, canvas.width, canvas.height);
    paths.forEach(function (p) {
        drawPath(p.departure.x, p.departure.y, p.arrival.x, p.arrival.y);
    });
    drawMap();
    drawDrones();
}

// ##################
// DISPLAY FUNCTIONS
// ##################

function drawDrones() {

    function drawDrone(x, y) {
        var xPx = x * xCase + xCase / 2;
        var yPx = y * yCase + yCase / 2;
        ctx.beginPath();
        ctx.arc(xPx, yPx, xCase / 3, 0, 2 * Math.PI, false);
        ctx.fillStyle = 'green';
        ctx.fill();
    }

    paths.forEach(function (path) {
        var x = path.departure.x + ((path.total - path.remaining) / path.total) * (path.arrival.x - path.departure.x);
        var y = path.departure.y + ((path.total - path.remaining) / path.total) * (path.arrival.y - path.departure.y);
        drawDrone(x, y);
    });

    drones.forEach(function (d) {
        if (d[ticks] == undefined)
            return;
        if (d[ticks].remaining == 0) {
            drawDrone(d[ticks].arrival.x, d[ticks].arrival.y);
        }
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
    ctx.lineWidth = "3";
    ctx.strokeStyle = COLOR.PATH;
    ctx.moveTo(dx * xCase + xCase / 2, dy * yCase + yCase / 2);
    ctx.lineTo(ax * xCase + xCase / 2, ay * yCase + yCase / 2);
    ctx.stroke();
}

function refreshSize() {

    var ratio = map[0].length / map.length;
    var preferedWidth = ratio * parent.innerHeight;


    if (preferedWidth < parent.innerWidth) {
        ctx.canvas.width = preferedWidth;
        ctx.canvas.height = parent.innerHeight;
    } else {
        var preferedHeight = (1/ratio) * parent.innerWidth;
        ctx.canvas.width = parent.innerWidth;
        ctx.canvas.height = preferedHeight;
    }
    xCase = canvas.width / map[0].length;
    yCase = canvas.height / map.length;
    render();
}