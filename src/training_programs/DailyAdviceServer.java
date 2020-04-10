import java.io.*;
import java.net.*;

public class DailyAdviceServer {
  String [] adviceList = {"Ешьте меньшими порциями", "Два слова: не годится",
  "Будьте честны хотя бы сегодня", "Смените прическу", "Прогуляйтесь", "Не стоит",
  "Лучше полежать", "Завтра получится"};

  public void go() {
    try {
      ServerSocket servSock = new ServerSocket(4242);

      while(true) {
        Socket sock = servSock.accept();
        
        PrintWriter pw = new PrintWriter(sock.getOutputStream());
        String advice = getAdvice();
        pw.println(advice);
        pw.close();
        System.out.println("Send: " + advice);
      }
    } catch(IOException e) { e.printStackTrace(); }
  }

  private String getAdvice() {
    int rand = (int) (Math.random() * adviceList.length);
    return adviceList[rand];
  }

  public static void main(String [] args) {
    DailyAdviceServer server = new DailyAdviceServer();
    server.go();
  }
}
