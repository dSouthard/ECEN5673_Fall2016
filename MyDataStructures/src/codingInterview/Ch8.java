package codingInterview;

public class Ch8 {

	public static void main (String [] args) {
		
		int x = recursiveMultiply(2,0);
		System.out.println(x);
	}
	
	public static int recursiveMultiply(int a, int b){
		if (a == 0 || b == 0){
			return 0;
		}
		
		return a + recursiveMultiply(a, b-1);
	}
}
