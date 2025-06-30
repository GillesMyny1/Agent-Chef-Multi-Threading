package lab1;

/**
 * BoundedBuffer class deals with the Bounded Buffer creation and maintenance.
 * This class has a set final SIZE and write/read boolean checks. More so, this
 * class has the newTable() method which takes care of updating the BoundedBuffer
 * with two new ingredients, the clearTable() method which takes care of clearing the
 * BoundedBuffer, lastly the peek() method which allows the Chef thread to check the contents
 * of the BoundedBuffer.
 * @author Gilles Myny
 */

import java.util.*;

public class BoundedBuffer {
	
	static final int SIZE = 2;
	private Object[] buffer = new Object[SIZE];
	
	static boolean writeable = true;
	static boolean readable = false;
	
	/**
	 * The newTable() method accepts an array of Object type and checks whether the BoundedBuffer
	 * is in a writeable state (empty). If not the thread is told to wait, if so the BoundedBuffer
	 * spaces are filled in and all necessary threads are notified.
	 * @param newIngs represents an Object array of new ingredients added by the Agent thread(s).
	 */
	public synchronized void newTable(Object[] newIngs) {
		while(!writeable) {
			try {
				wait();
			} catch(InterruptedException e) {
				System.err.println(e);
			}
		}
		for(int i = 0; i < SIZE; i++) {
			buffer[i] = newIngs[i];
		}
		readable = true;
		writeable = false;
		notifyAll();
	}
	
	/**
	 * The clearTable() method checks whether the BoundedBuffer is in a readable state (full). If not
	 * the thread is told to wait, if so the BoundedBuffer spaces are replaced with null (for safety).
	 */
	public synchronized void clearTable() {
		while(!readable) {
			try {
				wait();
			} catch(InterruptedException e) {
				System.err.println(e);
			}
		}
		for(int i = 0; i < SIZE; i++) {
			buffer[i] = null;
		}
		writeable = true;
		readable = false;
		notifyAll();
	}
	
	
	/**
	 * The peek() method returns a String ArrayList which contains the ingredients on the table.
	 * More importantly, it checks whether the BoundedBuffer is in a readable state (full). If not
	 * the thread is told to wait.
	 * @return a String ArrayList containing the two ingredients in the BoundedBuffer.
	 */
	public ArrayList<String> peek() {
		while(!readable) {
			try {
				wait();
			} catch(InterruptedException e) {
				System.err.println(e);
			}
		}
		ArrayList<String> ings = new ArrayList<>();
		for(int i = 0; i < SIZE; i++) {
			ings.add(buffer[i].toString());
		}
		return ings;
	}
}
