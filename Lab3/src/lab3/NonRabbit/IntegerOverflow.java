package lab3.NonRabbit;

public class IntegerOverflow {
	public static void main(String[] args) {
		System.out.println(Integer.MAX_VALUE);
		System.out.println(Integer.MIN_VALUE);
		
		System.out.println(Integer.MAX_VALUE + 1);
		System.out.println(Integer.MAX_VALUE + 2);
		
		System.out.println(Integer.MAX_VALUE + Integer.MIN_VALUE);
	}
}
