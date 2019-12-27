import java.util.ArrayList;
import java.io.*;
/*Простая игра наподобие морского боя, где случайным образом на поле размером
8х8 располагаются корабли, а пользователь угадывает их расположение*/

// Основной класс, контролирующий работу программы
public class NavalBattle {
  private GameHelper gameHelper = new GameHelper(); // Вспомогательный класс для работы игры
  private ArrayList<Ship> shipList = new ArrayList<Ship>(); // Список кораблей
  private ArrayList<String> guessList = new ArrayList<String>(); // Список совершенных выстрелов
  private ArrayList<String> createShipList = new ArrayList<String>(); //Список созданных кораблей для проверки пересечения при создании
  private String [] literPos = {"a", "b", "c", "d", "e", "f", "g", "h"}; // Буквы координат кораблей
  int numOfGuesses = 0; // Количество побежденных кораблей

  // Начальная установка игры
  private void setUpGame() {
    // Создание трех кораблей
    for(int i=0; i<3; i++) {
      shipList.add(new Ship(setPositionShip()));
    }

    // Информация начального экрана
    System.out.println("Цель - потопить три корабля");
    System.out.println("Постарайтель сделать это за минимальное количество ходов");
  }

  // Основной игровой цикл
  public void startPlaying() {
    // Перемееная игры
    boolean game = true;
    // Выполнять в цикле пока переменная активна
    while(game) {
      // Показать поле битвы
      showBattlefield();
      // Считать пользовательский ввод
      String userGuess = gameHelper.getUserInput("Сделайте ход: ");

      // Дополнительный автоматический игрок
      /*String userGuess = null;
      boolean isEx = true;
      while(isEx) {
        int startX = (int) (Math.random() * 8);
        int startY = (int) (Math.random() * 9);
        if(startX > 7) startX = 7;
        if(startY > 8) startY = 8;
        if(startY < 1) startY = 1;
        userGuess = literPos[startX] + Integer.toString(startY);
        for(String shipTest : createShipList) {
          if(shipTest.indexOf(userGuess) >= 0) {
            isEx = false;
            break;
          }
        }
      }*/

      // Проверить попадание, засчитать очки
      checkUserGuess(userGuess);
      // Если остался хоть один корабль - продолжить игру
      for(Ship shipTest : shipList) {
        if(shipTest.isLive()) {
          game = true;
          break;
        }
        // В противном случае прекратить игру
        else game = false;
      }
    }
  }

// Метод случайного расположения кораблей
  private ArrayList<String> setPositionShip() {
    // Создаем временный корабль
    ArrayList<String> loc = new ArrayList<String>(); // Начальная позиция корабля
    boolean enjoy = true;

    // Цикл while пока не создан корабль
    while(true) {
      // Загадать случайную позицию для корабля
      int startX = (int) (Math.random() * 8);
      int startY = (int) (Math.random() * 9);
      if(startX > 7) startX = 7;
      if(startY > 8) startY = 8;
      if(startY < 1) startY = 1;
      loc.add(literPos[startX] + Integer.toString(startY));
      // Если случайная позиция не пересекается со Списком всех кораблей для проверки пересечения
      // И список всех кораблей не пуст
      if(createShipList.size() > 0) {
        for(String shipTest : createShipList) {
          if(loc.indexOf(shipTest) >= 0) {
            enjoy = false;
            break;
          }
        }
      }
      // Если случайная точка успешно создана
      if(enjoy) {
        // Создать случайную длинну для этого корабля от 1 до 5
        int len = (int) (Math.random() * 5);
        if(len == 0) len = 1;
        ArrayList<String> locP = new ArrayList<String>(); // Тело корабля
        int snapr = (int) (Math.random() * 5); // Переменая свободных направлений
        if(snapr == 0) snapr = 1;

        // В зависимостри от направления и указанной длинны
        // проверить пересечение с границами  поля и другими кораблями
        if(snapr == 1) {
          if((startX+len) <= 7) {
            for(int i=startX+1; i<=startX+len; i++) {
              locP.add(literPos[i] + Integer.toString(startY));
            }
            for(String shipTest : createShipList) {
              if(locP.indexOf(shipTest) >= 0) {
                enjoy = false;
                break;
              }
            }
          }
        }

        if(snapr == 2) {
          if((startX-len) >= 0) {
            for(int i=startX-1; i>=startX-len; i--) {
              locP.add(literPos[i] + Integer.toString(startY));
            }
            for(String shipTest : createShipList) {
              if(locP.indexOf(shipTest) >= 0) {
                enjoy = false;
                break;
              }
            }
          }
        }

        if(snapr == 3) {
          if((startY+len) <= 8) {
            for(int i=startY+1; i<=startY+len; i++) {
              locP.add(literPos[startX] + Integer.toString(i));
            }
            for(String shipTest : createShipList) {
              if(locP.indexOf(shipTest) >= 0) {
                enjoy = false;
                break;
              }
            }
          }
        }

        if(snapr == 4) {
          if((startY-len) >= 1) {
            for(int i=startY-1; i>=startY-len; i--) {
              locP.add(literPos[startX] + Integer.toString(i));
            }
            for(String shipTest : createShipList) {
              if(locP.indexOf(shipTest) >= 0) {
                enjoy = false;
                break;
              }
            }
          }
        }
        // Добавить корабль в список созданных и вернуть вызывающему методу
        for(String shipTest : locP) {
          loc.add(shipTest);
          createShipList.add(shipTest);
        }
        break;
      }
    }
    return loc;
  }

  // Отобразить поле битвы
  public void showBattlefield() {
    // Вывести верхние цифровые координаты
    System.out.print("   ");
    for(int i=1; i<9; i++) {
      System.out.print(" " + i + "  ");
    }

    // Вывести боковые буквенные координаты и само поле битвы построчно
    for(int x=0; x<8; x++) {
      System.out.print("\n" + literPos[x] + " |");
      for(int y=1; y<9; y++) {
        // Если в ячейку произведен выстрел - отметить ее
        if(guessList.indexOf(literPos[x] + Integer.toString(y)) >= 0) {
          System.out.print("[");
          // Если в ячейке есть пораженная часть корабля - отметить ее
          if(isDeadField(literPos[x] + Integer.toString(y)))
            System.out.print("X]|");
          else System.out.print("-]|");
        } else System.out.print(" - |");
        // Дополнительно - показать все корабли на поле
        /*if(isLiveField(literPos[x] + Integer.toString(y))) {
            System.out.print("[X]|");
        } else System.out.print(" - |");*/
      }
    }
    System.out.print("\n");
  }

  // Проверка корабля на пораженные ячейки
  public boolean isDeadField(String pos) {
    for(Ship shipTest : shipList) {
      if(shipTest.getDead(pos))
      return true;
    }
    return false;
  }

  public boolean isLiveField(String pos) {
    for(Ship shipTest : shipList) {
      if(shipTest.getLive(pos))
      return true;
    }
    return false;
  }

  // Основной метод проверки попадания в ячейку
  public String checkUserGuess(String userGuess) {
    // Увеличить счетчик выстрелов
    numOfGuesses++;
    // Задать начальное значение возвращаемого значения по умолчанию
    String result = "Мимо";
    // Добавить этот выстрел в список выстрелов для отображения на поле битвы
    guessList.add(userGuess);

    // Пройти по всем кораблям и проверить попадание по указанным координатам
    for(Ship shipTest : shipList) {
      result = shipTest.checkIsAttack(userGuess);
      // В случае попадания заканчиваем цикл
      if(result.equals("Попал")) break;
      if(result.equals("Потопил")) break;
    }
    return result;
  }

  // Финальный метод с указанием счета
  public void finishGame() {
    showBattlefield();
    System.out.println("Все корабли потоплены");
    System.out.println("Вы сделали это за " + numOfGuesses + " хода(ов)");
  }

  // Основной рабочий метод
  public static void main(String [] args) {
    NavalBattle game = new NavalBattle();
    game.setUpGame();
    game.startPlaying();
    game.finishGame();
  }
}

// Класс лодки
public class Ship {
  // Координаты лодки
  private ArrayList<String> locationLivePartsOfShip;
  // Координаты побежденных ячеек лодки для отображения на общем игровом поле
  private ArrayList<String> locationDeadPartsOfShip = new ArrayList<String>();

  public Ship(ArrayList<String> loc) {
    this.locationLivePartsOfShip = loc;
  }

  // Проверка побежденной ячейки корабля для вывода на игровом поле
  public boolean getDead(String pos) {
    if(locationDeadPartsOfShip.contains(pos)) return true;
    return false;
  }

  public boolean getLive(String pos) {
    if(locationLivePartsOfShip.contains(pos)) return true;
    return false;
  }

  // Проверка живучести корабля
  public boolean isLive() {
    if(locationLivePartsOfShip.isEmpty()) return false;
    else return true;
  }

  public void show() {
    System.out.println(locationLivePartsOfShip);
  }

  // Проверка попадания по указанным координатам
  public String checkIsAttack(String userGuess) {
    // Установка значения по умолчанию
    String result = "Мимо";
    // Получение индекса выстрела в списке ячеек корабля
    int index = locationLivePartsOfShip.indexOf(userGuess);

    // В случае попадания удалить ячейку из списка живых
    // и добавить ее в список мертвых ячеек
    if(index >= 0) {
      locationDeadPartsOfShip.add(userGuess);
      locationLivePartsOfShip.remove(index);

      // В случае потопления корабля указать это
      if(locationLivePartsOfShip.isEmpty()) {
        System.out.println("Корабль потоплен");
        result = "Потопил";
      } else result = "Попал";
    }

    return  result;
  }
}

// Вспомогательный класс для работы элементов игры
public class GameHelper {
  // Метод для ввода координат с клавиатуры
  public String getUserInput(String promt) {
    String inputLine = null;
    System.out.print(promt + " ");
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      inputLine = reader.readLine();
      if (inputLine.length() == 0) return null;
    } catch (IOException e) {
      System.out.println("IOException: " + e);
    }
    return inputLine;
  }
}
