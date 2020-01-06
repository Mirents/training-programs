//package training_programs;

import java.util.ArrayList;

public class AbstractInterface {
  public static void main(String[] args) {
    ArrayList<Animals> list = new ArrayList<Animals>();
    list.add(new Cat("Marusia"));
    list.add(new Byrd("Vorobey"));

    for(Animals a : list)
      a.makeSound();
  }
}

public abstract class Animals {
  public String name;

  public abstract void makeSound();
}

public class Cat extends Animals {
  public Cat(String name) {
    this.name = name;
  }

  public void makeSound() {
    System.out.println("Cat " + name + " says Meow!");
  }
}

public class Byrd extends Animals {
  public Byrd(String name) {
    this.name = name;
  }

  public void makeSound() {
    System.out.println("Byrd " + name + " says Chik-chirik!");
  }
}
