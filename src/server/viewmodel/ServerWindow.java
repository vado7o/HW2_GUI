package server.viewmodel;

import server.logic.Server;
import server.storage.FileStorage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements ServerView {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;

    private JButton btnStart, btnStop;
    private JTextArea logPanel;
    private Server server;

    public ServerWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setTitle("Server");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        server = new Server(this, new FileStorage());

        logPanel = new JTextArea();
        JPanel controlPanel = new JPanel(new GridLayout(1, 2));
        btnStart = new JButton("Start");
        btnStop = new JButton("Stop");

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.startConnection();
            }
        });

        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                server.stopConnection();
            }
        });

        controlPanel.add(btnStart);
        controlPanel.add(btnStop);
        add(logPanel);
        add(controlPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public Server getServer(){
        return server;
    }

    @Override
    public void refreshLog(String text) {
        logPanel.append(text);
    }
}
