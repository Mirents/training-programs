import java.io.*;

public class GameSaverTest implements Serializable {

  public static void main(String [] args) {
    new GameSaverTest();
  }

  GameSaverTest() {
    GameCharacter on = new GameCharacter(10, "Эльф", "Палка");
    GameCharacter tw = new GameCharacter(10, "Воин", "Железка");
    GameCharacter th = new GameCharacter(10, "Хитрец", "Иголка");

    try {
      ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream("Game.sp"));
      os.writeObject(on);
      os.writeObject(tw);
      os.writeObject(th);
      os.close();
    } catch(Exception e) { e.printStackTrace(); }

    on = null;
    tw = null;
    th = null;

    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream("Game.sp"));
      GameCharacter onR = (GameCharacter) in.readObject();
      GameCharacter twR = (GameCharacter) in.readObject();
      GameCharacter thR = (GameCharacter) in.readObject();

      System.out.println("One: " + onR.getType());
      System.out.println("Two: " + twR.getType());
      System.out.println("Three: " + thR.getType());
    } catch(Exception e) { e.printStackTrace(); }
  }

  public class GameCharacter implements Serializable {
    int power;
    String type;
    String weapons;

    public GameCharacter(int p, String t, String w) {
      power = p;
      type = t;
      weapons = w;
    }

    public String getType() {
      return type;
    }

    public int getPower() {
      return power;
    }

    public String getWeapons() {
        return weapons;
    }
  }
}
