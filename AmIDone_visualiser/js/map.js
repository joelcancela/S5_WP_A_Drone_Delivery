/**
 * Created by Jeremy on 18/01/2017.
 */

const COLOR = {"BACKGROUND": "#F0EDE5", "PATH": "#00B3FD"};
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
var paths = [];

var xCase, yCase;

var ticks = -1;
var renderSinceTick = -1;


function initMap(json_log) {
    //initImg();
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

    function drawDrone(x, y, img) {
        drawObject(img, x, y, SIZE_IMG.DRONE);
    }

    paths.forEach(function (path) {
        /*var x = path.departure.x + ((path.total - path.remaining) / path.total) * (path.arrival.x - path.departure.x);
        var y = path.departure.y + ((path.total - path.remaining) / path.total) * (path.arrival.y - path.departure.y);*/
        var control = getControlPoint(path.departure.x, path.departure.y, path.arrival.x, path.arrival.y);
        var end = getQuadraticBezierXYatPercent(path.departure, control, path.arrival, (path.total - path.remaining) / path.total);
        drawDrone(end.x, end.y, document.getElementById('img_flying'));
    });

    drones.forEach(function (d) {
        if (d[ticks] == undefined)
            return;
        if (d[ticks].remaining == 0) {
            //drawDrone(d[ticks].arrival.x, d[ticks].arrival.y);
            //var changes = 0;
            /*d[ticks].inventory.forEach(function (item) {
                changes = d[ticks+1].inventory.item - d[ticks].inventory.item;
            });*/
            /*if (d[ticks-1] == undefined) {
                changes = 1;
            } else {
                $.each(d[ticks].inventory, function (index, value) {
                    changes = d[ticks - 1].inventory[index] - value;
                });
            }*/
            if (d[ticks].type == "unload" || d[ticks].type == "deliver")
                drawDrone(d[ticks].arrival.x, d[ticks].arrival.y, document.getElementById('img_unloading'));
            else if (d[ticks].type == "load")
                drawDrone(d[ticks].arrival.x, d[ticks].arrival.y, document.getElementById('img_loading'));
            else
                drawDrone(d[ticks].arrival.x, d[ticks].arrival.y, document.getElementById('img_flying'));
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


function drawPath(dx, dy, ax, ay) {
    //TODO: Pour le moment, en ligne droite
    var control = getControlPoint(dx, dy, ax, ay);
    var dxPx = dx * xCase + xCase / 2;
    var dyPx = dy * yCase + yCase / 2;
    var axPx = ax * xCase + xCase / 2;
    var ayPx = ay * yCase + yCase / 2;
    ctx.lineWidth = "5";
    ctx.beginPath();
    ctx.arc(control.x * xCase, control.y * yCase, 5, 0, 2 * Math.PI, false);
    ctx.fillStyle = 'red';
    ctx.fill();
    ctx.beginPath();
    ctx.strokeStyle = COLOR.PATH;
    ctx.moveTo(dxPx, dyPx);
    //ctx.lineTo(ax * xCase + xCase / 2, ay * yCase + yCase / 2);
    ctx.quadraticCurveTo(control.x * xCase, control.y * yCase, axPx, ayPx);
    ctx.stroke();
}

function getControlPoint(dx, dy, ax, ay) {
    var teta = Math.atan((ax - dx) / (ay - dy));
    var middle = {"x": (dx + ax) / 2, "y": (dy + ay) / 2};
    //console.log(middle);
    var ro = (Math.sqrt(2))/2;
    var control = {"x": ro * Math.cos(teta) + middle.x, "y": middle.y - ro * Math.sin(teta)};
    //console.log(control);
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