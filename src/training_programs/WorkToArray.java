package training_programs;

import java.util.ArrayList;
import java.util.LinkedList;

public class WorkToArray {
	
	public static void workArrayList()
	{
		System.out.println("Work to ArrayList");
		ArrayList<Integer> arrlistInt = new ArrayList<>(1);
		arrlistInt.add(18);
		arrlistInt.add(15);
		arrlistInt.add(14);
		arrlistInt.add(2);
		arrlistInt.add(94);

		System.out.print("Size of Array " + arrlistInt.size() + "; ");
		System.out.print("Array`s Data: ");
		for(Integer x : arrlistInt)
			System.out.print(x + ";");

		System.out.print("\nDelete 3-th Element on Array; ");
		arrlistInt.remove(2);
		System.out.print("Array`s Data: ");
		for(Integer x : arrlistInt)
			System.out.print(x + ";");
		
		// Очистка массива и повторный вывод
		arrlistInt.clear();
		System.out.print("\nArray`s Clear");
		System.out.println("");
		for(Integer x : arrlistInt)
			System.out.print(x);
	}
	
	public static void workLinkedList()
	{
		System.out.println("\nWork to LinkedList");
		LinkedList<Integer> arrlistInt = new LinkedList<>();
		arrlistInt.add(18);
		arrlistInt.add(15);
		arrlistInt.add(14);
		arrlistInt.add(2);
		arrlistInt.add(94);

		System.out.print("Size of Array " + arrlistInt.size() + "; ");
		System.out.print("Array`s Data: ");
		for(Integer x : arrlistInt)
			System.out.print(x + ";");

		System.out.print("\nDelete 3-th Element on Array; ");
		arrlistInt.remove(2);
		System.out.print("Array`s Data: ");
		for(Integer x : arrlistInt)
			System.out.print(x + ";");
		
		System.out.print("\nAdd 4-th Element on Array 22; ");
		arrlistInt.add(3, 22);
		System.out.print("Array`s Data: ");
		for(Integer x : arrlistInt)
			System.out.print(x + ";");
		
		// Очистка массива и повторный вывод
		arrlistInt.clear();
		System.out.print("\nArray`s Clear");
		System.out.println("");
		for(Integer x : arrlistInt)
			System.out.print(x);
	}
	
	public static void main(String[] args) {
		workArrayList();
		workLinkedList();
	}

}
