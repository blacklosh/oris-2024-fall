package ru.itis.cmd1;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener {

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == 71) {
            MyGameService.needNewGlider();
            System.out.println("MyGameService.needNewGlider();");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
