public class Mix {
  int counter = 0;

  public static void main(String [] args) {
    int count = 0;
    Mix [] mix = new Mix[20];
    int x = 0;
    while (x < 19) {
      mix[x] = new Mix();
      mix[x].counter++;
      count++;
      count = count + mix[x].maybeNew(x);
      x++;
    }
    System.out.println(count + " " + mix[1].counter);
  }

  public int maybeNew(int index) {
    if (index < 1) {
      return 1;
    }
    return 0;
  }
}
