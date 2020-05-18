import javax.swing.*;
import java.net.*;

public class Main {

    public static void main(String[] args) throws Exception  {
        Controller controller = new Controller();
        controller.start();


        /*SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Window();
            }
        });*/


        /*try {
            // open websocket
            final WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI("ws://0.0.0.0:27800"));

            // add listener
            clientEndPoint.addMessageHandler(new WebsocketClientEndpoint.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{\"action\":\"PublicMessage\",\"chat_id\":1,\"user_author\":1,\"message\":\"message\"}");

            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }*/
    }
}
