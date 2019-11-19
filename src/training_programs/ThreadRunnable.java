package training_programs;

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
		
	}

}
