package training_programs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SleepCycles {

	private static final long serialVersionUID = -8312085209948270707L;
	private JPanel contentPane;
	
	public static void main(String[] args) {

		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(400, 100);
		jFrame.setVisible(true);
		
		JPanel jPanel = new JPanel();
		jFrame.add(jPanel);
		
		// Менеджер FlowLayout
		jPanel.setLayout(new FlowLayout());
		jPanel.add(new JButton("Button 1"));
		JTextField textBox = new JTextField(10);
		jPanel.add(textBox);
		
		// Менеджер BorderLayout
		/*jPanel.setLayout(new BorderLayout());
		jPanel.add(new JButton("Button 1"), BorderLayout.NORTH);*/
		
		// Менеджер GridLayout
		/*jPanel.setLayout(new GridLayout(3,3));
		jPanel.add(new JButton("Button 1"));
		jPanel.add(new JButton("Button 2"));
		jPanel.add(new JButton("Button 3"));
		jPanel.add(new JButton("Button 4"));*/
	}
}
