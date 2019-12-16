public class GuessLauncher {
  public static void main(String[] args) {
    GuessGame gG = new GuessGame();
    gG.startGame();
  }
}

public class GuessGame {
  private String name = "123456";

  public String getName() {
    return name;
  }

  Player p1, p2, p3;

  public void startGame() {
    p1 = new Player();
    p2 = new Player();
    p3 = new Player();

    int guess_p1 = 0;
    int guess_p2 = 0;
    int guess_p3 = 0;

    int targetNumber = (int) (Math.random() * 10);
    System.out.println("Загадано число от 1 до 9...");
    
  }
}

public class Player {
  private number = 0;

  public int guess() {
    return (int) (Math.random() * 10);
  }
}
