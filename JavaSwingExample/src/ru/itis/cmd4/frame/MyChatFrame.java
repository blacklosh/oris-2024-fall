package ru.itis.cmd4.frame;

import ru.itis.cmd4.services.ChatClientService;

import javax.swing.*;
import java.awt.*;

public class MyChatFrame extends JFrame {

    private final JTextArea textArea = new JTextArea();
    private final JTextField messageField = new JTextField();
    private final JButton sendButton = new JButton("Отправить!");
    private final Font font = new Font("arial", Font.PLAIN, 24);
    private ChatClientService service;

    public void setService(ChatClientService service) {
        this.service = service;
    }

    public MyChatFrame() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setLayout(null);
        //TODO icon
        setTitle("My Itis Chat");

        //TODO ScrollBar
        textArea.setFont(font);
        textArea.setBounds(0, 0, 800, 500);
        textArea.setEditable(false);
        textArea.setBackground(Color.LIGHT_GRAY);
        textArea.setFocusable(false);
        add(textArea);

        messageField.setFont(font);
        messageField.setBounds(0, 500, 600, 50);
        add(messageField);

        sendButton.addActionListener(e -> handleSendButton());
        sendButton.setFont(font);
        sendButton.setBounds(600, 500, 200, 50);
        sendButton.setBackground(Color.BLACK);
        sendButton.setForeground(Color.WHITE);
        add(sendButton);
    }

    private void handleSendButton() {
        //TODO передать фокус обратно на текстовое поле
        String message = messageField.getText();
        messageField.setText("");
        if(service != null) {
            service.sendMessage(message);
        }
    }

    public void addMessage(String message) {
        textArea.setText(textArea.getText() + message + "\n");
    }

}
