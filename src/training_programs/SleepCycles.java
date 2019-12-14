package training_programs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SleepCycles {
	
	private static int slowSleep = 20; // Фаза медленнго сна
	private static int fastSleep = 70; // Фаза быстрого сна
	private static int cycleTime = 6; // Максимальное количество циклов
	private static int timeToFallSleep = 0; // Время для засыпания
	
	public static void main(String[] args) {

		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(199, 243);
		
		JPanel jPanel = new JPanel();
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		jPanel.setLayout(null);
		jFrame.getContentPane().add(jPanel);
		
		JButton jbutton = new JButton("Рассчитать");
		jbutton.setBounds(5, 90, 189, 30);
		jPanel.add(jbutton);
		
		JTextField textBoxFastSleep = new JTextField(10);
		textBoxFastSleep.setSize(40, 25);
		textBoxFastSleep.setLocation(150, 5);
		jPanel.add(textBoxFastSleep);
		JTextField textBoxSlowSleep = new JTextField(10);
		textBoxSlowSleep.setSize(40, 25);
		textBoxSlowSleep.setLocation(150, 32);
		jPanel.add(textBoxSlowSleep);
		JTextField textBoxTimeToFallSleep = new JTextField(10);
		textBoxTimeToFallSleep.setSize(40, 25);
		textBoxTimeToFallSleep.setLocation(150, 60);
		jPanel.add(textBoxTimeToFallSleep);
		
		JTextPane textPane = new JTextPane();
		JScrollPane textScroll = new JScrollPane(textPane);
		textScroll.setBounds(5, 125, 189, 80);
		jPanel.add(textScroll);
		
		JLabel lblNewLabel = new JLabel("Быстрый сон:");
		lblNewLabel.setBounds(5, 5, 135, 15);
		jPanel.add(lblNewLabel);
		
		JLabel label = new JLabel("Медленный сон");
		label.setBounds(5, 32, 135, 15);
		jPanel.add(label);
		
		JLabel label_1 = new JLabel("Время засыпания:");
		label_1.setBounds(5, 60, 135, 15);
		jPanel.add(label_1);
		
		LocalTime time = LocalTime.of(7, 5);
		
		jFrame.setVisible(true);
		
		jbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					String s = "";
					fastSleep = Integer.parseInt(textBoxFastSleep.getText());
					slowSleep = Integer.parseInt(textBoxSlowSleep.getText());
					timeToFallSleep = Integer.parseInt(textBoxTimeToFallSleep.getText());
					for(int i = cycleTime; i >= 1; i--) {
						LocalTime time1 = time.minusMinutes(i*(slowSleep+fastSleep) + timeToFallSleep);
						s += time1.toString() + "\n";
					}
					textPane.setText(s);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Ошибка - введите время!!!");
				}
			}
		});
		
	}
}
