var webSocket;
var messages;

function sockets() {
    messages = document.getElementById("messages");
}

function openSocket() {
    // Ensures only one connection is open at a time
    if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED) {
        writeResponse("WebSocket is already opened.");
        return;
    }

    // Create a new instance of the websocket
    webSocket = new WebSocket("ws://localhost:8080/Quoridor/echo");

    /**
     * Binds functions to the listeners for the websocket.
     */
    webSocket.onopen = function (event) {
        // For reasons I can't determine, onopen gets called twice
        // and the first time event.data is undefined.
        if (event.data === undefined)
            return;

        writeResponse(event.data);
    };

    webSocket.onmessage = function (event) {
        var obj = JSON.parse(event.data);

        if (typeof obj == "object") {
            //Json

            if(obj.messageType == "f"){
                if(obj.isLegal == true){
                    placeFenceForReal();
                }

                // fence on fence
                else if (obj.errorType == 2){
                    alert("There already is a fence there");
                }

                // fence out of bounds
                else if (obj.errorType == 3){
                    alert("Fence is out of bounds");
                }

                // fence will completely block player
                else if (obj.errorType == 4){
                    alert("Placing a fence there will completely block the other player");
                }

            }

            else if(obj.messageType == "m"){
                if(obj.isLegal == true)
                    movePlayerForReal();
                else
                    alert("Can't jump to there")
            }


            writeResponse("" + obj.isLegal);
        }
        
        else {
            //Not Json
            writeResponse(event.data);
        }
    };

    webSocket.onclose = function (event) {
        writeResponse("Connection closed.");
    };
}

/**
 * Sends the value of the text input to the server
 */
function send() {
    var text = document.getElementById("messageinput").value;

    // [Ido] Added type to prevent error on the server when trying 
    // to read "type" for regular `send` messages.
    var obj = { "type": "s", "message": text, "name": "hadar" };

    webSocket.send(JSON.stringify(obj));
}

function sendFence(fenceId, nextFenceId, a, b, c, d, fType) {
    var obj = { type: "f", firstId: fenceId, secondId: nextFenceId, a: a, b: b, c: c, d: d, fType: fType };
    webSocket.send(JSON.stringify(obj));
}

function sendMove(oldPos, newPos) {
    var obj = { type: "m", oldPos: oldPos, newPos: newPos }
    webSocket.send(JSON.stringify(obj));
}

function closeSocket() {
    webSocket.close();
}

function writeResponse(text) {
    messages.innerHTML += "<br/>" + text;
}
