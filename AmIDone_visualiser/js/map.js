/**
 * Created by Jeremy on 18/01/2017.
 */

const COLOR = {"BACKGROUND" : "#F0EDE5"};

const DIV_ID = "map";
const CANVAS_ID = "map_canvas";

var map;
var ctx;

function Map(rows, cols) {
    this.rows = rows;
    this.cols = cols;
}

function init() {
    console.log("init");
    //document.getElementById(DIV_ID).innerHTML = "<canvas id=\"" + CANVAS_ID + "\"></canvas>";
    // Defintion canvas/ctx
    canvas = document.getElementById(CANVAS_ID);
    ctx = canvas.getContext("2d");
    refreshSize();
    // Amelioration du rendu
    ctx.mozImageSmoothingEnabled = true;
    ctx.webkitImageSmoothingEnabled = true;
    ctx.msImageSmoothingEnabled = true;
    ctx.imageSmoothingEnabled = true;
    window.onresize = refreshSize;
    //refreshDisplay();
    setInterval(refreshDisplay, 100);
}

function refreshDisplay() {
    console.log("refreshDisplay");
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawMap();
}

function drawMap() {
    console.log("Draw map");
    ctx.fillStyle = COLOR.BACKGROUND;
    ctx.fillRect(0,0, canvas.width, canvas.height);
    for (var y = 0; y < map.length; y++) {
        for (var x = 0; x < map[y].length; x++) {
            switch (map[y][x]) {
                case "W":
                    //ctx.drawImage(document.getElementById("warehouse"), x * xCase, y * yCase, xCase, yCase);
                    console.log("W");
                    ctx.fillStyle = "#FF0000";
                    ctx.fillRect(x * xCase, y * yCase, xCase, yCase);
                    break;
                case "O":
                    console.log("O "+x+","+y);
                    //ctx.drawImage(document.getElementById("delivery-point"), x * xCase, y * yCase, xCase, yCase);
                    ctx.fillStyle = "#0000FF";
                    ctx.fillRect(x * xCase, y * yCase, xCase, yCase);
                    break;
            }

        }
    }
}

function setJson(event) {
    var input = event.target;

    var reader = new FileReader();
    reader.onload = function(){
        var text = reader.result;
        // SOME STUFF

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
        for(var i = 0; i < map.length; i++) {
            for(var z = 0; z < map[i].length; z++) {
                console.log(map[i][z]);
            }
        }
    };
    reader.readAsText(input.files[0]);
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
    refreshDisplay();
}
