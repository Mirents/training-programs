// package training_programs;

import java.io.*;
import java.util.ArrayList;

public class DotComTestDrive {
  public static void main(String [] args) {
    int numOfGuess = 0;
    int num = 10;
    int lengthLocations = 4;
    int startLocations = (int) (Math.random() * (num-lengthLocations+1));

    DotCom dot = new DotCom(startLocations, 4);
    GameInput gi = new GameInput();
    System.out.println("Цель игры - угадать ячейки, в которых загаданы числа.\n");
    dot.showliveCells();

    while(true) {
      String userGuess = gi.getUserInput("Ведите число: ");
      System.out.print("Пользователь выбирает ячейку: " + userGuess);
      numOfGuess++;
      String result = dot.checkYourself(userGuess);
      if (result.equals("Потопил") ) {
        System.out.println(" - Корабль потоплен");
        dot.showliveCells();
        break;
      } else if (result.equals("Попал")) {
        System.out.println(" - Попадание, счет равен: " + dot.numOfHits);
      } else if (result.equals("Уже потоплен")) {
        System.out.println(" - Вы уже стреляли в эту ячейку");
      } else if (result.equals("Мимо")) {
        System.out.println(" - Промах, счет равен: " + dot.numOfHits);
      }
      dot.showliveCells();
    }

    System.out.println("Потребовалось " + numOfGuess + " попыток");
  }
}

public class DotCom {
  private ArrayList<Integer> liveCell = new ArrayList<>();
  private ArrayList<Integer> deadCell = new ArrayList<>();
  int numOfHits = 0;
  int lengthLocations = 0;

  public DotCom(int startLocations, int lengthLocations) {
    this.lengthLocations = lengthLocations;
    for(int i=0; i<lengthLocations; i++) {
      this.liveCell.add(startLocations);
      startLocations++;
    }
  }
  public void setliveCells(ArrayList<Integer> loc) {
    liveCell = loc;
  }

  public void showliveCells() {
    System.out.print("|");
    for(int i=0; i<10; i++) {
      System.out.print(" " + i + " |");
    }
    System.out.print("\n|");

    for(int i=0; i<10; i++) {
      if(isCells(i)) {
        System.out.print(" x |");
      } else System.out.print(" - |");
    }
    System.out.print("\n");
  }

  public boolean isCells(int i) {
    for(int cells : deadCell) {
      if(i == cells) return true;
    }
    return false;
  }

  public String checkYourself(String stringGuess) {
    int guess = Integer.parseInt(stringGuess);
    int index = liveCell.indexOf(guess);
    String result = "Мимо";
    numOfHits++;
    if(index >= 0) {
      liveCell.remove(index);
      deadCell.add(guess);

      if(liveCell.isEmpty()) {
        result = "Потопил";
      } else { result = "Попал"; }
    }
    /*for(int i : liveCell) {
      if (guess == i) {
        result = "Попал";
        numOfHits++;
        liveCell.remove(guess);
        break;
      }*/ /*else if (liveCells[guess] == -1) {
        result = "Уже потоплен";
      }
    }*/

    /*if (numOfHits == lengthLocations) {
      result = "Потопил";
    }*/
    return result;
  }
}

public class GameInput {
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
