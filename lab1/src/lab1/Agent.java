package lab1;

/**
 * Agent class deals with the randomized distribution of two ingredients to the table,
 * where Chef threads can then pick from to make and eat a sandwich. This class contains
 * a randomizer method, randomNum() which chooses a random number, a run() method which runs
 * the addRandomIngredients() method if the total number of sandwiches made is less than 19 (counting 0).
 * @author Gilles Myny
 */

import java.util.*;

public class Agent implements Runnable {
	private BoundedBuffer foodTable;
	private String agentName;
	private String choiceOne;
	private String choiceTwo;
	private String choiceThree;
	private final int MIN = 1;
	private final int MAX = 3;
	
	/**
	 * Simple Agent constructor which initializes the variables.
	 * @param name represents a String of the Agent's name.
	 * @param buf represents a BoundedBuffer object.
	 * @param ingOne represents a String of the first kind of ingredient.
	 * @param ingTwo represents a String of the second kind of ingredient.
	 * @param ingThree represents a String of the third kind of ingredient.
	 */
	public Agent(String name, BoundedBuffer buf, String ingOne, String ingTwo, String ingThree) {
		agentName = name;
		foodTable = buf;
		choiceOne = ingOne;
		choiceTwo = ingTwo;
		choiceThree = ingThree;
	}
	
	/**
	 * The run() method keeps calling addRandomIngredients() method 
	 * until the total amount of sandwiches eaten by all Chef's is 20.
	 */
	public void run() {
		while(Restaurant.sandwichCount < 19) {
			addRandomIngredients();
		}
	}
	
	/**
	 * The addRandomIngredients() method contains a synchronized block 
	 * tied into the BoundedBuffer object reference.
	 * The method creates a String ArrayList and calls on the randomNum() 
	 * method to return a randomized number between set values of MAX and MIN.
	 * A simple if else if block controls the two kinds of ingredients to be 
	 * added to the BoundedBuffer. The method then prints out the two ingredients
	 * added and notifies all threads.
	 */
	public void addRandomIngredients() {
		synchronized(foodTable) {
			ArrayList<String> newIngs = new ArrayList<>();
			int num = randomNum();
			if(num == 1) {
				newIngs.add(choiceThree);
				newIngs.add(choiceOne);
				foodTable.newTable(newIngs.toArray());
			} else if(num == 2) {
				newIngs.add(choiceTwo);
				newIngs.add(choiceThree);
				foodTable.newTable(newIngs.toArray());
			} else if(num == 3) {
				newIngs.add(choiceTwo);
				newIngs.add(choiceOne);
				foodTable.newTable(newIngs.toArray());
			}
			System.out.println(agentName + " has put out two ingredients: " + newIngs);
			newIngs.clear();
			foodTable.notifyAll();
		}
	}
	
	/**
	 * The randomNum() is a simple method that generates a random number
	 * with the help of Math.random() method.
	 * @return an Integer value representing the random number.
	 */
	private int randomNum() {
		return (int)(Math.random() * (MAX - MIN + 1)) + MIN;
	}
}
