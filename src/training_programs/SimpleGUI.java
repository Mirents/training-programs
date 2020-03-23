import javax.swing.*;

public class SimpleGUI {
  public static void main(String[] args) {
      JFrame frame = new JFrame("Test");
      JButton button = new JButton("Click");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.getContentPane().add(button);
      frame.setSize(200, 200);

      frame.setVisible(true);
  }
}
