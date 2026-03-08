/**
 * Class Monitor To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
	/*
	 * ------------ Data members ------------
	 */

	private static final int THINKING = 0;
	private static final int HUNGRY = 1;
	private static final int EATING = 2;

	private int[] state;
	private int numOfPhil;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		// TODO: set appropriate number of chopsticks based on the # of philosophers
		numOfPhil = piNumberOfPhilosophers;
		state = new int[numOfPhil];
		for (int i = 0; i < numOfPhil; i++) {
			state[i] = THINKING;
		}
	}

	/*
	 * ------------------------------- User-defined monitor procedures You may need
	 * to add more procedures for task 5 -------------------------------
	 */
	private void test(int piTID) {
		int i = piTID - 1;
		int left = (i - 1 + numOfPhil) % numOfPhil;
		int right = (i + 1) % numOfPhil;
		if (state[left] != EATING && state[right] != EATING && state[i] == HUNGRY) {
			state[i] = EATING;
			notifyAll();
		}
	}

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) {
		// ...
		int i = piTID - 1;
		state[i] = HUNGRY;
		test(piTID);
		while (state[i] != EATING) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down and
	 * let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {
		// ...
		int i = piTID - 1;
		state[i] = THINKING;
		int left = (i - 1 + numOfPhil) % numOfPhil;
		int right = (i + 1) % numOfPhil;
		test(left + 1);
		test(right + 1);
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy (while she is not
	 * eating).
	 */
	public synchronized void requestTalk() {
		// ...
	}

	/**
	 * When one philosopher is done talking stuff, others can feel free to start
	 * talking.
	 */
	public synchronized void endTalk() {
		// ...
	}
}

// EOF
