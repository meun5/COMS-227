package mini1;

import java.util.*;

public class FromLoopToNuts {
	private FromLoopToNuts() {}

	public static int countMatches(
		String a,
		String b
	) {
		int amountOfSubStringsThatMatch = 0;
		
		for (int k = 0; k < a.length() && k < b.length(); k++) {
			if (a.charAt(k) == b.charAt(k)) {
				amountOfSubStringsThatMatch++;
			}
		}
		
		return amountOfSubStringsThatMatch;
	}
	
	public static int countSubstrings(
		String a,
		String b
	) {
		return b.split(a, -1).length - 1;
	}
	
	public static String eliminateRuns(
		String s
	) {
		StringBuilder k = new StringBuilder(s);
		
		for (int z = 1; z < s.length(); z++) {
			if (s.charAt(z) == s.charAt(z - 1)) {
				k.setCharAt(z, '\127');
			}
		}
		
		return k.toString().replaceAll("\127", "");
	}
	
	public static boolean isArithmetic(
		String text
	) {
		if (text.isEmpty()) {
			return true;
		}
		
		if (!text.contains(",") && !text.matches("-?[0-9]")) {
			return false;
		}
		
		if (text.matches(".*,?[a-zA-Z]")) {
			return false;
		}
		
		Scanner s = new Scanner(text);
		s.useDelimiter(",");
		
		List<Integer> a = new ArrayList<Integer>();
		
		while (s.hasNextInt()) {
			a.add(s.nextInt());
		}
		
		s.close();

		if (a.size() <= 2) {
			return true;
		}
		
		int initialDifference = a.get(1) - a.get(0);
		
		for (int z = 2; z < a.size(); z++) {
			if (a.get(z) - a.get(z - 1) != initialDifference) {
				return false;
			}
		}
		
		return true;
	}
	
	public static int threeInARow(Random r, int a) {
		List<Integer> list = new ArrayList<Integer>();
		
		list.add(r.nextInt(a));
		list.add(r.nextInt(a));
		list.add(r.nextInt(a));
		
		for (int l = 2; true; l++) {
			int c = list.get(l);
			if ((c == list.get(l - 1)) && (c == list.get(l - 2))) {
				return l + 1;
			}
			
			list.add(r.nextInt(a));
		}
	}
	
	public static int findEscapeCount(
		double x,
        double y,
        int maxIterations
    ) {
		double a = 0, b = 0;
		double _a, _b;
		
		for (int i = 0; i < maxIterations; i++) {
			if ((a * a + b * b) > 4) {
				return i;
			}
			
			_a = a * a - b * b + x;
			_b = 2 * a * b + y;
			a= _a;
			b=_b;
		}
		
		return maxIterations;
	}
	
	public static boolean differByOneSwap(
		String s,
		String t
	) {
		if (s.length() == 1) {
			return s != t;
		}
		
		if (s.length() - t.length() <= 3 && s.length() - t.length() >= 1) {
			return false;
		}
		
		int wrongCount = 0;
		
		for (int i = 1; i < s.length() && i < t.length(); i++) {
			if (s.charAt(i) != t.charAt(i) && (t.charAt(i) == s.charAt(i - 1) || t.charAt(i) == s.charAt(i + 1))) {
				wrongCount++;
			}
		}
		
		return wrongCount == 1 || wrongCount == 2;
	}
	
	public static int countSubstringsWithOverlap(
		String t,
		String s
	) {
		if (t.length() > s.length()) {
			return 0;
		}
		
		int bitLenght = t.length();
		
		int count = 0;
		
		for (int i = 0; i + bitLenght <= s.length(); i++) {
			if (s.substring(i, i + bitLenght).compareTo(t) == 0) {
				count++;
			}
		}
		
		return count;
	}
	
//	public static long countMatches(
//		String needle,
//		String haystack
//	) {
//		return needle.chars().filter(ch -> {
//			boolean x = haystack.contains(Character.toString((char) ch));
//			return x;
//		}).count();
//	}
}
