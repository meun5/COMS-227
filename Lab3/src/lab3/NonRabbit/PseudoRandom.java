package lab3.NonRabbit;

import java.util.*;

public class PseudoRandom {
	public static void main(String[] args) {
		Random r = new Random();
		
		System.out.println(r.nextInt(6));
		System.out.println(r.nextInt(6));
		System.out.println(r.nextInt(6));
		System.out.println(r.nextInt(6));
		
		r = new Random(137);
		
		System.out.println(r.nextInt(6));
		System.out.println(r.nextInt(6));
		System.out.println(r.nextInt(6));
		System.out.println(r.nextInt(6));
	}
}
