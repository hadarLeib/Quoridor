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

        int a, b, c, d, oldPos, newPos;
        JsonObject innerObject = new JsonObject();
        Gson gson = new Gson();
        boolean isAdj, isFenceLegal;
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

            this.game.getBoard().removeEdge(a, b);
            this.game.getBoard().removeEdge(c, d);

            isFenceLegal = this.game.getBoard().isValidFence(this.game.getCurrentPlayerPos(),
                    this.game.getCurrPlayer());

            if (!isFenceLegal) {
                this.game.getBoard().addEdge(a, b);
                this.game.getBoard().addEdge(c, d);
            }

            innerObject.addProperty("isLegal", isFenceLegal);
            
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
