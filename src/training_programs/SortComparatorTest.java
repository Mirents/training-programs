import java.util.*;
import java.io.*;

public class SortComparatorTest {
  ArrayList<Task> listTask= new ArrayList<Task>();

  public static void main(String[] args) {
    new SortComparatorTest().go();
  }

  public void go() {
    listTask.add(new Task("Get out", 1, 2));
    listTask.add(new Task("Play", 2, 3));
    listTask.add(new Task("Learn", 1, 4));
    listTask.add(new Task("Go", 2, 1));
    listTask.add(new Task("Go go go", 3, 2));
    listTask.add(new Task("Stop", 2, 4));
    listTask.add(new Task("Love", 3, 1));

    System.out.println("Print after sort:");
    PrintList();

    PriorityCompare pc = new PriorityCompare();
    Collections.sort(listTask, pc);
    Collections.sort(listTask, pc);
    System.out.println("Print before sort priority 2:");
    PrintList();

    InfluenceCompare ic = new InfluenceCompare();
    Collections.sort(listTask, ic);
    System.out.println("Print before sort influence:");
    PrintList();
  }

  public void PrintList() {
      for(Task t : listTask)
        System.out.println(t);
  }

  class PriorityCompare implements Comparator<Task> {
    public int compare(Task one, Task two) {
      return one.getPriority() - two.getPriority();
    }
  }

  class InfluenceCompare implements Comparator<Task> {
    public int compare(Task one, Task two) {
      return one.getInfluence() - two.getInfluence();
    }
  }

  class Task {
    String description;
    int priority;
    int influence;

    Task(String description, int priority, int influence) {
      this.description = description;
      this.priority = priority;
      this.influence = influence;
    }

    @Override
    public String toString() {
      return priority + " " + influence + " " + description;
    }

    public void setDescription(String description) {
      this.description = description;
    }

    public void setPriority(int priority) {
      this.priority = priority;
    }

    public void setInfluence(int influence) {
      this.influence = influence;
    }

    public String getDescription() {
      return description;
    }

    public int getPriority() {
      return priority;
    }

    public int getInfluence() {
      return influence;
    }
  } // End Task class
}
