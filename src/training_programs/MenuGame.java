import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MenuGame {
  JFrame frame;
  GameWindow paint = new GameWindow(400, 300);
  int x = 10;
  int y = 10;
  Rectangle dim = new Rectangle(100, 100, 400, 300);

  public static void main(String[] args) {
    new MenuGame().go();
  }

  public void go() {
    frame = new JFrame("X - O");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.getContentPane().add(BorderLayout.CENTER, paint);
    paint.addMouseListener(paint);
    frame.setBounds(dim);
    frame.setVisible(true);
  }

  class GameWindow extends JPanel implements MouseListener{
    int widthBox = 300;
    int heightBox = 400;
    Color col = new Color(50, 40, 20);
    int tolLin = 4;

    public GameWindow(int w, int h) {
      /*widthBox = w;
      heightBox = h;*/
    }

    public void paintComponent(Graphics g) {
      paintBattlePole(g);
      paintHods(g);
    }

    public void paintBattlePole(Graphics g) {
      g.setColor(new Color(150, 160, 130));
      g.fillRect(0, 0, this.getWidth(), this.getHeight());

      g.setColor(new Color(10, 30, 20));
      g.fillRect((widthBox-(int)(tolLin/2)), 0, tolLin, this.getHeight());
      g.setColor(new Color(10, 30, 20));
      g.fillRect((widthBox*2-(int)(tolLin/2)), 0, tolLin, this.getHeight());

      g.setColor(new Color(10, 30, 20));
      g.fillRect(0, (heightBox-(int)(tolLin/2)), this.getWidth(), tolLin);
      g.setColor(new Color(10, 30, 20));
      g.fillRect(0, (heightBox*2-(int)(tolLin/2)), this.getWidth(), tolLin);
    }

    public void paintHods(Graphics g) {
      g.setColor(col);
      g.fillOval((x-25), (y-25), 50, 50);
    }

    public void mousePressed(MouseEvent e) {
      Point p = e.getPoint();
      x = (int) p.getX();
      y = (int) p.getY();

      widthBox = (int) (this.getWidth()/3);
      heightBox = (int) (this.getHeight()/3);

      int r = 0;
      int g = 0;
      int b = 0;
      int i = (int)(255/6);
      if(x <= widthBox) {
        r = i;
        g = i;
        b = i;
      }
      else if(x > widthBox && x <= widthBox*2) {
        r = i*2;
        g = i*2;
        b = i*2;
      }
      else if(x > widthBox*2) {
        r = i*3;
        g = i*3;
        b = i*3;
      }

      if(y <= heightBox) {
        r += (int)(i/2);
        g += (int)(i/2);
        b += (int)(i/2);
      }
      else if(y > heightBox && x <= heightBox*2) {
        r += (int)(i/3);
        g += (int)(i/3);
        b += (int)(i/3);
      }
      else if(y > heightBox*2) {
        r += (int)(i/4);
        g += (int)(i/4);
        b += (int)(i/4);
      }

      col = new Color(r, g, b);
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
