package lab6;

import java.lang.Character;;

public final class StringFunctionality {
	public static String str_name(String name) {
		StringBuilder initals = new StringBuilder();
		
		for (String part : name.split(" ")) {
			initals.append(part.charAt(0));
		}
		
		return initals.toString();
	}
	
	public static int getFirstVowelPosition(String word) {
		for (int i = 0; i < word.length(); i++) {
			if (
				"aeiou".indexOf(
					Character.toLowerCase(word.charAt(i))
				) >= 0
			) {
				return i;
			}
		}
		
		return -1;
	}
}
