package codingInterview;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class InterviewQuestionsOne {

	public static void main (String[] args){
		
		
		String test1 = "asfwl;eifalscwaeriasdf";
		String test2 = "1234qwerasdf0987poiu;lkj.,mn";
		String test3 = "sadfASDFwoiW91238$%^";
		
		/*
		System.out.println("Testing isUnique");
		isUnique(test1);
		isUnique(test2);
		isUnique(test3);
		System.out.println("Done with test.");
		
		System.out.println("Testing isPermutation");
		test1 = "cow";
		test2 = "coradfjcwolasdf";
		test3 = "ascijwoiedoinagda";
		
		containsPermutation(test1, test2);
		containsPermutation(test1, test3);
		
		test2 = "wco";
		isPermutation(test1, test2);
		System.out.println("Done with test.");
		
		System.out.println("Testing URLify");
		test1 = "Test This String.    ";
		test2 = "http:// www. asdf  li        ";
		test3 = "Space Space Space       ";
		
		URLify(test1.toCharArray(), test1.length());
		URLify(test2.toCharArray(), test2.length());
		URLify(test3.toCharArray(), test3.length());
		System.out.println("Done with test.");
		
		
		
		System.out.println("Testing isPalindromePermutation");
		test1 = "Tact Coa";
		test2 = "lifeeifz";
		test3 = "cacccac";
		
		isPalindromePermutation(test1);
		isPalindromePermutation(test2);
		isPalindromePermutation(test3);
		System.out.println("Done with test.");
		
		System.out.println("Testing oneMoveAway");
		test1 = "pale";
		test2 = "pales";
		test3 = "baies";
		
		oneMoveAway(test1, test2);
		oneMoveAway(test2, test3);
		oneMoveAway(test1, test3);
		System.out.println("Done with test.");
		
		
		System.out.println("Testing stringCompression");
		test1 = "aaaaabbbbbssssdfekccccaaaaa";
		test2 = "mmmmssssnniujvklll";
		test3 = "sdfqwerpoiu";
		
		stringCompression(test1);
		stringCompression(test2);
		stringCompression(test3);
		System.out.println("Done with test.");
		
		System.out.println("Testing rotateMatrix");
		
		int[][] matrixA = intializeMatrix(5);
		int [][] matrixB = intializeMatrix(10);
		
		rotateMatrix(matrixA);
		rotateMatrix(matrixB);
		
		System.out.println("Done with test.");
		*/
		System.out.println("Testing zeroMatrix");
		
		int[][] matrixA = intializeMatrix(5, true);
		int[][] matrixB = intializeMatrix(5,6, true);
		
		zeroMatrix(matrixA);
		zeroMatrix(matrixB);
		
		System.out.println("Done with test.");
	}
	
	public static void zeroMatrix(int [][] matrix){
		if (matrix == null || matrix.length < 1) {
			return;
		}
		
		System.out.println("Before: ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("[");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf(" %d ", matrix[i][j]);
			}
			System.out.println("]");
		}
		
		// Trackers for zeroed-out rows/cols
		int [] rowTracker = new int[matrix.length];
		int [] colTracker = new int[matrix[0].length];
		
		// for through matrix
		for (int i = 0; i < matrix.length; i++){
			for (int j = 0; j < matrix[i].length; j++){
				if (matrix[i][j] == 0){
					// Check if this 0 is from a previous setting to zero action
					if (rowTracker[i] <= 0 && colTracker[j] <= 0){
						rowTracker[i]++;
						colTracker[j]++;
						
						/*System.out.printf("Row Tracker: ");
						for (int x = 0; x < rowTracker.length; x++)
							System.out.printf("%d ",rowTracker[x]);
						
						System.out.printf("Col Tracker: ");
						for (int x = 0; x < rowTracker.length; x++)
							System.out.printf("%d ",colTracker[x]);
						
						System.out.println();
						*/
						for (int clearRow = 0; clearRow < matrix.length; clearRow++){
							matrix[clearRow][j] = 0;
						}
						
						for (int clearCol = 0; clearCol < matrix[i].length; clearCol++){
							matrix[i][clearCol] = 0;
						}
					}
				}
			}
		}
		
		System.out.println("After: ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("[");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.printf(" %d ", matrix[i][j]);
			}
			System.out.println("]");
		}
	}
	
	public static int[][] intializeMatrix(int rowSize, int colSize) {
		int [][] matrix = new int [rowSize][colSize];
		
		for (int i = 0; i < rowSize; i++){
			for (int j = 0; j < colSize; j++)
				matrix[i][j] = (int)Math.ceil(Math.random() * 100);
		}
		return matrix;
	}
	
	public static int[][] intializeMatrix(int size) {
		return intializeMatrix(size, size);
	}
	
	public static int[][] intializeMatrix(int size, boolean withZeroes){
		return intializeMatrix(size, size, withZeroes);
	}

	public static int[][] intializeMatrix(int rowSize, int colSize, boolean withZeroes) {
		int [][] matrix = intializeMatrix(rowSize, colSize);
		
		if (withZeroes) {
			int numberOfZeroes = (int)Math.ceil(Math.random() * rowSize);
			for (int i = 0; i < numberOfZeroes; i++){
				// Randomly insert zeroes
				matrix[(int)Math.ceil(Math.random() * 100)%rowSize][(int)Math.ceil(Math.random() * 100)%colSize] = 0;
			}
		}
		
		return matrix;
	}
	public static void rotateMatrix(int [][] matrix){
		if (matrix == null || matrix.length < 2){
			return;
		}
		
		System.out.println("Before: ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("[");
			for (int j = 0; j < matrix.length; j++) {
				System.out.printf(" %d ", matrix[i][j]);
			}
			System.out.println("]");
		}
		
		int rowLength = matrix.length - 1;
		// Go index by index
		for (int i = 0; i < (matrix.length/2); i++) {
			for (int j = i; j < rowLength - i; j++){
				int temp = matrix[i][j];	// Save original value from top row
				// Shift everything over
				// Top index: matrix[i][j]
				// Left index: matrix[rowLength - j][i]
				// Bottom index: matrix[rowLength - i][rowLength - j]
				// Right index: matrix[j][rowLength - i]
				matrix[i][j] = matrix[rowLength - j][i]; // Top row
				matrix[rowLength - j][i] = matrix[rowLength - i][rowLength - j]; // Left row
				matrix[rowLength - i][rowLength - j] = matrix[j][rowLength - i]; // Bottom row
				matrix[j][rowLength - i] = temp; // Right row
			}
		}
		
		System.out.println("After: ");
		for (int i = 0; i < matrix.length; i++) {
			System.out.printf("[");
			for (int j = 0; j < matrix.length; j++) {
				System.out.printf(" %d ", matrix[i][j]);
			}
			System.out.println("]");
		}
	}
	
	public static void stringCompression(String input){
		if (input == null || input.isEmpty())
			return;
		
		// Add function to check compression count
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < input.length(); i++){
			char thisChar = input.charAt(i);
			int counts = 1;
			while (i + 1 < input.length() && input.charAt(i + 1) == thisChar){
				counts++;
				i++;
			}
			builder.append(thisChar).append(counts);
		}
		
		System.out.println("Compressed string: " + builder.toString());
		
	}
	
	public static void oneMoveAway(String test1, String test2){
		// Check inputs
		if (test1 == null || test2 == null) return;
		
		// Check lengths: if |test1.length() - test2.length()| > 1 ==> more than 1 edit away	
		if (Math.abs(test1.length() - test2.length()) > 1){
			System.out.println(test1 + " and " + test2 + " are MORE than 1 edit away.");
			return;
		}
		
		// From beginning check that each character matches
		if (test1.length() < test2.length()){
			oneInsertionRemoval(test1, test2);
		}
		else if (test1.length() > test2.length()) {
			oneInsertionRemoval(test2, test1);
		}
		else
			oneReplace(test1, test2);
	}
	
	public static void oneInsertionRemoval(String smaller, String longer){
		boolean edited = false;
		
		for (int sIndex = 0, lIndex = 0; sIndex < smaller.length() && lIndex < longer.length(); sIndex++, lIndex++){
			if (smaller.charAt(sIndex) != longer.charAt(lIndex)){
				// Check if this is the first edit
				if (!edited) {
					// Note this edit
					edited = true;
					
					// Check if the next index in smaller == this one in longer (insertion in 1/remove in 2)
					if (smaller.charAt(sIndex + 1) == longer.charAt(lIndex)){
						// Move the sIndex up one spot
						sIndex++;
						continue;
					}
					
					// Check if the index in smaller matches the next index in longer (remove in 1/insertion in 2)
					if (smaller.charAt(sIndex) == longer.charAt(lIndex + 1)){
						lIndex++;
						continue;
					}
					
					else {
						System.out.println(smaller + " and " + longer + " are MORE than 1 edit away.");
						return;
					}
				}
				else {
					System.out.println(smaller + " and " + longer + " are MORE than 1 edit away.");
					return;
				}
			}
		}
		
		// Gone through the entire chain, there's still 1 left on the longer chain
		// Neither arrays have been edited yet, then the 1 extra is an allowable edit
		if (!edited){
			System.out.println(smaller + " and " + longer + " are 1 or fewer edits away.");
		}
		else {
			System.out.println(smaller + " and " + longer + " are MORE than 1 edit away.");
		}
	}
	
	public static void oneReplace(String test1, String test2){
		boolean edited = false;
		for (int i = 0; i < test1.length(); i++){
			if (test1.charAt(i) != test2.charAt(i)){
				if (!edited){
					edited = true;
				}
				else {
					System.out.println(test1 + " and " + test2 + " are MORE than 1 edit away.");
					return;
				}
			}
		}
		
		System.out.println(test1 + " and " + test2 + " are 1 or fewer edits away.");
	}
	
	public static void isPalindromePermutation(String inputRaw){
		if (inputRaw.isEmpty()) return;
		
		// Make all characters lower case
		final String input = inputRaw.toLowerCase(Locale.US);
		
		// Put all characters into the HashMap
		Map<Character, Integer> counts = new HashMap<>();
		for (int i = 0; i < input.length(); i++){
			// Skip spaces
			if (input.charAt(i) == ' ') {
				break;
			}
			
			if (counts.get(input.charAt(i)) == null){
				counts.put(input.charAt(i), 1);
			}
			else {
				int ccounts = counts.get(input.charAt(i));
				ccounts++;
				counts.put(input.charAt(i), ccounts);
			}
		}
		
		// Check if string length is even or odd
		final boolean odd;
		if (input.length()%2 != 0)
			odd = true; // Used as a flag to allow for 1 character to not have a matching pair
		else
			odd = false;
		
		// Check that all entries have a matching pair, allowing for 1 odd char for odd lengths
		counts.entrySet().stream().forEach(e -> {
			int chances = 0;
			if (odd) {
				chances = 1;
			}
			if (e.getValue()%2 != 0){
				if (odd && chances > 0){
					// Allow for 1 unmatched pair
					chances = 0;
				}
				else {
					System.out.println(input + " is not a palindome in any permutation");
					return;
				}
			}
		});
		
		// Gone through all entries and prerequisites have been met
		System.out.println(input + " IS a palindome in any permutation!");
		
	}
	
	public static void URLify(char [] input, int size){
		if (input == null || size < 3)
			// If size < 3, there's no space for a space to be transformed, assuming correct size provided
			return;
		
		System.out.println("Beginning string: " + new String(input));
		int letterTracker = size - 1, spaceTracker = size - 1;
		
		// Find the first white space, if any, to replace
		while (letterTracker >= 0 && input[letterTracker] == ' '){
			letterTracker--;
		}
		
		// Check if there were no white spaces
		if (letterTracker < 0 | letterTracker == size - 1)
			return;
		
		while (spaceTracker != letterTracker && letterTracker >= 0){
			if (input[letterTracker] != ' '){
				// Swap letters
				input[spaceTracker] = input[letterTracker];
				
				spaceTracker--;
				letterTracker--;
			}
			else {
				// Found a space, insert "%20"
				input[spaceTracker--] = '0';
				input[spaceTracker--] = '2';
				input[spaceTracker--] = '%';
				letterTracker--;
			}
		}
		
		System.out.println("Ending string: " + new String(input));
	}
	
	public static void isUnique(String input){
		if (input.isEmpty()) return;
		
		for (int i = 0; i < input.length(); i++){
			for (int j = i + 1; j < input.length(); j++) {
				if (input.charAt(i) == input.charAt(j)){
					System.out.println("NO");
					return;
				}
			}
		}
		
		// No repeated characters found
		System.out.println("YES");
	}
	
	public static void containsPermutation(String test1, String test2){
		if (test1.length() < test2.length())
			if (isPermutationWork(test1, test2))
				System.out.println(test2 + " contains a permunation of " + test1);
			else 
				System.out.println(test2 + " does NOT contain a permunation of " + test1);
		else
			if (isPermutationWork(test2, test1))
				System.out.println(test1 + " contains a permunation of " + test2);
			else 
				System.out.println(test1 + " does NOT contain a permunation of " + test2);
	}
	
	public static boolean isPermutationWork(String smaller, String larger){
		for (int i = 0; i < smaller.length(); i++){
			for (int j = 0; j < larger.length(); j++){
				if (smaller.charAt(i) == larger.charAt(j)){
					// Check the sliding window, if there's room left
					if (j + smaller.length() < larger.length()){
						if (checkWindow(smaller, larger.substring(j, j + smaller.length())))
							return true;
					}
				}
			}
		}
		return false;
	}
	
	static String sort(String input){
		char [] temp = input.toCharArray();
		java.util.Arrays.sort(temp);
		return new String(temp);
	}
	
	public static void isPermutation(String test1, String test2){
		if (test1.length() == test2.length()){
			String sortedtest1 = sort(test1);
			String sortedtest2 = sort(test2);
			if (sortedtest1.equals(sortedtest2)) {
				System.out.println(test1 + " is a permutation of " + test2);
				return;
			}
		}
		
		System.out.println(test1 + " is not a permutation of " + test2);
	}
	
	public static boolean checkWindow(String smaller, String larger){
		for (int i = 0; i < smaller.length(); i++){
			boolean found = false;
			for (int j = 0; j < larger.length(); j++){
				if (smaller.charAt(i) == larger.charAt(j)){
					found = true;
				}
			}
			
			if (!found)
				return false;
		}
		return true;
	}
}
