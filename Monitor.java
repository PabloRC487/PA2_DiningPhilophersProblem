/**
 * Class Monitor To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */

/*
 * Task 4: Dynamic Modification of the Number of Philosophers
 *
 * Changing the number of philosophers mid-execution is not possiblefor the following reasons:
 *
 * 1. The state[] array is fixed at initialization. Adding or removing a new philosopher
 *    would require resizing the array and recalculating all neighbor indices,
 *    which would break the logic of philosophers currently waiting in wait().
 *
 * 2. If a philosopher leaves mid-execution it could be possible that he leaves 
 * 	  while still holding a chopstick, which would break the system if another 
 * 	  philosopher is waiting for that chopstick.
 *
 * To support true dynamic modification, the entire Monitor would need to be
 * redesigned using dynamic data structures (e.g. ArrayList instead of array)
 * and additional synchronization to handle structural changes safely.
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
	private boolean isTalking = false;

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
		while (isTalking) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isTalking = true;
	}

	/**
	 * When one philosopher is done talking stuff, others can feel free to start
	 * talking.
	 */
	public synchronized void endTalk() {
		isTalking = false;
		notifyAll();
	}
}

// EOF
