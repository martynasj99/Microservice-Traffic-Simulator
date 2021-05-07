function updateInformation(){
    let info = $("#info");
    info.empty();
    for(var i = 0; i < webSocket6.data.roundTrips.length; i++ ){
        let roundTrip = webSocket6.data.roundTrips[i];
        info.append("<p>[Vehicle with Id "+roundTrip.vehicleId + " took " + roundTrip.totalTime+"s from "+roundTrip.route.startNode+" to "+roundTrip.route.endNode+"]</p>");
    }
    for(let i = 0; i < webSocket6.data.crashLog.length; i++ ){
        info.append("<p>"+webSocket6.data.crashLog[i]+"</p>");
    }
}