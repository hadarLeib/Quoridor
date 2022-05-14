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

    //opens session server - client
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("[Connection] SessionID = " + session.getId());

        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        this.game = new Game(); //creates game
    }

    //receivesthe session (Session) and message (String) from client
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
        String messageType = obj.get("type").getAsString(); //getting the message type from the client
        //three types of messages:
        //contains type = "f" : player would like to place a fence
        //contains type = "m" : player would like to move his piece
        //contains type = "i" : it is the AI's turn to move

        // `send` message
        if (messageType.equals("s")) {
            return;
        }

        // Fence move
        else if (messageType.equals("f")) {
            //receiveing information from client's message about the fence
            a = obj.get("a").getAsInt();
            b = obj.get("b").getAsInt();
            c = obj.get("c").getAsInt();
            d = obj.get("d").getAsInt();
            firstFenceId = obj.get("firstId").getAsInt();
            secondFenceId = obj.get("secondId").getAsInt();
            fenceType = obj.get("fType").getAsString(); //h - horizontal, v - vertical

            //creating the fence via information received from client's message
            Fence fence = new Fence(firstFenceId, secondFenceId, (fenceType.equals("h")), a, b, c, d);
            fenceLegalErrorType = this.game.checkFenceLegal(fence);

            if (fenceLegalErrorType == 1) { // no errors - legal fence move

                //place fence
                this.game.addFenceToMap(fence);
                this.game.getCurrPlayer().useFence();
                this.game.switchPayer();
                this.game.getBoard().removeEdge(a, b);
                this.game.getBoard().removeEdge(c, d);
            }

            innerObject.addProperty("isLegal", (fenceLegalErrorType == 1)); // if returns one, no errors
            innerObject.addProperty("errorType", fenceLegalErrorType);
        }

        // Player move
        else if (messageType.equals("m")) {
            //receiveing information from client's message about the movement
            oldPos = obj.get("oldPos").getAsInt();
            newPos = obj.get("newPos").getAsInt();

            //legal or not
            isAdj = this.game.checkIfLegalConsideringJump(oldPos, newPos);

            if (isAdj) {
                //is legal - move piece
                this.game.setPlayerPos(this.game.getCurrPlayer(), newPos);
                this.game.switchPayer();
            }

            innerObject.addProperty("isLegal", isAdj);
        }

        // AI's turn
        else if (messageType.equals("i")) {
            Minimax minimaxAlphaBeta = new Minimax(2); ////////// depth change ///////////
            Move move = new Move();
            Game gameCopy = new Game(this.game);

            move = minimaxAlphaBeta.bestMoveCalc(gameCopy);
            //move will be the best move for the computer to make

            if (move.getMoveType().equals("f")) {
                //the move is a fence placement

                //place fence
                this.game.addFenceToMap(((Fence) move));
                this.game.getCurrPlayer().useFence();
                this.game.switchPayer();
                this.game.getBoard().removeEdge(((Fence) move).getA(), ((Fence) move).getB());
                this.game.getBoard().removeEdge(((Fence) move).getC(), ((Fence) move).getD());

                //preparing message for client (from server)
                innerObject.addProperty("aiMoveType", "f");
                innerObject.addProperty("fenceID", ((Fence)move).getFirstId());

            }

            else if (move.getMoveType().equals("m")) {
                //the moveis a piece movement

                //move piece
                this.game.setPlayerPos(this.game.getCurrPlayer(), ((PlayerMove) move).getPlayerNewPos());
                this.game.switchPayer();

                //preparing message for client (from server)
                innerObject.addProperty("aiMoveType", "m");
                innerObject.addProperty("oldPos", ((PlayerMove)move).getPlayerOldPos());
                innerObject.addProperty("newPos", ((PlayerMove)move).getPlayerNewPos());
            }

            innerObject.addProperty("isLegal", true);

        }

        // type of message the client should deal with
        //("m" - piece movement, "f" - fence placement, "i" - ai turn)
        innerObject.addProperty("messageType", messageType);
        str = gson.toJson(innerObject);

        try {
            session.getBasicRemote().sendText(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    //close session
    @OnClose
    public void onClose(Session session) {
        System.out.println("[Disconnection] SessionID = " + session.getId());
    }

    //error catch
    @OnError
    public void onError(Throwable e) {
        System.out.println("[Error] " + e);
    }
}
