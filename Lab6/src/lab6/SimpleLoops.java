package lab6;

/**
 * An example with some buggy loops.
 */
public class SimpleLoops {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int count = countP("Mississippi");
		System.out.println(count);

		int result = findLastP("Mississippi");
		System.out.println(result);

		int result1 = findFirstP("stop");
		System.out.println(result1);

		int result2 = findFirstP("xxxyyyzzz");
		System.out.println(result2);
	}

	/**
	 * Returns the number of P's in a string.
	 * 
	 * @param s the string to examine
	 * @return number of P's in s
	 */
	private static int countP(String s) {
		int count = 0;
		int i = 0;
		
		while (i < s.length()) {
			if (isLetterP(s.charAt(i))) {
				count++;
			}
			
			i++;
		}
		
		return count;
	}

	/**
	 * Returns the index of the last P in a string, or -1 if the string contains no
	 * P's.
	 * 
	 * @param s the string to examine
	 * @return index of the last P, or -1
	 */
	private static int findLastP(String s) {
		return s.toLowerCase().lastIndexOf('p');
	}

	/**
	 * Returns the index of the first P in a string, or -1 if the string contains no
	 * P's.
	 * 
	 * @param s the string to examine
	 * @return index of the first vowel, or -1
	 */
	private static int findFirstP(String s) {
		return s.toLowerCase().indexOf('p');
	}

	/**
	 * Returns true if the given character is the letter "P" (lowercase or
	 * uppercase), false otherwise the character to check
	 * 
	 * @return true if the given character is a "P", false otherwise
	 */
	private static boolean isLetterP(char ch) {
		return ch == 'p' || ch == 'P';
	}
}