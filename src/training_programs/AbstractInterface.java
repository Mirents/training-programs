//package training_programs;

import java.util.ArrayList;

public class AbstractInterface {
  public static void main(String[] args) {
    ArrayList<Object> list = new ArrayList<Object>();
    list.add(new Cat("Marusia"));
    list.add(new Byrd("Vorobey"));

    // Вариант вызова 1
    for(Object a : list) {
      Animals A = (Animals) a;
      A.makeSound();
    }

    // Вариант вызова 2
    for(Object a : list) {
      Animals A = (Animals) a;

      if(A instanceof Cat) {
        Cat c = (Cat) a;
        c.makeSound();
      }

      if(A instanceof Byrd) {
        Byrd c = (Byrd) a;
        c.makeSound();
      }
    }
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
