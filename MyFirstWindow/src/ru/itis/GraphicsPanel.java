package ru.itis;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GraphicsPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static int i = 0;

	/**
	 * Create the panel.
	 */
	public GraphicsPanel() {
		Timer timer = new Timer(100, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				i++;
				System.out.println("123");
			}
		});
		timer.start();
	}
	
	@Override
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		gr.setColor(Color.CYAN);
		gr.drawLine(10, 10, 20, 30);
		gr.fillRect(50, 50, 600, 600);
		gr.setColor(Color.ORANGE);
		gr.fillOval(100, 100, 200, 300);
		gr.setFont(new Font("arial", 1, 50));
		gr.drawString("AHAHHAHHAH", 200, 400);
		BufferedImage img;
		try {
			img = ImageIO.read(new File("d://unnamed.jpg"));
			gr.drawImage(img, 70, 70, 120 + i, 120, null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		repaint();
	}

}
