package training_programs;

/*
Секретный способ цезаря общения с генералами.
Состоял в том, что он заменял букву на следующую в алфавите.
Пример:
Input: "defend the east wall of the castle"
Output: "efgfoe uif fbtu xbmm pg uif dbtumf"*/

import java.util.Scanner;

public class CaesarCipher {

	// Статическая переменная для проверки алфавита
	public static final String albh = "abcdefghijklmnopqrstuvwxyz";

	public static void main(String[] args) {
		Scanner myEnt = new Scanner(System.in);
		String str = "";
		// Вводим текстовую строку
		str = myEnt.nextLine();
		System.out.println(rev(str));
	}

	// Метод перестановки букв в строке
	public static String rev(String s)
	{
		// Временный буфер для работы с текстом
    StringBuilder sb = new StringBuilder(s);
    // Основной цикл
    for(int i=0;i<sb.length();i++)
    {
        // Первый символ для проверки из текста
        char c1 = sb.charAt(i);
        // Дополнительный цикл прохода по алфавиту
        for(int y=0;y<albh.length();y++)
        {
        // Второй символ для проверки из алфавита
        char c2 = albh.charAt(y);
        // Если символы равны
        if(c1==c2)
        {
        // Если это не последний элемент алфавита
        if(y!=albh.length()-1)
        {
        // Присваиваем тексту следующее значение
        sb.setCharAt(i, albh.charAt(y+1));
        }
        else
        {
        // Если это последний элемент - присваиваем первкю букву алфавита
        sb.setCharAt(i, albh.charAt(0));
        }
        }
        }
    }
    // Возврат измененного текста
    return sb.toString();
	}
}
