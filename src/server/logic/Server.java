package server.logic;

import client.logic.Client;
import server.storage.Repo;
import server.viewmodel.ServerView;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private List<Client> clientBase;
    private boolean launching;
    private ServerView serverView;
    private Repo repo;

    public Server(ServerView serverView, Repo repo) {
        this.repo = repo;
        this.serverView = serverView;
        clientBase = new ArrayList<>();
    }

    public void startConnection() {
        if (launching) logRefresh("Сервер запущен ранее!");
        else {
            launching = true;
            logRefresh("Сервер запущен!");
        }
    }

    public void stopConnection() {
        if (!launching) logRefresh("Сервер не запущен!");
        else {
            launching = false;
            while (!clientBase.isEmpty()) {
                disconnectClient(clientBase.get(0));
            }
            logRefresh("Сервер отключен");
        }
    }

    public boolean addClient(Client client) {
        if (!launching) return false;
        else {
            logRefresh(client.getName() + " онлайн!");
            clientBase.add(client);
            return true;
        }
    }

    public void message(String str) {
        if (!launching) return;
        else {
            str += "";
            logRefresh(str);
            for (Client client : clientBase) {
                client.getResponse(str);
            }
            repo.save(str);
        }
    }

    public String getLog() {
        return repo.load();
    }

    private void logRefresh(String text) {
        serverView.refreshLog(text + "\n");
    }
    public void disconnectClient(Client client) {
        clientBase.remove(client);
        if (client != null) {
            client.disconnect();
            logRefresh(client.getName() + " отсоединился");
        }
    }

}
