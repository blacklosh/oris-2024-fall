package ru.itis.cmd4.services;

import ru.itis.cmd4.frame.MyChatFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatClientService implements Runnable {

    private final PrintWriter out;
    private final BufferedReader in;
    private final String nickname;
    private final MyChatFrame frame;

    public ChatClientService(String host, int port, String nickname, MyChatFrame frame) {
        try {
            Socket socket = new Socket(host, port);
            this.nickname = nickname;
            this.frame = frame;
            out = new PrintWriter(socket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            //TODO обработать ошибки подключения
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(String message) {
        out.println("[" + LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
                + " " + nickname + "]: " + message);
        out.flush();
    }

    @Override
    public void run() {
        try {
            String line;
            while ((line = in.readLine()) != null) {
                frame.addMessage(line);
            }
        } catch (Exception e) {
            // TODO обработать неожиданное завершение общения
        }
    }
}
