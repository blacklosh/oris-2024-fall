package ru.itis.cmd1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static ru.itis.cmd1.Main.*;

public class MyMouseListener implements MouseListener {

    public static int X_PADDING = 0;
    public static int Y_PADDING = 0;

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Мышь тапнули");
        int x = e.getX() - X_PADDING;
        int y = e.getY() - Y_PADDING;

        int blockX = x / BS;
        int blockY = y / BS;

        if(MouseEvent.BUTTON1 == e.getButton()) {
            MAP[blockX][blockY] = 1;
        } else {
            MAP[blockX][blockY] = 0;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
