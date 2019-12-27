// package training_programs;

// Класс для запуска приложения игра угадай число
// Три объекта класса имитируют игроков, которые угадывают число
public class GuessLauncher {
  public static void main(String[] args) {
    GuessGame gG = new GuessGame();
    gG.startGame();
  }
}

// Класс игры
public class GuessGame {
  // Три виртуальных игрока
  Player p1, p2, p3;

  // Основной рабочий метод
  public void startGame() {
    // Создание игроков
    p1 = new Player();
    p2 = new Player();
    p3 = new Player();

    // Генерирование случайного числа
    int targetNumber = (int) (Math.random() * 10);

    // В цикле попытка игроками угадать число
    while(true) {
      System.out.println("Нужно угадать число: " + targetNumber);

      // Каждый из игроков пытается угадать число
      p1.guess();
      p2.guess();
      p3.guess();

      // Вывод на экран результатов
      System.out.println("Первый игрок думает, что это число: " + p1.guess());
      System.out.println("Второй игрок думает, что это число: " + p2.guess());
      System.out.println("Третий игрок думает, что это число: " + p3.guess());

      // В зависимости от того, какой из игроков выиграл, объявляется победитель
      // и цикл прерывает свою работу. Либо игра продолжается.
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

  // Класс игрока
  public class Player {
    // Переменная угадываемого числа
    private int number = 0;

    // Метод угадывания
    public int guess() {
      number = (int) (Math.random() * 10);
      return number;
    }

    // Метод получения угаданной переменной извне класса
    public int getNumber() {
      return number;
    }
  }
}
