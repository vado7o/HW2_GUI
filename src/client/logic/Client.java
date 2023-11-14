package client.logic;

import client.viewmodel.ClientView;
import server.logic.Server;

public class Client {
    private boolean isOnline;
    private ClientView clientView;
    private Server server;
    private String name;

    public Client(ClientView clientView, Server server) {
        this.clientView = clientView;
        this.server = server;
    }

    public String getName() {
        return name;
    }
    public void message(String text) {
        if (isOnline) {
            if (!text.isEmpty()) {
                server.message(name + ": " + text);
            }
        } else {
            addToLog("Отсутствует подключение к серверу");
        }
    }
    public boolean makeConnection(String name) {
        this.name = name;
        if (server.addClient(this)) {
            String text = server.getLog();
            addToLog(text);
            addToLog("Подключение успешно!\n");
            isOnline = true;
            return true;
        } else {
            addToLog("Подключение неудачно! Попробуйте позднее!");
            return false;
        }
    }
    public void disconnect() {
        if (isOnline) {
            isOnline = false;
            clientView.disconnect();
            server.disconnectClient(this);
            message("Сервер разорвал соединение");
        }
    }
    public void getResponse(String text) {
        addToLog(text);
    }
    private void addToLog(String text) {
        clientView.showMessage(text + "\n");
    }
}