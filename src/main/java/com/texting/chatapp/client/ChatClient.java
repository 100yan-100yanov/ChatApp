package com.texting.chatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private Socket socket = null;
    private BufferedReader inputConsole = null;
    private PrintWriter writer = null;
    private BufferedReader reader = null;

    public ChatClient(String address, int port) {
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to the chat server.");

            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            writer = new PrintWriter(socket.getOutputStream(), true);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = "";

            while (!line.equals("exit")) {
                line = inputConsole.readLine();
                writer.println(line);
                System.out.println(reader.readLine());
            }

            socket.close();
            inputConsole.close();
            writer.close();

        } catch (UnknownHostException u) {
            System.out.println("Host unknown: " + u.getMessage());

        } catch (IOException i) {
            System.out.println("Unexpected exception: " + i.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("127.0.0.1", 5000);
    }
}
