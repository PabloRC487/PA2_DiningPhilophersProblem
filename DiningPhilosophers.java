
/**
 * Class DiningPhilosophers
 * The main starter.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */

import java.util.Scanner;

public class DiningPhilosophers {
	/*
	 * ------------ Data members ------------
	 */

	/**
	 * This default may be overridden from the command line
	 */
	public static final int DEFAULT_NUMBER_OF_PHILOSOPHERS = 5;

	/**
	 * Dining "iterations" per philosopher thread while they are socializing there
	 */
	public static final int DINING_STEPS = 10;

	/**
	 * Our shared monitor for the philosphers to consult
	 */
	public static Monitor soMonitor = null;

	/*
	 * ------- Methods -------
	 */

	/**
	 * Main system starts up right here
	 */
	public static void main(String[] argv) {
		try {
			/*
			 * TODO: Should be settable from the command line or the default if no arguments
			 * supplied.
			 */

			Scanner s = new Scanner(System.in);
			int iPhilosophers = 0;
			// int iPhilosophers = DEFAULT_NUMBER_OF_PHILOSOPHERS;

			while (iPhilosophers < 3) {
				System.out.println("Enter the number of philosophers you want (>=3): ");
				if (s.hasNextInt()) {
					iPhilosophers = s.nextInt();
					if (iPhilosophers < 3) {
						// For invalid integer
						System.out.println("\"" + iPhilosophers
								+ "\" is not an acceptable number. Please enter a positive number (bigger than or equal 3)");
					}
				} else {
					// For non-integer
					String invalid = s.next();
					System.out.println("\"" + invalid
							+ "\" is not an acceptable number. Please enter a positive number (bigger than or equal 3)");
					//s.next();
				}
			}
			s.close();

			// Make the monitor aware of how many philosophers there are
			soMonitor = new Monitor(iPhilosophers);

			// Space for all the philosophers
			Philosopher aoPhilosophers[] = new Philosopher[iPhilosophers];

			// Let 'em sit down
			for (int j = 0; j < iPhilosophers; j++) {
				aoPhilosophers[j] = new Philosopher();
				aoPhilosophers[j].start();
			}

			System.out.println(iPhilosophers + " philosopher(s) came in for a dinner.");

			// Main waits for all its children to die...
			// I mean, philosophers to finish their dinner.
			for (int j = 0; j < iPhilosophers; j++)
				aoPhilosophers[j].join();

			System.out.println("All philosophers have left. System terminates normally.");
		} catch (InterruptedException e) {
			System.err.println("main():");
			reportException(e);
			System.exit(1);
		}
	} // main()

	/**
	 * Outputs exception information to STDERR
	 * 
	 * @param poException Exception object to dump to STDERR
	 */
	public static void reportException(Exception poException) {
		System.err.println("Caught exception : " + poException.getClass().getName());
		System.err.println("Message          : " + poException.getMessage());
		System.err.println("Stack Trace      : ");
		poException.printStackTrace(System.err);
	}
}

// EOF
