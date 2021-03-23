var maxX = 0;
var maxY = 0;

var locations = {},
    traffic = {},
    graph = {},
    trafficLights = {};

var x = {},
    y = {};

var isSet = false;

function map(){
    $("#time").html("[" + webSocket5.data.time+"]");

    graph = webSocket3.data;

    let canvas = d3.select("#network");

    let width = canvas.attr("width"),
        height = canvas.attr("height"),
        r = 10,
        ctx = canvas.node().getContext("2d");

    let simulation = d3.forceSimulation(graph.nodes)
        .force("link", d3.forceLink()
            .id(function (d) {return d.name;})
            .links(graph.links)
        )
        .force("charge", d3.forceManyBody().strength(-1500))
        .force("center", d3.forceCenter(width/2, height/2))
        .on("end", update);

    function update(){
        ctx.clearRect(0,0, width, height);
        locations = webSocket2.data;
        traffic = webSocket1.data;
        trafficLights = webSocket4.data;
        graph.nodes.forEach(drawNode);
        isSet = true;
        graph.links.forEach(drawLink);
        graph.links.forEach(drawVehicleAtLink);
        graph.nodes.forEach(drawVehicleAtNode);
    }

    function drawNode(d){

        if(isSet === false){
            if(d.x > maxX) maxX = d.x;
            if(d.y > maxY) maxY = d.y;
            x[d.id] = d.x;
            y[d.id] = d.y;
        }

        d.x = x[d.id];
        d.y = y[d.id];

        ctx.beginPath();
        ctx.fillStyle = (d.type === "HOME" || d.type === "WORK") ? "#0033ff" : "#f54242";
        ctx.moveTo(d.x, d.y);
        ctx.arc(d.x, d.y, r, 0, 2* Math.PI);
        ctx.fillText("ID: " + d.id + " : " + d.name, d.x+r,d.y+r);
        ctx.fill();
    }

    function drawLink(l){
        ctx.beginPath();
        ctx.lineWidth = 8;
        ctx.strokeStyle = "#000000";
        ctx.moveTo(l.source.x, l.source.y);
        ctx.fillText("L: " + l.length, (l.source.x+l.target.x)/2, (l.source.y+l.target.y)/2);
        ctx.lineTo(l.target.x, l.target.y);
        ctx.stroke();
    }

    function drawVehicleAtNode(d){
        let num = locations[d.name];

        for(let i = 0; i < num; i++){
            ctx.beginPath();
            ctx.fillStyle = "#f5ef42"
            ctx.moveTo(d.x, d.y);
            ctx.arc(d.x+(i*5), d.y, 3, 0, 2* Math.PI);
            ctx.fill();
        }
    }

    function drawVehicleAtLink(l){
        let segments = traffic[l.relationshipId].cells;
        let tl = trafficLights[l.target.id];

        let source_x = l.source.x,
            source_y = l.source.y,
            target_x = l.target.x,
            target_y = l.target.y;

        let diff_x = target_x - source_x;
        let diff_Y = target_y - source_y;

        let inc_x = diff_x/segments;
        let inc_Y = diff_Y/segments;

        //draw traffic light
        if(tl) {
            ctx.beginPath();
            console.log(tl);
            console.log(tl.tlsstate);
            let sec = -1;
            for(let i = 0; i < tl.inLinks.length; i++){
                if(tl.inLinks[i] === l.relationshipId){
                    sec = i;
                    break;
                }
            }

            if(sec !== -1){
                switch (tl.tlsstate.state[sec*tl.outLinks.length]){
                    case "GREEN":
                        ctx.fillStyle = "#00a80e";
                        break;
                    case "AMBER":
                        ctx.fillStyle = "#fc9803";
                        break;
                    case "RED":
                        ctx.fillStyle = "#ff0000";
                        break;
                    default:
                        break;
                }
                ctx.moveTo(source_x+(inc_x*(segments-1)), source_y+(inc_Y*(segments-1)));
                ctx.arc(source_x+(inc_x*(segments-1)), source_y+(inc_Y*(segments-1)), 6, 0, 2*Math.PI);
                ctx.fill();
            }
        }

        for(let i = 0; i < segments; i++){
            //draw lane direction visualisation
            if(i === (segments-1) ){
                ctx.beginPath();
                ctx.lineWidth=1;
                ctx.strokeStyle = "#ffffff";
                ctx.moveTo(source_x+(inc_x*i), source_y+(inc_Y*i));
                ctx.lineTo(l.target.x, l.target.y);
                ctx.stroke();
            }
            //draw vehicle
            let vehicle = traffic[l.relationshipId].traffic[i];
            if(vehicle != null){
                ctx.beginPath();
                ctx.fillStyle = "#f5ef42";
                ctx.moveTo(source_x+(inc_x*i), source_y+(inc_Y*i));
                ctx.arc(source_x+(inc_x*i), source_y+(inc_Y*i), 4, 0, 2* Math.PI);
                ctx.fillText("ID: " + traffic[l.relationshipId].traffic[i].id, source_x+(inc_x*i)+10, source_y+(inc_Y*i)+10);
                ctx.fill();
                ctx.beginPath();
                ctx.fillStyle = "#000000";
                ctx.fillText("ID: " + vehicle.id + "[SPEED: " + vehicle.speed+ "]", source_x+(inc_x*i)+10, source_y+(inc_Y*i)+10);
                ctx.fill();
            }
        }
    }
}

window.setInterval(map, 3000);
