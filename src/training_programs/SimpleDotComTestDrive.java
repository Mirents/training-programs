public class SimpleDotComTestDrive {
  public static void main(String [] args) {
    SimpleDotCom dot = new SimpleDotCom();
    int numOfGuess = 0;
    int startLocations = (int) (Math.random() * 8);
    startLocations = 2;
    System.out.println(startLocations + "-" + (startLocations+1) + "-" + (startLocations+2));

    int [] locations = {startLocations, startLocations+1, startLocations+2};
    dot.setLocationCells(locations);
    dot.showLocationCells();

    while(true) {
      String userGuess = Integer.toString((int) (Math.random() * 10));
      System.out.println("Пользователь атакует ячейку: " + userGuess);
      numOfGuess++;
      String result = dot.checkYourself(userGuess);
      if (result.equals("Потопил") ) {
        System.out.println("Счет равен: " + dot.numOfHits);
        break;
      } else if (result.equals("Попал")) {
        System.out.println("Счет равен: " + dot.numOfHits);
      }
    }

    System.out.println("Потопил за " + numOfGuess + " ходов");
  }
}

public class SimpleDotCom {
  int [] locationCells;
  int numOfHits = 0;

  public void setLocationCells(int [] locs) {
    locationCells = locs;
  }

  public void showLocationCells() {
    for(int i=0; i<10; i++) {
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

    //for (int cell : locationCells) {
    if (guess == cell && cell != -1) {
    for(int i=0; i<10; i++) {}
      if (guess == locationCells[i] && locationCells[i] != -1) {
        result = "Попал";
        numOfHits++;
        locationCells[i] = -1;
        break;
      }
    }

    if (numOfHits == locationCells.length) {
      result = "Потопил";
    }

    return result;
  }
}
