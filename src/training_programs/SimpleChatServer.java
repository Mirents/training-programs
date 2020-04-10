import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
  ArrayList<PrintWriter> clientOutputStreams;

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
          System.out.println("Send message: " + message + " to " + sock.toString());
          tellEveryone(message);
        }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public static void main(String [] args) {
    new SimpleChatServer().go();
  }

  public void go() {
    clientOutputStreams = new ArrayList<PrintWriter>();
    try {
      ServerSocket serverSock = new ServerSocket(5000);
      System.out.println("Start server " + serverSock.toString());

      while(true) {
        Socket clientSocket = serverSock.accept();
        PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
        clientOutputStreams.add(writer);

        Thread t = new Thread(new ClientHandler(clientSocket));
        t.start();
        System.out.println("Add client: " + clientSocket.toString());
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
