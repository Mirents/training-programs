import java.io.*;
import java.awt.*;
import javax.imageio.ImageIO;
import javax.swing.*;


public class FindAndReplaceDoubleImage {
  JFrame frame;

  public static void main(String [] args) {
    new FindAndReplaceDoubleImage().go();
  }

  public void go() {
		frame = new JFrame("X - O");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setBounds(new Rectangle(100, 100, 400, 300));
		frame.setVisible(true);
	}
}
