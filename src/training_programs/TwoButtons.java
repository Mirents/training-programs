import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

// Второй способ реализовать несколько интерфейсов для нескольки объектов.
// При этом не возникает проблемы с доступом к элементам внешнего класса.
public class TwoButtons {
    private JFrame frame;
    private JLabel label;
    private int xpos, ypos, nx, ny;

    public static void main(String [] args) {
      TwoButtons tb = new TwoButtons();
      tb.go();
    }

    public void go() {
      frame = new JFrame("TwoButtons");
      JButton buttonLeft = new JButton("Left");
      buttonLeft.addActionListener(new LeftListener());
      JButton buttonRight = new JButton("Right");
      buttonRight.addActionListener(new RightListener());
      MyFlyingOval paint = new MyFlyingOval();

      frame.setSize(300, 300);
      //frame.getContentPane().add(BorderLayout.SOUTH, buttonText);
      frame.getContentPane().add(BorderLayout.CENTER, paint);
      frame.getContentPane().add(BorderLayout.SOUTH, buttonLeft);
      frame.getContentPane().add(BorderLayout.NORTH, buttonRight);
      frame.setVisible(true);

      nx = 1;
      ny = 1;
      xpos = 50;
      ypos = 50;
      for(int i=0; i < 1000; i ++) {
        if(xpos-1 > 0 && xpos+1 < 300)
          xpos+=nx;
        if(xpos == 0 && xpos == 300) {
            nx *= -1;
            xpos+=nx;
        }
        paint.repaint();
        try {
          Thread.sleep(50);
        } catch(Exception ex) {}
      }
    }

    class LeftListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        nx = -1;
      }
    }

    class RightListener implements ActionListener {
      public void actionPerformed(ActionEvent event) {
        nx = 1;
      }
    }

    class MyFlyingOval extends JPanel {
        public void paintComponent(Graphics g) {
          g.setColor(Color.white);
          g.fillRect(0, 0, this.getWidth(), this.getHeight());
          g.setColor(Color.red);
          g.fillOval(xpos, ypos, 50, 50);
        }
    }
}
