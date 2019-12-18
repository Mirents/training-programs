import java.util.ArrayList;
import java.io.*;
/*Простая игра наподобие морского боя, где случайным образом на поле размером
8х8 располагаются корабли, а пользователь угадывает их расположение*/

// Основной класс, контролирующий работу программы
public class NavalBattle {
  private GameHelper gameHelper = new GameHelper();
  private ArrayList<Ship> shipList = new ArrayList<Ship>();
  private ArrayList<String> guessList = new ArrayList<String>();
  private String [] literPos = {"a", "b", "c", "d", "e", "f", "g", "h"};
  int numOfGuesses = 0;

  private void setUpGame() {
    // Создание трех кораблей
    /*for(int i=0; i<10; i++) {
      shipList.add(new Ship(setPositionShip()));
    }*/
    ArrayList<String> loc = new ArrayList<String>();
    loc.add("e2");
    loc.add("f2");
    loc.add("g2");
    loc.add("h2");
    shipList.add(new Ship(loc));
    ArrayList<String> loc1 = new ArrayList<String>();
    loc1.add("b1");
    loc1.add("b2");
    loc1.add("b3");
    shipList.add(new Ship(loc1));
    ArrayList<String> loc2 = new ArrayList<String>();
    loc2.add("b5");
    loc2.add("c5");
    loc2.add("d5");
    loc2.add("e5");
    shipList.add(new Ship(loc2));

    // Информация начального экрана
    System.out.println("Цель - потопить три корабля");
    System.out.println("Постарайтель сделать это за минимальное количество ходов");
  }

  public void startPlaying() {
    boolean game = true;
    while(game) {
      showBattlefield();
      String userGuess = gameHelper.getUserInput("Сделайте ход: ");

      /*String userGuess = null;
      int startX = (int) (Math.random() * 8);
      int startY = (int) (Math.random() * 9);
      if(startX > 7) startX = 7;
      if(startY > 8) startY = 8;
      if(startY < 1) startY = 1;
      userGuess = literPos[startX] + Integer.toString(startY);*/

      checkUserGuess(userGuess);
      for(Ship shipTest : shipList) {
        if(shipTest.isLive()) {
          game = true;
          break;
        }
        else game = false;
      }
    }
  }

// Не рпюотает метод случайного расположения элементов
  private ArrayList<String> setPositionShip() {
    ArrayList<String> loc = new ArrayList<String>();
    ArrayList<String> locTest = new ArrayList<String>();
    int startX = (int) (Math.random() * 8);
    int startY = (int) (Math.random() * 9);
    if(startX > 7) startX = 7;
    if(startY > 8) startY = 8;
    if(startY < 1) startY = 1;
    {
      loc.add(literPos[startX] + Integer.toString(startY));
      int len = (int) (Math.random() * 5);
      if(len == 0) len = 1;
              //len = 2;
      System.out.println("\n" + literPos[startX] + Integer.toString(startY) + " - " + len);
      int randNapr = 0;
      while(randNapr >= 0) {
        randNapr = (int) (Math.random() * 4);
        if(randNapr == 0) {
          if((startX+len) <= 7) {
            for(int i=startX+1; i<=startX+len; i++) {
              loc.add(literPos[i] + Integer.toString(startY));
              System.out.println(literPos[i] + Integer.toString(startY) + " ");
            }
            randNapr = -1;
          }
        } else if(randNapr == 1) {
          if((startX-len) >= 0) {
            for(int i=startX-1; i>=startX-len; i--) {
              loc.add(literPos[i] + Integer.toString(startY));
              System.out.println(literPos[i] + Integer.toString(startY) + " ");
            }
            randNapr = -1;
          }
        } else if(randNapr == 2) {
          if((startY+len) <= 8) {
            for(int i=startY+1; i<=startY+len; i++) {
              loc.add(literPos[startX] + Integer.toString(i));
              System.out.println(literPos[startX] + Integer.toString(i) + " ");
            }
            randNapr = -1;
          }
        } else if(randNapr == 3){
          if((startY-len) >= 1) {
            for(int i=startY-1; i>=startY-len; i--) {
              loc.add(literPos[startX] + Integer.toString(i));
              System.out.print(literPos[startX] + Integer.toString(i) + " ");
            }
            randNapr = -1;
          }
        }
      }
    }
    return loc;
  }

  public void showBattlefield() {
    System.out.print("   ");
    for(int i=1; i<9; i++) {
      System.out.print(" " + i + "  ");
    }

    for(int x=0; x<8; x++) {
      System.out.print("\n" + literPos[x] + " |");
      for(int y=1; y<9; y++) {
        if(guessList.indexOf(literPos[x] + Integer.toString(y)) >= 0) {
          System.out.print("[");
          if(isEmptyField(literPos[x] + Integer.toString(y)))
            System.out.print("X]|");
          else System.out.print(" ]|");
        } else System.out.print(" - |");
      }
    }
    System.out.print("\n");
  }

  public boolean isEmptyField(String pos) {
    for(Ship shipTest : shipList) {
      if(shipTest.getDead(pos))
      return true;
    }
    return false;
  }

  public String checkUserGuess(String userGuess) {
    numOfGuesses++;
    String result = "Мимо";
    guessList.add(userGuess);

    for(Ship shipTest : shipList) {
      result = shipTest.checkIsAttack(userGuess);

      if(result.equals("Попал")) break;
      if(result.equals("Потопил")) {
        //shipList.remove(shipTest);
        break;
      }
    }
    return result;
  }

  public void finishGame() {
    System.out.println("Все корабли потоплены");
    System.out.println("Вы сделали это за " + numOfGuesses + " хода(ов)");
  }

  public static void main(String [] args) {
    NavalBattle game = new NavalBattle();
    game.setUpGame();
    game.startPlaying();
    game.finishGame();
  }
}

// Класс лодки
public class Ship {
  private ArrayList<String> locationLivePartsOfShip;
  private ArrayList<String> locationDeadPartsOfShip = new ArrayList<String>();

  public Ship(ArrayList<String> loc) {
    this.locationLivePartsOfShip = loc;
  }

  public boolean getDead(String pos) {
    if(locationDeadPartsOfShip.indexOf(pos) >= 0) return true;
    return false;
  }

  public boolean isLive() {
    if(locationLivePartsOfShip.isEmpty()) return false;
    else return true;
  }

  public String checkIsAttack(String userGuess) {
    String result = "Мимо";
    int index = locationLivePartsOfShip.indexOf(userGuess);

    if(index >= 0) {
      locationDeadPartsOfShip.add(userGuess);
      locationLivePartsOfShip.remove(index);

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
