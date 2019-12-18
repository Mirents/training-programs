import java.io.*;

public class SimpleDotComTestDrive {
  public static void main(String [] args) {
    int numOfGuess = 0;
    int startLocations = (int) (Math.random() * 8);
    SimpleDotCom dot = new SimpleDotCom(10, startLocations);
    GameInput gi = new GameInput();
    dot.showLocationCells();

    while(true) {
      // String userGuess = Integer.toString((int) (Math.random() * 10));
      String userGuess = gi.getUserInput("Ведите число: ");
      System.out.print("Пользователь атакует ячейку: " + userGuess);
      numOfGuess++;
      String result = dot.checkYourself(userGuess);
      if (result.equals("Потопил") ) {
        System.out.println(" - Корабль потоплен, счет равен: " + dot.numOfHits);
        break;
      } else if (result.equals("Попал")) {
        System.out.println(" - Попадание, счет равен: " + dot.numOfHits);
      } else if (result.equals("Уже потоплен")) {
        System.out.println(" - Вы уже стреляли в эту ячейку");
      } else if (result.equals("Мимо")) {
        System.out.println(" - Промах");
      }
      dot.showLocationCells();
    }

    System.out.println("Потребовалось " + numOfGuess + " попыток");
  }
}

public class SimpleDotCom {
  int [] locationCells;
  int numOfHits = 0;

  public SimpleDotCom(int num, int startLocations) {
    locationCells = new int[num];
    for(int i=0; i<10; i++) {
      if(i == startLocations) {
        locationCells[i] = startLocations;
      } else if(i == startLocations+1) {
        locationCells[i] = startLocations+1;
      } else if(i == startLocations+2) {
        locationCells[i] = startLocations+2;
      } else locationCells[i] = 0;
    }
  }
  public void setLocationCells(int [] locs) {
    locationCells = locs;
  }

  public void showLocationCells() {
    for(int i=0; i<locationCells.length; i++) {
      if(isCells(i)) System.out.print(-i);
      else System.out.print("-0");
    }
    System.out.print("\n");
  }

  public boolean isCells(int i) {
    for(int cells : locationCells) {
      if(i == cells) return true;
    }
    return false;
  }

  public String checkYourself(String stringGuess) {
    int guess = Integer.parseInt(stringGuess);
    String result = "Мимо";

    for(int i=0; i<locationCells.length; i++) {
      if (guess == locationCells[i] && locationCells[i] > 0) {
        result = "Попал";
        numOfHits++;
        locationCells[i] = -1;
        break;
      } else if (locationCells[i] == -1) {
        result = "Уже потоплен";
      }
    }

    if (numOfHits == 3) {
      result = "Потопил";
    }
    return result;
  }
}

public class GameInput {
  public String getUserInput(String promt) {
    String inputLine = null;
    System.out.print("\n" + promt + " ");
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
