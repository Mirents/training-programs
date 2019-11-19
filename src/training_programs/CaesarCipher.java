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
		
	}
}
