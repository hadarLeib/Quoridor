import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
 
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
 
/** 
 * @ServerEndpoint gives the relative name for the end point
 * This will be accessed via ws://localhost:8080/EchoChamber/echo
 * Where "localhost" is the address of the host,
 * "EchoChamber" is the name of the package
 * and "echo" is the address to access this class from the server
 */
@ServerEndpoint("/echo") 
public class server {
    Game game;
    /**
     * @OnOpen allows us to intercept the creation of a new session.
     * The session class allows us to send data to the user.
     * In the method onOpen, we'll let the user know that the handshake was 
     * successful.
     */
    @OnOpen
    
    public void onOpen(Session session){
        System.out.println(session.getId() + " has opened a connection");
        System.out.println("h");
        try {
            session.getBasicRemote().sendText("Connection Established");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.game = new Game();
        this.game.getBoard().printAdjGraph();

    }
 
    /**
     * When a user sends a message to the server, this method will intercept  react to it.
     * and allow us to react to it. For now the message is read as a String.
     */
    @OnMessage
    public void onMessage(String message, Session session){
        int a, b, c, d, oldPos, newPos;
        System.out.println("Message from " + session.getId() + ": " + message);
        JsonParser parser = new JsonParser(); 
        JsonObject obj = parser.parse(message).getAsJsonObject(); 
        String messageType = obj.get("type").getAsString();
        
        if(messageType.equals("f")){
            a = obj.get("a").getAsInt();
            b = obj.get("b").getAsInt();
            c = obj.get("c").getAsInt();
            d = obj.get("d").getAsInt();
            
            this.game.getBoard().removeEdge(a, b);
            this.game.getBoard().removeEdge(c, d);
        }
        
        else{
            oldPos = obj.get("oldPos").getAsInt();
            newPos = obj.get("newPos").getAsInt();
        }
        
        try {
            session.getBasicRemote().sendText(message);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
 
    /**
     * The user closes the connection.
     * 
     * Note: you can't send messages to the client from this method
     */
    @OnClose
    public void onClose(Session session){
        System.out.println("Session " +session.getId()+" has ended");
    }
}
