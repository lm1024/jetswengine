/**
 * Documentation comment - can be extracted to HTML files using the javadoc tool
 */
class TestClass {
	/*
	 * This block comment should contain any class-wide or interface-wide information
	 * that wasn’t appropriate for the class/interface documentation comment.
	 * 
	 * If you need to give information about a class, interface, variable, or
	 * method that isn’t appropriate for documentation, use an implementation
	 * block comment or single-line comment immediately after the declaration
	 * (like this).
	 */
	
	/* All numbers that are not -1, 0 or 1 declared here as statics */
	private static final int MAX_NUMBER_OF_GREEN_BOTTLES = 10;
	
	static int numberOfGreenBottles; // One declaration per line
	
	int numberOfFingersOnOneHand; // Small comment if required
	
	/* Single line block comment to describe method. */
	public static void main(String[] args) {
		int n = 0; // Initialise where declared if possible
 
		n++; 
		
		if (n == 1) {
			sing();
		} else if (n == 2) {
			System.out.println("N is two.");
		} else {
			System.out.println("hello mate.");
		}
	}

	/*
	* Longer block comment to describe method.
	* method sing() sings a song!
	*/
	protected static void sing() {
		/* 
		 * Loops through and sings all the verses of the song 
		 * Code over the 80 char limit can be formatted using CTRL + SHIFT + F
		 * and it always looks horrible.
		 */
		for (numberOfGreenBottles = MAX_NUMBER_OF_GREEN_BOTTLES; numberOfGreenBottles > 0; numberOfGreenBottles--) {
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
