package ru.itis.cmd1.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class MyFrame extends JFrame {


    public MyFrame(JPanel panel, MouseListener mouseListener, KeyListener keyListener) throws HeadlessException {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 810, 640);
        setTitle("Game of life");
        setContentPane(panel);

        addMouseListener(mouseListener);
        setFocusable(true);

        addKeyListener(keyListener);

    }
}
