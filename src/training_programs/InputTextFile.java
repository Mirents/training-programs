package training_programs;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class InputTextFile {

	public static void main(String[] args) {
		BufferedReader br = null;
		try {
			// Определение объекта для чтения файла
			br = new BufferedReader(new FileReader("history_command.txt"));
			// Переменная для считывания данных из файла
			String line;
			// Создание новой переменной для слияния всей строки
			String s = "";
			// Процесс считывания из файла
			while((line = br.readLine()) != null)
			{
				System.out.println(line);
				s += line + " ";
			}
			if(s != null) {
				System.out.println("All text to file");
				System.out.println(s);
			}
		} catch(IOException e) {
			System.out.print("Error " + e);
		}
		finally
		{
			try
			{
				br.close();
			} catch(IOException e)
			{
				System.out.print("Error " + e);
			}
		}
	}

}
