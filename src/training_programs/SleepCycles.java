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

	private static int cycleTime = 6; // Максимальное количество циклов
	private static int timeToFallSleep = 0; // Время для засыпания
	private static int hourUp = 7;
	private static int minuteUp = 0;
	private static int minuteSleep = 30;
	private static int hourSleep = 1;
	
	public static void main(String[] args) {

		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(225, 261);
		
		JPanel jPanel = new JPanel();
		jPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		jPanel.setLayout(null);
		jFrame.getContentPane().add(jPanel);
		
		JButton jbutton = new JButton("Рассчитать");
		jbutton.setBounds(5, 90, 215, 30);
		jPanel.add(jbutton);
		
		JTextPane textPane = new JTextPane();
		JScrollPane textScroll = new JScrollPane(textPane);
		textScroll.setBounds(5, 125, 215, 100);
		jPanel.add(textScroll);
		
		JLabel lblNewLabel = new JLabel("Цикл сна:");
		lblNewLabel.setBounds(5, 5, 135, 15);
		jPanel.add(lblNewLabel);
		
		JLabel label_1 = new JLabel("Время засыпания:");
		label_1.setBounds(5, 35, 135, 15);
		jPanel.add(label_1);
		
		JSpinner spinnerHourCicle = new JSpinner();
		spinnerHourCicle.setModel(new SpinnerNumberModel(1, 1, 5, 1));
		spinnerHourCicle.setBounds(145, 8, 36, 20);
		jPanel.add(spinnerHourCicle);
		
		JSpinner spinnerMunuteCicle = new JSpinner();
		spinnerMunuteCicle.setModel(new SpinnerNumberModel(30, 1, 59, 1));
		spinnerMunuteCicle.setBounds(185, 8, 36, 20);
		jPanel.add(spinnerMunuteCicle);
		
		JSpinner spinnerTimeToFallSleep = new JSpinner();
		spinnerTimeToFallSleep.setModel(new SpinnerNumberModel(0, 0, 60, 1));
		spinnerTimeToFallSleep.setBounds(145, 35, 36, 20);
		jPanel.add(spinnerTimeToFallSleep);
		
		JLabel label = new JLabel("Проснуться в:");
		label.setBounds(5, 60, 135, 15);
		jPanel.add(label);
		
		JSpinner spinnerHourUp = new JSpinner();
		spinnerHourUp.setModel(new SpinnerNumberModel(7, 0, 24, 1));
		spinnerHourUp.setBounds(145, 60, 36, 20);
		spinnerHourUp.setValue(hourUp);
		jPanel.add(spinnerHourUp);
		
		JSpinner spinnerMinuteUp = new JSpinner();
		spinnerMinuteUp.setModel(new SpinnerNumberModel(0, 0, 59, 1));
		spinnerMinuteUp.setValue(minuteUp);
		spinnerMinuteUp.setBounds(185, 60, 36, 20);
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
						if (i == 1) s += time1.toString();
						else s += time1.toString() + "\n";
					}
					textPane.setText(s);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Ошибка - введите время!!!");
				}
			}
		});
		
	}
}
