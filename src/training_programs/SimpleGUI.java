import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// Т.к. класс SimpleGUI использует интерфейс ActionListener, он может
// в одном методе отслеживать отдельные нажатия на разные кнопки.
// Но с точки зрения ООП это не совсем корректно и желательно
// пользоваться другим методом, описаным в программе TwoButtons.
public class SimpleGUI implements ActionListener {
    JButton buttonColor = new JButton("New color");
    JButton buttonText = new JButton("New button label");
    JFrame frame = new JFrame("SimpleGUI");

  public static void main(String[] args) {
      SimpleGUI sgui = new SimpleGUI();
      sgui.go();
  }

  public void go() {
    buttonColor.addActionListener(this);
    buttonText.addActionListener(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(BorderLayout.SOUTH, buttonColor);
    frame.getContentPane().add(BorderLayout.NORTH, buttonText);
    frame.setSize(300, 300);
    frame.getContentPane().add(BorderLayout.CENTER, new MyDrawPanel());

    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    if(event.getSource() == buttonColor)
      frame.repaint();
    else
      buttonText.setText("New text");
  }
}
