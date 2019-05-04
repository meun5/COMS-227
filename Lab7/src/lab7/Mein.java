package lab7;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Mein {

	@SuppressWarnings("serial")
	public static void main(String args[]) {
		ArrayList<Integer> e = new ArrayList<Integer>() {{
			add(1);
			add(2);
			add(-9);
			add(4);
			add(-3);
			add(-123);
		}};
		
		System.out.println(
			Arrays.toString(
				getPositiveNumbers(e.stream().mapToInt(i -> i).toArray()
			)
		));
		
		System.out.println(
			Arrays.toString(randothExpe(10, 1000))	
		);
		
		System.out.println(
			Card.toString(
				new Deck().select(5)
			)
		);
	}
	
	public static int[] getPositiveNumbers(int[] numbers) {
		ArrayList<Integer> r = new ArrayList<Integer>();
	
		for (int k : numbers) {
			if (k > 0) {
				r.add(k);
			}
		}
		
		return r.stream().mapToInt(i -> i).toArray();
	}
	
	
	public static int[] randothExpe(int max, int iters) {
		Random z = new SecureRandom();
		ArrayList<Integer> n = new ArrayList<Integer>();
		int[] c = new int[max];
		
		for (int i = 0; i < iters; i++) {
			n.add(
				z.nextInt(max - 1)
			);
		}
		
		for (int t : n) {
			c[t]++;
		}
		
		return c;
	}
}
