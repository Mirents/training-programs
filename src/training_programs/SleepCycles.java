package training_programs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class SleepCycles {
	
	public static void main(String[] args) {

		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(300, 300);
		jFrame.setVisible(true);
		
		JPanel jPanel = new JPanel();
		jFrame.getContentPane().add(jPanel);
		
		// Менеджер FlowLayout
		/*jPanel.setLayout(new FlowLayout());
		jPanel.add(new JButton("Button 1"));
		JTextField textBox = new JTextField(10);
		jPanel.add(textBox);
		JTextPane textPane = new JTextPane();
		JScrollPane textScroll = new JScrollPane(textPane);
		textScroll.setBounds(5, 30, 500, 150);
		textPane.setBounds(5, 30, 500, 150);
		jPanel.add(textPane);*/
		
		// Менеджер BorderLayout
		/*jPanel.setLayout(new BorderLayout());
		jPanel.add(new JButton("Button 1"), BorderLayout.NORTH);*/
		
		// Менеджер GridLayout
		/*jPanel.setLayout(new GridLayout(3,3));
		jPanel.add(new JButton("Button 1"));
		jPanel.add(new JButton("Button 2"));
		jPanel.add(new JButton("Button 3"));
		jPanel.add(new JButton("Button 4"));*/
		
		jPanel.setLayout(new GridLayout(3,1));
		jPanel.add(new JButton("Button 1"));
		JTextField textBox = new JTextField(10);
		jPanel.add(textBox);
		JTextPane textPane = new JTextPane();
		JScrollPane textScroll = new JScrollPane(textPane);
		textScroll.setBounds(5, 30, 500, 150);
		jPanel.add(textScroll);
	}
}
