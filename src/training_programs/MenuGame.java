import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGame {
  JFrame frame;
  GameWindow paint = new GameWindow();
  int x = 10;
  int y = 10;
  Rectangle dim = new Rectangle(100, 100, 400, 300);

  public static void main(String[] args) {
    new MenuGame().go();
  }

  public void go() {
    frame = new JFrame("X - O");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(BorderLayout.CENTER, paint);
    paint.addMouseListener(paint);
    frame.setBounds(dim);
    frame.setVisible(true);
  }

  class GameWindow extends JPanel implements MouseListener{

    public void paintComponent(Graphics g) {
      paintBattlePole(g);
      paintHods(g);
    }

    public void paintBattlePole(Graphics g) {
      g.setColor(new Color(150, 160, 130));
      g.fillRect(0, 0, this.getWidth(), this.getHeight());
    }

    public void paintHods(Graphics g) {
      g.setColor(new Color(50, 40, 20));
      g.fillOval((x-25), (y-25), 50, 50);
    }

    public void mousePressed(MouseEvent e) {
      Point p = e.getPoint();
      x = (int) p.getX();
      y = (int) p.getY();
      repaint();
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }
  }
}
