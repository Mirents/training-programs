import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class testJTextField {
  JFrame frame = new JFrame("Test program");
  JLabel label = new JLabel("text");
  JTextField textField = new JTextField(10);
  JButton button = new JButton("Clear");

  public static void main(String [] args) {
    testJTextField program = new testJTextField();
    program.work();
  }

  public void work() {
    JPanel panel = new JPanel();

    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.add(label);
    panel.add(textField);
    panel.add(button);
    textField.addActionListener(new textFieldActionListener());
    textField.addKeyListener(new textFieldKeyListener());
    button.addActionListener(new buttonActionListener());

    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(300, 100);
    frame.setVisible(true);
  }

  class textFieldActionListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      label.setText("textField");
    }
  }

  class textFieldKeyListener extends KeyAdapter {
    public void keyReleased(KeyEvent event) {
      //if(event.getKeyCode() == KeyEvent.VK_ESCAPE)
        label.setText(event.getKeyCode() + "");
    }
  }

  class buttonActionListener implements ActionListener {
    public void actionPerformed(ActionEvent event) {
      textField.setText("");
    }
  }
}
