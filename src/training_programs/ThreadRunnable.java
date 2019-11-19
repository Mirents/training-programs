package training_programs;
// Work two methods of Thread

class someThtead extends Thread
{
	public void run() {
		for(int i = 1; i <= 100; i++)
		{
			System.out.println("Thread number " + i);
		}
	}
}

class someRunnable implements Runnable
{

	@Override
	public void run() {
		for(int i = 1; i <= 100; i++)
		{
			System.out.println("Runnable number " + i);
		}
	}
}

public class ThreadRunnable {

	public static void main(String[] args) {
		// Реализация работы двух потоков первым методом
		someThtead sT1 = new someThtead();
		sT1.start();
		someThtead sT2 = new someThtead();
		sT2.start();
		
		// Реализация работы двух потоков вторым методом
		Thread sR1 = new Thread(new someRunnable());
		Thread sR2 = new Thread(new someRunnable());
		sR1.start();
		sR2.start();
	}
}
