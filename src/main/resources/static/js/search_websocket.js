
var stompClient = null;


function process_response(response) {

}

function connect() {
    var wSocket = new SockJs("/search");
    stompClient =Somp.over(wSocket);
    stompClient.connect({},function () {
        stompClient.subscribe("/topic/search", function (response) {
            process_response(response);
        })
    });
}

connect();