import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// Второй способ реализовать несколько  интерфейс для нескольки объектов.
// При этом не возникает проблемы с доступом к элементам внешнего класса.
public class TwoButtons {
    private JFrame frame;
    private JLabel label;
    private int xpos, ypos;

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
      frame.getContentPane().add(BorderLayout.CENTER, new MyFlyingOval());
      frame.getContentPane().add(BorderLayout.EAST, label);
      frame.getContentPane().add(BorderLayout.WEST, buttonColor);

      frame.setVisible(true);
    }

    class ColorListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        xpos += 10;
        ypos += 10;
        frame.repaint();
      }
    }

    class TextListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        label.setText("Test!");
      }
    }

    class MyFlyingOval extends JPanel {
        public void paintComponent(Graphics g) {
          g.setColor(Color.red);
          g.fillOval(xpos, ypos, 50, 50);
        }
    }
}
