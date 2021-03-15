function loadLocationsAtNodes(){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8081/location/nodes/number',
        success: function(data){
            locations = data;
        }
    });
}

function loadTraffic(){
    $.ajax({
        type: 'GET',
        url: 'http://localhost:8081/location/drivers/traffic',
        success: function(data){
            traffic = data;
        }
    });
}

function step(){
    $.ajax({
        type: 'PUT',
        url: 'http://localhost:8081/vehicles/step',
        success: function(data){
            map();
        }
    });
}

function register(){
    $.ajax({
        type: 'POST',
        url: 'http://localhost:9000/registry',
        data: JSON.stringify({"name":"traffic-step", "uri":"http://localhost:8081/vehicles/step"}),
        success: function(data){
            console.log("Registered!");
        },
        crossDomain: true,
        headers: {
            'Content-Type':'application/json'
        }
    });
}

function unregister(){
    $.ajax({
        type: 'DELETE',
        url: 'http://localhost:9000/registry',
        success: function(data){
            console.log("Unregistered!");
        },
        crossDomain: true,
    });
}