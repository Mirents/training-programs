public class BeerSong {
  public static void main(String[] args) {
    int beerNum = 99;
    String word = "бутылок (бутылки)";

    while (beerNum > 0) {
      // Очевидно неправильное выплонение программы в
      // коде определения окончания слова "бутылка".
      if (beerNum == 1)
      word = "бутылка";

      System.out.println(beerNum + " " + word + " пива на стене");
      System.out.println(beerNum + " " + word + " пива.");
      System.out.println("Возьми одну.");
      System.out.println("Пусти по кругу.");
      beerNum--;
      if (beerNum > 0)
        System.out.println(beerNum + " " + word + " пива на стене");
      else
        System.out.println("Нет бутылок пива на стене");
    }
  }
}
