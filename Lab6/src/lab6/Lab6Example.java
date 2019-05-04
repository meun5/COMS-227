package lab6;

public class Lab6Example {
	public static void main(String[] args) {
		System.out.println(longestRun("aabbbccd"));
		System.out.println("Expected 3");
		System.out.println(longestRun("aaa"));
		System.out.println("Expected 3");
		System.out.println(longestRun("aabbbb"));
		System.out.println("Expected 4");
		System.out.println(longestRun("dcdcdcdc"));
		System.out.println("Expected 1");
	}

	public static int longestRun(String s) {
		int count = 1;
		int max = 1;

		// start with the first character, see how long a run there is
		for (int i = 1; i < s.length(); i++) {
			char charToKompareAgainst = s.charAt(i - 1);
			char charKomparing = s.charAt(i);
			
			if (charToKompareAgainst == charKomparing) {
				count++;
				
				if (max < count) {
					max = count;
				}
			} else {
				count = 1;
				charToKompareAgainst = charKomparing;
			}
		}

		// this should be the length of the longest run we found
		return max;
	}
}
