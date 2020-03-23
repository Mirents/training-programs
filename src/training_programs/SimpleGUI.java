import javax.swing.*;
import java.awt.event.*;

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
    frame.getContentPane().add(button);
    frame.setSize(200, 200);

    frame.setVisible(true);
  }

  public void actionPerformed(ActionEvent event) {
    button.setText("Check!!!");
  }
}
