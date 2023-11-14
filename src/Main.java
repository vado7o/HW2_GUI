import client.viewmodel.ClientGUI;
import server.viewmodel.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        ClientGUI client1 = new ClientGUI(serverWindow, "Vadim");
        ClientGUI client2 = new ClientGUI(serverWindow, "Stepan");
    }
}