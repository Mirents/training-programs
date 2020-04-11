import java.util.*;
import java.io.*;

public class SortComparatorTest {
  ArrayList<Task> listTask= new ArrayList<Task>();

  public static void main(String[] args) {
    new SortComparatorTest().go();
  }

  public void go() {
    listTask.add(new Task("Get out", 1, 2));
    listTask.add(new Task("Play", 1, 3));
    listTask.add(new Task("Learn", 1, 4));
    listTask.add(new Task("Go", 2, 1));
    listTask.add(new Task("Go go go", 3, 2));
    listTask.add(new Task("Stop", 2, 4));
    listTask.add(new Task("Love", 1, 5));

    System.out.println("Print before sort:");
    PrintList();

    Comparator<Task> sort = new PriorityCompare();
    Collections.sort(listTask, sort);
    System.out.println("Print after sort priority:");
    PrintList();

    sort = new InfluenceCompare();
    Collections.sort(listTask, sort);
    System.out.println("Print after sort influence:");
    PrintList();
  }

  public void PrintList() {
      for(Task t : listTask)
        System.out.println(t);
  }

  class PriorityCompare implements Comparator<Task> {
    public int compare(Task one, Task two) {
      if(one.getPriority() == two.getPriority()) {
        return Integer.compare(one.getInfluence(), two.getInfluence());
      } else {
        return Integer.compare(one.getPriority(), two.getPriority());
      }
    }
  }

  class InfluenceCompare implements Comparator<Task> {
    public int compare(Task one, Task two) {
      if(one.getInfluence() == two.getInfluence()) {
        return Integer.compare(one.getPriority(), two.getPriority());
      } else {
        return Integer.compare(one.getInfluence(), two.getInfluence());
      }
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
