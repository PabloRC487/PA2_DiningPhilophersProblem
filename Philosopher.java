import common.BaseThread;

/**
 * Class Philosopher. Outlines main subrutines of our virtual philosopher.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Philosopher extends BaseThread {
	/**
	 * Max time an action can take (in milliseconds)
	 */
	public static final long TIME_TO_WASTE = 1000;
	public final double PROB_WANTING_PEPPER = 1;

	/**
	 * The act of eating.
	 * - Print the fact that a given phil (their TID) has started eating.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done eating.
	 */
	public void eat()
	{
		try
		{
			System.out.println("I am philosopher " + getTID() + " and I am starting to eat.\n");
			randomYield();
			// ...
			
			if(Math.random() < PROB_WANTING_PEPPER) {
				System.out.println("Philosopher " + getTID() + " wants a pepper shaker.\n");
				DiningPhilosophers.soMonitor.requestPepper();
				randomYield();
				DiningPhilosophers.soMonitor.releasePepper();
				System.out.println("Philosopher " + getTID() + " has released the pepper shaker\n");
			}
			
			sleep((long)(Math.random() * TIME_TO_WASTE));
			
			// ...
			randomYield();
			System.out.println("I am philosopher " + getTID() + " and I have finished eating.\n");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.eat():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of thinking.
	 * - Print the fact that a given phil (their TID) has started thinking.
	 * - yield
	 * - Then sleep() for a random interval.
	 * - yield
	 * - The print that they are done thinking.
	 */
	public void think()
	{
		// ...
		try
		{
			System.out.println("I am philosopher " + getTID() + " and I am starting to think.\n");
			randomYield();
			sleep((long)(Math.random() * TIME_TO_WASTE));
			randomYield();
			System.out.println("I am philosopher " + getTID() + " and I have finished thinking.\n");
		}
		catch(InterruptedException e)
		{
			System.err.println("Philosopher.think():");
			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * The act of talking. - Print the fact that a given phil (their TID) has
	 * started talking. - yield - Say something brilliant at random - yield - The
	 * print that they are done talking.
	 */

	public void talk()	
	{
		System.out.println("Philosopher " + getTID() + " starts talking:\n");
		randomYield();
		saySomething();
		randomYield();
		System.out.println("Philosopher " + getTID() + " has stopped talking\n");
	}

	/**
	 * No, this is not the act of running, just the overridden Thread.run()
	 */
	public void run() {
		for(int i = 0; i < DiningPhilosophers.DINING_STEPS; i++)
		{
			DiningPhilosophers.soMonitor.pickUp(getTID());

			eat();

			DiningPhilosophers.soMonitor.putDown(getTID());

			think();

			/*
			 * TODO:
			 * A decision is made at random whether this particular
			 * philosopher is about to say something terribly useful.
			 */
			if(Math.random() < 0.25)
			{
				DiningPhilosophers.soMonitor.requestTalk();
				talk();
				DiningPhilosophers.soMonitor.endTalk();
			}

			randomYield();
		}
	} // run()

	/**
	 * Prints out a phrase from the array of phrases at random. Feel free to add
	 * your own phrases.
	 */
	public void saySomething() {
		String[] astrPhrases = { "Eh, it's not easy to be a philosopher: eat, think, talk, eat...\n",
				"You know, true is false and false is true if you think of it\n",
				"2 + 2 = 5 for extremely large values of 2...\n", "If thee cannot speak, thee must be silent\n",
				"My number is " + getTID() + "\n" };

		System.out.println(
				"Philosopher " + getTID() + " says: " + astrPhrases[(int) (Math.random() * astrPhrases.length)]);
	}
}

// EOF
