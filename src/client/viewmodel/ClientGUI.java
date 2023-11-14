package client.viewmodel;

import client.logic.Client;
import server.viewmodel.ServerWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ClientGUI extends JFrame implements ClientView {
    public static final int WIDTH = 400;
    public static final int HEIGHT = 300;
    JTextArea log;
    JTextField ipPanel, portTextView, loginPanel, messPanel;
    JPasswordField passwordPanel;
    JButton btnLogin, btnSend;
    JPanel topPanel;
    private Client client;

    public ClientGUI(ServerWindow server, String name) {
        client = new Client(this, server.getServer());
        setTitle("Chat client");
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setLocation(server.getX() - 500, server.getY());
        setDefaultCloseOperation(HIDE_ON_CLOSE);

        topPanel = new JPanel(new GridLayout(2, 3));
        ipPanel = new JTextField("127.0.0.1");
        portTextView = new JTextField("8189");
        loginPanel = new JTextField(name);
        passwordPanel = new JPasswordField("123456");
        btnLogin = new JButton("login");
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        topPanel.add(ipPanel);
        topPanel.add(portTextView);
        topPanel.add(new JPanel());
        topPanel.add(loginPanel);
        topPanel.add(passwordPanel);
        topPanel.add(btnLogin);

        log = new JTextArea();
        log.setEditable(false);
        JScrollPane jScrollPane =  new JScrollPane(log);
        JPanel textPanel = new JPanel(new BorderLayout());
        messPanel = new JTextField();
        btnSend = new JButton("send");
        btnSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                message();
            }
        });
        textPanel.add(messPanel);
        textPanel.add(btnSend, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(jScrollPane);
        add(textPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public void login(){
        if (client.makeConnection(loginPanel.getText())) topPanel.setVisible(false);
    }
    public void disconnect(){
        topPanel.setVisible(true);
        client.disconnect();
    }
    public void showMessage(String message) {
        log.append(message);
    }
    private void message(){
        client.message(messPanel.getText());
        messPanel.setText("");
    }
}
