function updateInformation(){
    $("#info").empty();
    for(var i = 0; i < webSocket6.data.roundTrips.length; i++ ){
        $("#info").append("<p>[Vehicle with Id "+webSocket6.data.roundTrips[i].vehicleId + " took " + webSocket6.data.roundTrips[i].totalTime+"s]</p>");
    }
    for(let i = 0; i < webSocket6.data.crashLog.length; i++ ){
        $("#info").append("<p>"+webSocket6.data.crashLog[i]+"</p>");
    }
}