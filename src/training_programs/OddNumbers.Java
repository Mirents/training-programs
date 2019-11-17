package training_programs;
// Output to the console odd numbers
// from 0 to the entered maximum

import java.util.Scanner;

public class OddNumbers
{
    public static void main(String[] args) {
        int maxInt;
        Scanner myInt = new Scanner(System.in);

        maxInt = myInt.nextInt();
        
        System.out.println("Output odd numbers to max " + maxInt);
        for(int i=0; i<=maxInt; i++)
        {
            if (i%2 != 0) {
                System.out.println(i);
            } else {continue;}
        }
    }
}
