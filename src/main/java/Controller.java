import javax.swing.*;

public class Controller {
    private Chat chat = null;
    private Window window = null;

    public Chat getChat() {
        return chat;
    }

    public Window getWindow() {
        return window;
    }

    public void start() {
        chat = Chat.getInstance(this);
        chat.connect();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                window = new Window(Controller.this);
            }
        });
    }
}
