import java.util.ArrayList;
import java.io.*;
/*Простая игра наподобие морского боя, где случайным образом на поле размером
7х7 располагаются корабли, а пользователь угадывает их расположение*/

// Основной класс, контролирующий работу программы
public class NavalBattle {
  private GameHelper gameHelper = new GameHelper();
  private ArrayList<Ship> shipList = new ArrayList<Ship>();
  private String [] literPos = {"a", "b", "c", "d", "e", "f", "g", "h"};
  int numOfGuesses = 0;

  private void setUpGame() {
    // Создание трех кораблей
    /*for(int i=0; i<3; i++) {
      ArrayList<String> loc = new ArrayList<String>();
      loc.add("B" + Integer.toString(i));
      shipList.add(new Ship("wdd", loc));
    }*/

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
    ArrayList<String> loc3 = new ArrayList<String>();
    loc3.add("g4");
    loc3.add("g5");
    loc3.add("g6");
    loc3.add("g7");
    shipList.add(new Ship(loc3));

    // Информация начального экрана
    System.out.println("Цель - потопить три корабля");
    System.out.println("Постарайтель сделать это за минимальное количество ходов");
  }

  public void startPlaying() {
    boolean game = true;
    while(game) {
      showBattlefield();
      String userGuess = gameHelper.getUserInput("Сделайте ход: ");
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

  public void showBattlefield() {
    System.out.print("   ");
    for(int i=0; i<8; i++) {
      System.out.print(" " + i + "  ");
    }

    for(int x=0; x<8; x++) {
      System.out.print("\n" + literPos[x] + " |");
      for(int y=0; y<8; y++) {
        if(isEmptyField(literPos[x] + Integer.toString(y)))
          System.out.print(" x |");
        else System.out.print(" - |");
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
