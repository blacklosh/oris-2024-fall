package ru.itis.cmd4;

import ru.itis.cmd4.frame.MyChatFrame;
import ru.itis.cmd4.services.ChatClientService;
import ru.itis.cmd4.services.ChatServerService;

import javax.swing.*;

public class Main4 {

    public static void main(String[] args) {
        MyChatFrame frame = new MyChatFrame();

        String nickname = JOptionPane.showInputDialog("Как вас представить?");
        //TODO обработать ситуации с пустым ником и если пользователь закрыл окно

        int result = JOptionPane.showConfirmDialog(null, "Хотите ли стать хостом?");
        if(result == JOptionPane.YES_OPTION) {
            ChatServerService chatServerService = new ChatServerService(1234);
            new Thread(chatServerService).start();
            ChatClientService clientService = new ChatClientService("127.0.0.1", 1234, nickname, frame);
            frame.setService(clientService);
            new Thread(clientService).start();
        } else {
            // TODO обработать ситуации с CANCEL_OPTION и CLOSE
            String host = JOptionPane.showInputDialog("Введите хост хозяина комнаты");
            //TODO обработать ситуации с пустым ником и если пользователь закрыл окно

            ChatClientService clientService = new ChatClientService(host, 1234, nickname, frame);
            frame.setService(clientService);
            new Thread(clientService).start();
        }

        frame.setVisible(true);
    }

}
