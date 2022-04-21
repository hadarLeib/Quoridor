import org.glassfish.tyrus.server.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class QuoridorServer {
    public static void main(String[] args) {
        Server server = new Server("localhost", 8080, "/Quoridor", QuoridorServerEndpoint.class);

        try {
            server.start();
            System.out.println("\nServer is up...");
            System.out.println("Press any key to stop the server...\n");
            BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            bufferRead.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
