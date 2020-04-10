import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClientA {
  JTextField outgoing;
  PrintWriter writer;
  Socket sock;

  public void go() {
    JFrame frame = new JFrame("Simple chat client");
    JPanel panel = new JPanel();
    outgoing = new JTextField(20);
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(new SendButtonListener());
    panel.add(outgoing);
    panel.add(sendButton);
    frame.getContentPane().add(BorderLayout.CENTER, panel);
    setupNetworking();
    frame.setSize(400, 500);
    frame.setVisible(true);
  }

  private void setupNetworking() {
    try {
      sock = new Socket("127.0.0.1", 5000);
      writer = new PrintWriter(sock.getOutputStream());
      System.out.println("network work");
    } catch(IOException e) { e.printStackTrace(); }
  }

  public class SendButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent a) {
      try {
        writer.println(outgoing.getText());
        writer.flush();
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public static void main(String [] args) {
    new SimpleChatClientA().go();
  }
}
