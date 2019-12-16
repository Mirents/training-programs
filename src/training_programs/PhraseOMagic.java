public class PhraseOMagic {
  public static void main (String[] args) {
    // Создание трех списков для формирования предложений
    String[] wordListOne = {"круглосуточно", "трех-звенно", "3-х метрово",
    "взаимно", "обоюдно", "фронтэндно", "импульсивно",
    "проникающе", "Вертикально", "стопроцентно", "динамично"};

    String[] wordListTwo = {"уполномоченный", "трудный", "парадный",
    "ориентированный", "центральный", "распределенный", "кластеризованный",
    "фирменный", "изящный", "позиционированный", "сетевой", "сфокусированный",
    "выгодный", "выровненный", "ветикальный", "общий", "совместный",
    "ускоренный"};

    String[] wordListThree = {"процесс", "пункт загрузки", "выход из положения",
    "тип структуры", "талант", "подход", "уровень завоевонного внимания",
    "портал", "период времени", "обзор", "образец", "пункт следования"};

    // Определение длинн массивов
    int oneLength = wordListOne.length;
    int twoLength = wordListTwo.length;
    int threeLength = wordListThree.length;

    int rand1 = (int) (Math.random() * oneLength);
    int rand2 = (int) (Math.random() * twoLength);
    int rand3 = (int) (Math.random() * threeLength);

    String phrase = wordListOne[rand1] + " " + wordListTwo[rand2] + " " +
    wordListThree[rand3];

    System.out.println("Вот: " + phrase);
  }
}
