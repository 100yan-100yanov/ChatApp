package com.texting.chatapp.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable{
    private final Socket clientSocket;
    private final List<ClientHandler> clients;
    private final PrintWriter writer;
    private final BufferedReader reader;

    public ClientHandler(Socket clientSocket, List<ClientHandler> clients) throws IOException {
        this.clientSocket = clientSocket;
        this.clients = clients;
        this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            String inputLine;

            while ((inputLine = reader.readLine()) != null) {
                for (ClientHandler client : clients) {
                    client.writer.println(inputLine);
                }
            }

        } catch (IOException e) {
            System.out.println("An error occurred: " + e.getMessage());

        } finally {
            try {
                reader.close();
                writer.close();
                clientSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
