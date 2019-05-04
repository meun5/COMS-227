package mini3;

import java.util.Arrays;
import java.util.stream.Collectors;

public class Execute {
	public static void main(String[] args) {
		int[][] o = Combinations.getCombinations(new int[] {
			1,
			2,
			3,
		});
		
		System.out.println(Arrays
        .stream(o)
        .map(Arrays::toString) 
        .collect(Collectors.joining(System.lineSeparator())));
		
		for (int[] a : o) {
			System.out.println(Arrays.toString(a));
		}
	}
}
