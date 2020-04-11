import java.io.*;
import java.net.*;
import java.util.*;

public class BeatBoxServer {
  ArrayList<ObjectOutputStream> clientOutputStream;

  public static void main(String[] args) {
    new BeatBoxServer().go();
  }

  public class clientHandler implements Runnable {
    ObjectInputStream in;
    Socket clientSocket;

    public clientHandler(Socket socket) {
      try {
        clientSocket = socket;
        in = new ObjectInputStream(clientSocket.getInputStream());
      } catch(Exception e) { e.printStackTrace(); }
    }

    public void run() {
      Object o1 = null;
      Object o2 = null;

      try {
        while((o1 = in.readObject()) != null) {
          o2 = in.readObject();
          System.out.println("Read two objects");
          tellEvetyone(o1, o2);
        }
      } catch(Exception e) { e.printStackTrace(); }
    }
  }

  public void go() {
    clientOutputStream = new ArrayList<ObjectOutputStream>();

    try {
      ServerSocket serverSock = new ServerSocket(4242);

      while(true) {
        Socket clientSocket = serverSock.accept();
        ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
        clientOutputStream.add(out);

        Thread t = new Thread(new clientHandler(clientSocket));
        t.start();

        System.out.println("Got a connection");
      }
    } catch(Exception e) { e.printStackTrace(); }
  }

  public void tellEvetyone(Object o1, Object o2) {
    Iterator it = clientOutputStream.iterator();

    while(it.hasNext()) {
      try {
        ObjectOutputStream out = (ObjectOutputStream) it.next();
        out.writeObject(o1);
        out.writeObject(o2);
      } catch(Exception e) { e.printStackTrace(); }
    }
  }
}
