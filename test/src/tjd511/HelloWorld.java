package tjd511;

/**
 * Documentation comment - can be extracted to HTML files using the javadoc tool
 */
class HelloWorld {
	/*
	 * This block comment should contain any class-wide or interface-wide information
	 * that wasn’t appropriate for the class/interface documentation comment.
	 * 
	 * If you need to give information about a class, interface, variable, or
	 * method that isn’t appropriate for documentation, use an implementation
	 * block comment or single-line comment immediately after the declaration
	 * (like this).
	 */

	static int numberOfGreenBottles; // One declaration per line
	int numberOfFingersOnOneHand; // Small comment if required
	
	/* Block comment to describe method. */
	public static void main(String[] args) {
		int n = 0; // Initialise where declared if possible
		System.out.println("Hello World!");
 
		n++; // Don't group these
		
		if (n == 1) {
			sing();
		} else if (n == 2) {
			System.out.println("Hello World!");
		} else {
			System.out.println("Hello World!");
		}
	}

	/*
	* Longer block comment to describe method.
	* method sing() sings a song!
	*/
	protected static void sing() {
		for (numberOfGreenBottles = 10; numberOfGreenBottles > 0; numberOfGreenBottles--) {
			System.out.printf("\n%d green bottles hanging on the wall,\n",
					numberOfGreenBottles);
			System.out.printf("%d green bottles hanging on the wall,\n",
					numberOfGreenBottles);
			System.out
					.printf("And if one green bottle should accidentally fall,\n");
			System.out.printf(
					"There'll be %d green bottles hanging on the wall.\n", 
					(numberOfGreenBottles-1));
		}
	}
}
