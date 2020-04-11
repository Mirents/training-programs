import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;

public class SimpleChatServer {
  ArrayList<PrintWriter> clientOutputStreams;
  JTextArea workLog;
  DateFormat dateFormat;

  public void createWindow() {
    JFrame frame = new JFrame("Simple chat server");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    workLog = new JTextArea(15, 50);
    workLog.setLineWrap(true);
    workLog.setWrapStyleWord(true);
    workLog.setEditable(false);
    JScrollPane sqroller = new JScrollPane(workLog);
    sqroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    sqroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    panel.add(BorderLayout.NORTH, new JLabel("History:"));
    panel.add(BorderLayout.CENTER, sqroller);

    frame.getContentPane().add(BorderLayout.CENTER, panel);
    frame.setSize(700, 300);
    frame.setVisible(true);
    dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
    setTextWorkLog("Create server");
  }

  public void setTextWorkLog(String text) {
    workLog.append(dateFormat.format(new Date()) + " " + text + "\n");
  }

  public class ClientHandler implements Runnable {
    BufferedReader reader;
    Socket sock;

    public ClientHandler(Socket clientSocket) {
      try {
      sock = clientSocket;
      InputStreamReader inReader = new InputStreamReader(sock.getInputStream());
      reader = new BufferedReader(inReader);
    } catch(Exception e) { e.printStackTrace(); }
  }

    public void run() {
      String message;
      try {
        while((message = reader.readLine()) != null) {
          setTextWorkLog("Send message: " + message + " to " + sock.toString());
          tellEveryone(message);
        }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public static void main(String [] args) {
    new SimpleChatServer().go();
  }

  public void go() {
    createWindow();
    clientOutputStreams = new ArrayList<PrintWriter>();
    try {
      ServerSocket serverSock = new ServerSocket(5000);
      setTextWorkLog("Start server " + serverSock.toString());

      while(true) {
        Socket clientSocket = serverSock.accept();
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        clientOutputStreams.add(writer);

        Thread t = new Thread(new ClientHandler(clientSocket));
        t.start();
        setTextWorkLog("Add client: " + clientSocket.toString());
      }
    } catch(Exception e) { e.printStackTrace(); }
  }

  public void tellEveryone(String message) {
    Iterator it = clientOutputStreams.iterator();
    while(it.hasNext()) {
      try {
        PrintWriter writer = (PrintWriter) it.next();
        writer.println(message);
        writer.flush();
      } catch(Exception e) { e.printStackTrace(); }
    }
  }
}
