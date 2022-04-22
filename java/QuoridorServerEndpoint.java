import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

@ServerEndpoint(value = "/echo")
public class QuoridorServerEndpoint {
    Game game;

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("[Connection] SessionID = " + session.getId());

        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.game = new Game();
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("[Message] " + message);

        int a, b, c, d, oldPos, newPos, fenceLegalErrorType, firstFenceId, secondFenceId;
        String fenceType = "";
        JsonObject innerObject = new JsonObject();
        Gson gson = new Gson();
        boolean isAdj;
        String str = "";

        JsonParser parser = new JsonParser();
        JsonObject obj = parser.parse(message).getAsJsonObject();
        String messageType = obj.get("type").getAsString();

        // `send` message
        if (messageType.equals("s")) {
            return;
        }

        // Fence move
        else if (messageType.equals("f")) {
            a = obj.get("a").getAsInt();
            b = obj.get("b").getAsInt();
            c = obj.get("c").getAsInt();
            d = obj.get("d").getAsInt();
            firstFenceId = obj.get("firstId").getAsInt();
            secondFenceId = obj.get("secondId").getAsInt();
            fenceType = obj.get("fType").getAsString();

            fenceLegalErrorType = this.game.checkFenceLegal(firstFenceId, secondFenceId, a, b, c, d);
            if(fenceLegalErrorType == 1){ // no errors - legal fence move
                this.game.addFenceToMap(firstFenceId, secondFenceId, (fenceType.equals("h")));
            }

            innerObject.addProperty("isLegal", (fenceLegalErrorType==1)); // if returns one, no errors
            innerObject.addProperty("errorType", fenceLegalErrorType);
            
        }

        // Player move
        else if (messageType.equals("m")) {
            oldPos = obj.get("oldPos").getAsInt();
            newPos = obj.get("newPos").getAsInt();

            isAdj = this.game.checkIfLegalConsideringJump(oldPos, newPos);

            if (isAdj) {
                this.game.setPlayerPos(this.game.getCurrPlayer(), newPos);
                this.game.switchPayer();
            }

            innerObject.addProperty("isLegal", isAdj);
        }

        innerObject.addProperty("messageType", messageType);
        str = gson.toJson(innerObject);

        try {
            session.getBasicRemote().sendText(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("[Disconnection] SessionID = " + session.getId());
    }

    @OnError
    public void onError(Throwable e) {
        System.out.println("[Error] " + e);
    }
}
