package ru.itis.cmd1.frame;

import ru.itis.cmd1.MyGameService;

import javax.swing.*;
import java.awt.*;

import static ru.itis.cmd1.Main.*;

public class MyPanel extends JPanel {

    private JToggleButton toggle;

    private JSlider slider;

    public MyPanel() {
        setLayout(null);
        toggle = new JToggleButton("Остановить время");
        toggle.setBounds(10, 10, 150, 40);
        toggle.addActionListener(e -> handleButtonClick());
        toggle.setFocusable(false);
        add(toggle);

        slider = new JSlider(0, 15);
        slider.setBounds(170, 10, 150, 20);
        slider.addChangeListener(e -> handleSliderChange());
        slider.setFocusable(false);
        add(slider);
    }

    private void handleSliderChange() {
        MyGameService.setSkipIterations(slider.getValue());
    }

    private void handleButtonClick() {
        if(toggle.isSelected()) {
            MyGameService.setActive(false);
        } else {
            MyGameService.setActive(true);
        }
    }

    @Override
    public void paintComponent(Graphics gr) {
        super.paintComponent(gr);

        for(int x = 0; x < W_COUNT; x++) {
            for(int y = 0; y < H_COUNT; y++) {
                if(MAP[x][y] == 1) {
                    gr.setColor(Color.ORANGE);
                } else {
                    gr.setColor(Color.WHITE);
                }
                gr.fillRect(BS * x, BS * y, BS, BS);
            }
        }

        gr.setColor(Color.GRAY);
        for(int x = 0; x < W_COUNT; x++) {
            gr.drawLine(BS * x, 0, BS * x, 1000);
        }
        for(int y = 0; y < H_COUNT; y++) {
            gr.drawLine(0, BS * y, 1000, BS * y);
        }

        repaint();
    }
}
