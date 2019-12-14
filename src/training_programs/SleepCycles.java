package training_programs;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
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
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;

public class SleepCycles {
	
	private static int slowSleep = 20; // Фаза медленнго сна
	private static int fastSleep = 70; // Фаза быстрого сна
	private static int cycleTime = 6; // Максимальное количество циклов
	private static int timeToFallSleep = 0; // Время для засыпания
	private static int hourUp = 7;
	private static int minuteUp = 0;
	private static int minuteSleep = 30;
	private static int hourSleep = 1;
	
	public static void main(String[] args) {

		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(275, 364);
		
		JPanel jPanel = new JPanel();
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		jPanel.setLayout(null);
		jFrame.getContentPane().add(jPanel);
		
		JButton jbutton = new JButton("Рассчитать");
		jbutton.setBounds(5, 90, 189, 30);
		jPanel.add(jbutton);
		
		JTextField textBoxFastSleep = new JTextField(10);
		textBoxFastSleep.setSize(40, 25);
		textBoxFastSleep.setLocation(223, 5);
		textBoxFastSleep.setText("70");
		jPanel.add(textBoxFastSleep);
		JTextField textBoxSlowSleep = new JTextField(10);
		textBoxSlowSleep.setSize(40, 25);
		textBoxSlowSleep.setLocation(223, 32);
		textBoxSlowSleep.setText("20");
		jPanel.add(textBoxSlowSleep);
		JTextField textBoxTimeToFallSleep = new JTextField(10);
		textBoxTimeToFallSleep.setSize(40, 25);
		textBoxTimeToFallSleep.setLocation(223, 60);
		textBoxTimeToFallSleep.setText("15");
		jPanel.add(textBoxTimeToFallSleep);
		
		JTextPane textPane = new JTextPane();
		JScrollPane textScroll = new JScrollPane(textPane);
		textScroll.setBounds(5, 125, 189, 80);
		jPanel.add(textScroll);
		
		JLabel lblNewLabel = new JLabel("Цикл сна:");
		lblNewLabel.setBounds(5, 5, 135, 15);
		jPanel.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("Время засыпания:");
		label_1.setBounds(5, 37, 135, 15);
		jPanel.add(label_1);
		
		JSpinner spinnerHourCicle = new JSpinner();
		spinnerHourCicle.setBounds(96, 8, 36, 20);
		spinnerHourCicle.setValue(hourSleep);
		jPanel.add(spinnerHourCicle);
		
		JSpinner spinnerMunuteCicle = new JSpinner();
		spinnerMunuteCicle.setBounds(165, 8, 36, 20);
		spinnerMunuteCicle.setValue(minuteSleep);
		jPanel.add(spinnerMunuteCicle);
		
		JSpinner spinnerTimeToFallSleep = new JSpinner();
		spinnerTimeToFallSleep.setBounds(144, 32, 36, 20);
		spinnerTimeToFallSleep.setValue(timeToFallSleep);
		jPanel.add(spinnerTimeToFallSleep);
		
		JLabel label = new JLabel("Проснуться в:");
		label.setBounds(15, 65, 135, 15);
		jPanel.add(label);
		
		JSpinner spinnerHourUp = new JSpinner();
		spinnerHourUp.setBounds(119, 60, 36, 20);
		spinnerHourUp.setValue(hourUp);
		jPanel.add(spinnerHourUp);
		
		JSpinner spinnerMinuteUp = new JSpinner();
		spinnerMinuteUp.setValue(minuteUp);
		spinnerMinuteUp.setBounds(188, 60, 36, 20);
		jPanel.add(spinnerMinuteUp);
		
		jFrame.setVisible(true);
		
		jbutton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					LocalTime time = LocalTime.of(Integer.parseInt(spinnerHourUp.getValue().toString()),
							Integer.parseInt(spinnerMinuteUp.getValue().toString()));
					String s = "";
					hourSleep = Integer.parseInt(spinnerHourCicle.getValue().toString());
					minuteSleep = Integer.parseInt(spinnerMunuteCicle.getValue().toString());
					timeToFallSleep = Integer.parseInt(spinnerTimeToFallSleep.getValue().toString());
					
					for(int i = cycleTime; i >= 1; i--) {
						LocalTime time1 = time.minusMinutes(i*(hourSleep*60+minuteSleep) + timeToFallSleep);
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
