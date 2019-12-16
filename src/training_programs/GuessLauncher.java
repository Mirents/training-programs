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

    while(true) {
      System.out.println("Нужно угадать число: " + targetNumber);

      p1.guess();
      p2.guess();
      p3.guess();

      System.out.println("Первый игрок думает, что это число: " + p1.getNumber());
      System.out.println("Второй игрок думает, что это число: " + p2.getNumber());
      System.out.println("Третий игрок думает, что это число: " + p3.getNumber());

      if(p1.getNumber() == targetNumber) {
        System.out.println("Первый игрок выиграл!");
        break;
      } else if(p2.getNumber() == targetNumber) {
        System.out.println("Второй игрок выиграл!");
        break;
      } else if(p3.getNumber() == targetNumber) {
        System.out.println("Третий игрок выиграл!");
        break;
      } else System.out.println("Никто из игроков не угадал число, переиграть заново!");
    }
  }

  public class Player {
    private int number = 0;

    public int guess() {
      return (int) (Math.random() * 10);
    }

    public int getNumber() {
      return number;
    }
  }
}
