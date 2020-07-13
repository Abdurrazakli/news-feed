
var stompClient = null;

function put_data_to_html(objects) {
    console.log(objects);
    html =  "<a class=\"tab-link-wrapper\">"+"<a>";
    $("#searchResult").append(html);
}

function process_response(body) {
    objects = JSON.parse(body);
    put_data_to_html(objects);
}

function get_body() {
    return {'query':$("#query").val(),
        'username' : get_session_id()
    };
}

function get_session_id() {
    stompClient.ws._transport.url;
    url = url.replace(
        "ws://localhost:8080/spring-security-mvc-socket/secured/room/",  "");
    url = url.replace("/websocket", "");
    url = url.replace(/^[0-9]+\//, "");
    console.log("Your current session is: " + url);
    return url;
}

function send_and_get() { // Send data to back
    var body = JSON.stringify(get_body());
    console.log("Data sent to back: ");
    console.log(body);
    stompClient.send("/secured/search",{},body);
}


function open_modal() {
    $("#searchModal").show();
}


function connect() {
    var socket = new SockJS('/ws_0001');
    stompClient =Stomp.over(socket);

    stompClient.connect({},function () {
        stompClient.subscribe("secured/user/queue/specific-user"+"-user" + get_session_id(), function (body) {
            process_response(body);
        })
    });
}


$(function () {
    connect();

    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $("#search").click(function () { open_modal(); send_and_get();});

});
