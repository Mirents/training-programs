import java.awt.*;
import javax.swing.*;

class MyDrawPanel extends JPanel {
  public void paintComponent(Graphics g) {
  /*  g.setColor(Color.red);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());
    g.setColor(Color.blue);
    g.fillOval(70, 70, 100, 100);*/

    Graphics2D g2d = (Graphics2D) g;

    int red = (int) (Math.random() * 255);
    int green = (int) (Math.random() * 255);
    int blue = (int) (Math.random() * 255);
    Color on = new Color(red, green, blue);

    red = (int) (Math.random() * 255);
    green = (int) (Math.random() * 255);
    blue = (int) (Math.random() * 255);
    Color tw = new Color(red, green, blue);

    GradientPaint grad = new GradientPaint(70, 70, on, 150, 150, tw);

    g2d.setPaint(grad);
    g2d.fillOval(70, 70, 100, 100);
  }
}
