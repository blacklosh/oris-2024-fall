package ru.itis.cmd4.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServerService implements Runnable {

    private final ServerSocket serverSocket;

    //TODO удалять клиентов после отключения
    private static final List<ClientHandler> clients = new CopyOnWriteArrayList<>();

    public ChatServerService(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            // TODO обработать ситуацию, когда порт занят
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        while (1>0) {
            try {
                Socket socket = serverSocket.accept();
                ClientHandler clientHandler = new ClientHandler(socket);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            } catch (IOException e) {
                //TODO отловить
                throw new RuntimeException(e);
            }
        }
    }

    public static void broadcast(String message) {
        System.out.println("BROADCAST");
        for(ClientHandler handler : clients) {
            handler.sendMessageToThisSocket(message);
        }
    }

    private static class ClientHandler implements Runnable {
        private final PrintWriter out;
        private final BufferedReader in;

        private ClientHandler(Socket socket) {
            try {
                out = new PrintWriter(socket.getOutputStream());
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            } catch (IOException e) {
                // TODO обработать ошибки канала
                throw new RuntimeException(e);
            }
        }

        @Override
        public void run() {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    broadcast(line);
                }
            } catch (Exception e) {
                // TODO обработать неожиданное завершение общения
            }
        }

        public void sendMessageToThisSocket(String message) {
            out.println(message);
            out.flush();
        }
    }
}
