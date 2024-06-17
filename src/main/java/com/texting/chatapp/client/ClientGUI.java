package com.texting.chatapp.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private ChatClient client;
    private JButton exitButton;

    public ClientGUI() {
        super("Chat Application");

        buildGUI();
        startClient();
    }

    private void buildGUI() {
        // GUI size, message area and text field
        setSize(400, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        textField = new JTextField();

        textField.addActionListener(e -> {
            client.sendMessage(textField.getText());
            textField.setText("");
        });

        add(textField, BorderLayout.SOUTH);

        // Username prompt
        String name = JOptionPane.showInputDialog(
                this,
                "Enter your name:",
                "Name entry",
                JOptionPane.PLAIN_MESSAGE);

        // Username on window title
        this.setTitle("Chat Application - " + name);

        // Username and time stamp on messages
        textField.addActionListener(e -> {
            String message = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) +
                    "] " + name + ": " + textField.getText();

            client.sendMessage(message);
            textField.setText("");
        });

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void startClient() {
        try {
            this.client = new ChatClient("127.0.0.1", 5000, this::onMessageReceived);
            client.startClient();

        } catch (IOException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Error connecting to the server",
                    "Connection error",
                    JOptionPane.ERROR_MESSAGE);

            System.exit(1);
        }
    }

    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI().setVisible(true));
    }
}
