package ru.itis;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import java.awt.GridLayout;
import javax.swing.JToggleButton;

public class MyFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField numberField;
	
	private JButton[] numberButtons = new JButton[10];
	private JPanel operationPanel;
	private JButton clearBtn;
	private JButton resultBtn;
	private JButton plusButton;
	private JButton divisionBtn;
	
	private double memory;
	private Operation operation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MyFrame frame = new MyFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MyFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 649, 453);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		//setContentPane(contentPane);
		setContentPane(new GraphicsPanel());
		contentPane.setLayout(new BorderLayout(0, 0));
		
		numberField = new JTextField();
		numberField.setEditable(false);
		contentPane.add(numberField, BorderLayout.NORTH);
		numberField.setColumns(10);
		
		JPanel keyPanel = new JPanel();
		contentPane.add(keyPanel, BorderLayout.CENTER);
		keyPanel.setLayout(new GridLayout(4, 3, 0, 0));
		
		operationPanel = new JPanel();
		contentPane.add(operationPanel, BorderLayout.EAST);
		operationPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		clearBtn = new JButton("C");
		clearBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearButtonAction();
			}
		});
		operationPanel.add(clearBtn);
		
		resultBtn = new JButton("=");
		resultBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				resultButtonAction();
			}
		});
		
		operationPanel.add(resultBtn);
		
		plusButton = new JButton("+");
		plusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operationButtonAction(Operation.PLUS);
			}
		});
		operationPanel.add(plusButton);
		
		divisionBtn = new JButton("/");
		divisionBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				operationButtonAction(Operation.DIVIDE);
			}
		});
		operationPanel.add(divisionBtn);
		
		for(int i = 0; i < 10; i++) {
			final int a = i;
			numberButtons[i] = new JButton(""+i);
			numberButtons[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					addNumberToScreen(a);
				}
			});
			keyPanel.add(numberButtons[i]);
		}
	}
	
	private void addNumberToScreen(int n) {
		numberField.setText(numberField.getText() + n);
	}
	
	private void resultButtonAction() {
		if(Operation.PLUS.equals(operation)) {
			memory += getNumberFromScreen();
		} else if(Operation.DIVIDE.equals(operation)) {
			memory /= getNumberFromScreen();
		}
		
		numberField.setText(memory + "");
		memory = 0;
	}
	
	private double getNumberFromScreen() {
		try {
			return Double.parseDouble(numberField.getText());
		} catch (Exception e) {
			return 0;
		}
	}
	
	private void operationButtonAction(Operation op) {
		operation = op;
		memory = getNumberFromScreen();
		numberField.setText("0");
	}
	
	private void clearButtonAction() {
		memory = 0;
		operation = Operation.NONE;
		numberField.setText("0");
	}

}
