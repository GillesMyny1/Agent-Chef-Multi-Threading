package lab1;

/**
 * Chef class deals with the Chef threads.
 * This class has a run method and a makeSandwich method which retrieves 
 * the two ingredients and checks if those ingredients do not match 
 * that Chef's specific ingredient.
 * @author Gilles Myny
 */
import java.util.*;

public class Chef implements Runnable {
	private BoundedBuffer foodTable;
	private String mainIngredient;
	private String chefName;
	
	
	/**
	 * Basic Chef class constructor.
	 * @param name represents a String of the Chef's name.
	 * @param buf represents the BoundedBuffer object passed to all Chef's.
	 * @param mainIng represents a String of the Chef's main ingredient.
	 */
	public Chef(String name, BoundedBuffer buf, String mainIng) {
		this.chefName = name;
		this.foodTable = buf;
		this.mainIngredient = mainIng;
	}
	
	/**
	 * The run() method keeps calling makeSandwich() method 
	 * until the total amount of sandwiches eaten by all Chef's is 20.
	 */
	public void run() {
		while(Restaurant.sandwichCount < 20) {
			makeSandwich();
		}
	}
	
	/**
	 * The makeSandwich() method contains a synchronized block tied into the BoundedBuffer object reference.
	 * If the retrieved ingredients on the table do not match the Chef's main ingredient the Chef will take
	 * the ingredients off the table and make & eat the sandwich while incrementing the sandwichCount.
	 * If that's not the case the Chef thread is told to wait until the foodTable is updated.
	 */
	public void makeSandwich() {
		synchronized(foodTable) {
			ArrayList<String> ingredients = foodTable.peek();
			if(!ingredients.contains(mainIngredient)) {
				foodTable.clearTable();
				Restaurant.sandwichCount++;
				System.out.println(chefName + " made and ate a sandwich.");
				System.out.println("The total sandwiches made and eaten is " + Restaurant.sandwichCount + "\n");
				foodTable.notifyAll();
			} else {
				try {
					foodTable.wait();
				} catch(InterruptedException e) {
					System.err.println(e);
				}
			}
		}
	}
}
