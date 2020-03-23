import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class SimpleGUI implements ActionListener {
    JButton button = new JButton("Click");

  public static void main(String[] args) {
      SimpleGUI sgui = new SimpleGUI();
      sgui.go();
  }

  public void go() {
    JFrame frame = new JFrame("Test");
    button.addActionListener(this);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(BorderLayout.SOUTH, button);
    frame.setSize(300, 300);
    frame.getContentPane().add(BorderLayout.CENTER, new MyDrawPanel());

    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    frame.repaint();
  }
}
