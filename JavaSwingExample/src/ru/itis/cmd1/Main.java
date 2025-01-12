package ru.itis.cmd1;

import ru.itis.cmd1.frame.MyFrame;
import ru.itis.cmd1.frame.MyPanel;

import java.awt.*;

public class Main {

    public static final int W_COUNT = 50;
    public static final int H_COUNT = 30;

    public static final int[][] MAP = new int[W_COUNT][H_COUNT];

    public static final int BS = 16;

    public static void main(String[] args) {
        MyPanel panel = new MyPanel();
        MyMouseListener mouseListener = new MyMouseListener();
        MyKeyListener keyListener = new MyKeyListener();
        MyFrame myFrame = new MyFrame(panel, mouseListener, keyListener);
        myFrame.setVisible(true);
        MyGameService gameService = new MyGameService(1);
        Insets insets = myFrame.getInsets();
        MyMouseListener.X_PADDING = insets.left;
        MyMouseListener.Y_PADDING = insets.top;
    }
}