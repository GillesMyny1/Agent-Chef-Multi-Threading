package lab1;

/**
 * The Restaurant class is a simple class that deals with initializing
 * the BoundedBuffer, the Agent thread, and all three Chef threads with
 * their own ingredients. Lastly, the class starts all the threads.
 * @author Gilles Myny
 * @id 101145477
 */

public class Restaurant {
	public volatile static int sandwichCount = 0;
	
	public static void main(String[] args) {
		BoundedBuffer ingTable = new BoundedBuffer();
		
		Thread agent = new Thread(new Agent("Agent", ingTable, "bread", "ham", "cheese"), "Agent");
		Thread chefOne = new Thread(new Chef("Chef 1", ingTable, "cheese"), "Chef 1");
		Thread chefTwo = new Thread(new Chef("Chef 2", ingTable, "ham"), "Chef 2");
		Thread chefThree = new Thread(new Chef("Chef 3", ingTable, "bread"), "Chef 3");
		
		agent.start();
		chefOne.start();
		chefTwo.start();
		chefThree.start();
	}
}
