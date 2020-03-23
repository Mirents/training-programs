import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class TwoButtons {
    private JFrame frame;
    private JLabel label;

    public static void main(String [] args) {
      TwoButtons tb = new TwoButtons();
      tb.go();
    }

    public void go() {
      frame = new JFrame("TwoButtons");
      label = new JLabel("text");
      JButton buttonColor = new JButton("New color");
      buttonColor.addActionListener(new ColorListener());
      JButton buttonText = new JButton("New button label");
      buttonText.addActionListener(new TextListener());

      frame.setSize(300, 300);
      frame.getContentPane().add(BorderLayout.SOUTH, buttonText);
      frame.getContentPane().add(BorderLayout.CENTER, new MyDrawPanel());
      frame.getContentPane().add(BorderLayout.EAST, label);
      frame.getContentPane().add(BorderLayout.WEST, buttonColor);

      frame.setVisible(true);
    }

    class ColorListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        frame.repaint();
      }
    }

    class TextListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        label.setText("Test!");
      }
    }
}
