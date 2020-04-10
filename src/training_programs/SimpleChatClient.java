import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SimpleChatClient {
  JTextArea incoming;
  JTextField outgoing;
  BufferedReader reader;
  PrintWriter writer;
  Socket sock;

  public static void main(String [] args) {
    SimpleChatClient c = new SimpleChatClient();
    c.go();
  }

  public void go() {
    JFrame frame = new JFrame("Simple chat client");
    JPanel panel = new JPanel(new BorderLayout());
    incoming = new JTextArea(15, 50);
    incoming.setLineWrap(true);
    incoming.setWrapStyleWord(true);
    incoming.setEditable(false);
    JScrollPane sqroller = new JScrollPane(incoming);
    sqroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    sqroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    outgoing = new JTextField(20);
    JButton sendButton = new JButton("Send");
    sendButton.addActionListener(new SendButtonListener());
    Box box1 = new Box(BoxLayout.Y_AXIS);
    box1.add(sqroller);
    box1.add(outgoing);
    box1.add(sendButton);
    panel.add(BorderLayout.CENTER, box1);
    setupNetworking();

    Thread readerThread = new Thread(new IncomingReader());
    readerThread.start();

    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setSize(400, 300);
    frame.setVisible(true);
  }

  private void setupNetworking() {
    try {
      sock = new Socket("127.0.0.1", 5000);
      InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
      reader = new BufferedReader(streamReader);
      writer = new PrintWriter(sock.getOutputStream());
      System.out.println("Start client");
    } catch(IOException e) { e.printStackTrace(); }
  }

  public class SendButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      try {
        writer.println(outgoing.getText());
        writer.flush();
        System.out.println("Send message: " + outgoing.getText());
      } catch(Exception e) { e.printStackTrace(); }
      outgoing.setText("");
      outgoing.requestFocus();
    }
  }

  public class IncomingReader implements Runnable {
    public void run() {
      String message;
      try {
        while((message = reader.readLine()) != null) {
          System.out.println("Read message: " + message);
          incoming.append(message + "\n");
        }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }
}
