class WebSocket{

    constructor(endPoint, port){
        this.endPoint = endPoint;
        this.stompClient = null;
        this.data = null;
        this.port = port;
    }

    connect(){
        var that = this;
        var socket = new SockJS('http://localhost:'+this.port+'/'+this.endPoint);
        this.stompClient = Stomp.over(socket);
        this.stompClient.connect({}, function(frame) {
            console.log('Connected: '+that.endPoint+ ' ' + frame);
            that.stompClient.subscribe('/topic/'+that.endPoint, function(messageOutput) {
                that.data = JSON.parse(messageOutput.body);
            });
        });
    }

    disconnect() {
        if(this.stompClient != null) {
            this.stompClient.disconnect();
        }
        setConnected(false);
        console.log("Disconnected");
    }

    sendMessage() {
        this.stompClient.send("/app/"+this.endPoint, {}, null);
    }
}

const webSocket1 = new WebSocket('traffic', 8081),
    webSocket2 = new WebSocket('nodes', 8081),
    webSocket3 = new WebSocket('map', 8081),
    webSocket4 = new WebSocket('traffic-lights', 8082),
    webSocket5 = new WebSocket('information', 8083),
    webSocket6 = new WebSocket('information', 8081);
webSocket1.connect();
webSocket2.connect();
webSocket3.connect();
webSocket4.connect();
webSocket5.connect();
webSocket6.connect();