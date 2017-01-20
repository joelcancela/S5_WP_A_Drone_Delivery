/**
 * Created by Jeremy on 18/01/2017.
 */

const COLOR = {"BACKGROUND": "#FFFFFF", "PATH": "#00B3FD"};

const SIZE_IMG = {"DRONE": 1, "POI": 0.8};

const DIV_ID = "map";
const CANVAS_ID = "map_canvas";

const FRAME_PER_SECOND = 60;
const FRAME_FREQ = 1 / FRAME_PER_SECOND;

const TIME_BETWEEN_TICK = 2;

const RENDER_BEFORE_TICK = FRAME_PER_SECOND * TIME_BETWEEN_TICK;

var img = {};

var map;
var ctx, canvas;
var drones;
var focus;
var paths = [];

var xCase, yCase;

var ticks = -1;
var renderSinceTick = -1;


function initMap(json_log) {
    drones = [];
    focus = [];
    json_log.drones.forEach(function (d) {
        addDrone(d);
    });
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
}

function startMap() {
    setInterval(render, FRAME_FREQ * 1000);
}


// ##################
// GET SET ADD REMOVE
// ##################

function addPath(departure, arrival, remaining, droneId) {
    var remainingTicks = remaining * RENDER_BEFORE_TICK;
    paths.push({"departure": departure, "arrival": arrival, "total": remainingTicks, "remaining": remainingTicks, "droneId": droneId});
}

function addDrone(drone) {
    drones.push(drone);
    focus.push(0);
}

function setFocus(droneId) {
    focus[droneId] = 1;
}

function unsetFocus(droneId) {
    focus[droneId] = 0;
}

function setJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function () {
        var text = reader.result;
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
            addPath(d[ticks].departure, d[ticks].arrival, d[ticks].remaining, drones.indexOf(d));
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
        drawPath(p.departure.x, p.departure.y, p.arrival.x, p.arrival.y, focus[p.droneId]);
    });
    drawMap();
    drawDrones();
}

// ##################
// DISPLAY FUNCTIONS
// ##################

function drawDrones() {

    function drawDrone(x, y, img, droneId) {
        drawObject(img, x, y, SIZE_IMG.DRONE);
    }

    paths.forEach(function (path) {
        var control = getControlPoint(path.departure.x, path.departure.y, path.arrival.x, path.arrival.y, 1);
        var end = getQuadraticBezierXYatPercent(path.departure, control, path.arrival, (path.total - path.remaining) / path.total);
        var id = 'img_flying';
        if (focus[path.droneId] != 0)
            id += "_red";
        drawDrone(end.x, end.y, document.getElementById(id));
    });

    drones.forEach(function (d) {
        if (d[ticks] == undefined)
            return;
        if (d[ticks].remaining == 0) {
            var id;
            if (d[ticks].type == "unload" || d[ticks].type == "deliver")
                id = 'img_unloading';
            else if (d[ticks].type == "load")
                id = 'img_loading';
            else
                id = "img_flying";
            if (focus[drones.indexOf(d)] != 0)
                id += "_red";
            drawDrone(d[ticks].arrival.x, d[ticks].arrival.y, document.getElementById(id), drones.indexOf(d))
        }
    });

}

function drawMap() {

    for (var y = 0; y < map.length; y++) {
        for (var x = 0; x < map[y].length; x++) {
            switch (map[y][x]) {
                case "W":
                    drawObject(document.getElementById('img_warehouse'), x, y, SIZE_IMG.POI);
                    break;
                case "O":
                    drawObject(document.getElementById('img_order'), x, y, SIZE_IMG.POI);
                    break;
            }

        }
    }
}

function drawObject(img, x, y, size) {
    ctx.drawImage(img, x * xCase + (xCase * ((1 - size) / 2)), y * yCase + (yCase * ((1 - size) / 2)), xCase * size, yCase * size);
}


function drawPath(dx, dy, ax, ay, focus) {
    var dxPx = dx * xCase + xCase / 2;
    var dyPx = dy * yCase + yCase / 2;
    var axPx = ax * xCase + xCase / 2;
    var ayPx = ay * yCase + yCase / 2;
    var control = getControlPoint(dxPx, dyPx, axPx, ayPx, xCase);
    ctx.lineWidth = "5";
    ctx.beginPath();
    if (focus == 0)
        ctx.strokeStyle = COLOR.PATH;
    else
        ctx.strokeStyle = COLOR.FOCUS;
    ctx.moveTo(dxPx, dyPx);
    ctx.quadraticCurveTo(control.x, control.y, axPx, ayPx);
    ctx.stroke();
}

function getControlPoint(dx, dy, ax, ay, roModifier) {
    var teta = Math.atan((ax - dx) / (ay - dy));
    var middle = {"x": (dx + ax) / 2, "y": (ay + dy) / 2};
    var ro = roModifier * ((Math.sqrt(2))/2);
    var control = {"x": ro * Math.cos(teta) + middle.x, "y": middle.y - ro * Math.sin(teta)};
    return control;
}

function getQuadraticBezierXYatPercent(startPt,controlPt,endPt,percent) {
    var x = Math.pow(1-percent,2) * startPt.x + 2 * (1-percent) * percent * controlPt.x + Math.pow(percent,2) * endPt.x;
    var y = Math.pow(1-percent,2) * startPt.y + 2 * (1-percent) * percent * controlPt.y + Math.pow(percent,2) * endPt.y;
    return( {"x":x,"y":y} );
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