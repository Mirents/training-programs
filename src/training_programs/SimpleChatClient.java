import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class SimpleChatClient {
  JTextArea incoming;
  JTextField outgoing;
  BufferedReader reader;
  PrintWriter writer;
  Socket sock;
  DateFormat dateFormat;

  public static void main(String [] args) {
    SimpleChatClient c = new SimpleChatClient();
    c.go();
  }

  public void go() {
    JFrame frame = new JFrame("Simple chat client");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

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
    box1.add(new JLabel("History message:"));
    box1.add(sqroller);
    box1.add(Box.createVerticalStrut(5));
    Box box2 = new Box(BoxLayout.X_AXIS);
    box2.add(new JLabel("Message:"));
    box2.add(outgoing);
    box2.add(sendButton);
    panel.add(BorderLayout.CENTER, box1);
    panel.add(BorderLayout.SOUTH, box2);

    dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    setupNetworking();

    Thread readerThread = new Thread(new IncomingReader());
    readerThread.start();

    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setSize(400, 300);
    frame.setVisible(true);

    setTextIncoming("Create window");
  }

  public void setTextIncoming(String text) {
    incoming.append(dateFormat.format(new Date()) + " " + text + "\n");
  }

  private void setupNetworking() {
    try {
      sock = new Socket("127.0.0.1", 5000);
      InputStreamReader streamReader = new InputStreamReader(sock.getInputStream());
      reader = new BufferedReader(streamReader);
      writer = new PrintWriter(sock.getOutputStream());
      setTextIncoming("Start client");
    } catch(IOException e) { e.printStackTrace(); }
  }

  public class SendButtonListener implements ActionListener {
    public void actionPerformed(ActionEvent ev) {
      try {
        writer.println(outgoing.getText());
        writer.flush();
        setTextIncoming("Send message: " + outgoing.getText());
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
          setTextIncoming("Read message: " + message);
          incoming.append(message + "\n");
        }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }
}
