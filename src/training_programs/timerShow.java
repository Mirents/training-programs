//package training_programs;

public class timerShow {

	public static void main(String[] args) {
		int thisPeriodTimer = 0; // Текущий период, 0-работа, 1-отдых, 2-большой перерыв
		int circleWorkTimer = 1; // Текущий цикл работы до большого перерыва
		int [] schemeTimerMinute = {5, 3, 4, 3}; // Массив переменных - {время работы,
		// перерыв, большой перерыв, количество циклов до большого перерыва
		int countTimerSecond = schemeTimerMinute[thisPeriodTimer]; // Счетчик вреиени
  System.out.println(thisPeriodTimer + " " + circleWorkTimer);
		while (countTimerSecond > 0) {
			countTimerSecond--;
			//System.out.println(countTimerSecond+" min");
			if(countTimerSecond<=0) {
				if(thisPeriodTimer==0) {
					if(circleWorkTimer==schemeTimerMinute[3]) {
						circleWorkTimer = 1;
						thisPeriodTimer = 2;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer];
						//System.out.println("Большой перерыв!");
						System.out.println(thisPeriodTimer + " " + circleWorkTimer);
					} else {
						circleWorkTimer++;
						thisPeriodTimer = 1;
						countTimerSecond = schemeTimerMinute[thisPeriodTimer];
						//System.out.println("Начинаем работать!");
						System.out.println(thisPeriodTimer + " " + circleWorkTimer);
					}
				} else if (thisPeriodTimer == 1) {
					thisPeriodTimer = 0;
					countTimerSecond = schemeTimerMinute[thisPeriodTimer];
					//System.out.println("Отдых закончен!");
					System.out.println(thisPeriodTimer + " " + circleWorkTimer);
				} else if (thisPeriodTimer == 2) {
					thisPeriodTimer = -1;
					circleWorkTimer = schemeTimerMinute[3];
					countTimerSecond = -5;
					//System.out.println("Отдых закончен!");
					System.out.println(thisPeriodTimer + " " + circleWorkTimer);
			}
		}
			//System.out.println(countTimerSecond+" min");
			//countTimerSecond--;
			// Составить агоритм и прогнать его в тестовом режиме
		}
	}
}
