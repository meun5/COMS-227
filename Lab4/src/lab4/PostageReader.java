package lab4;

public class PostageReader {
	public static void main(String[] args) {
		java.util.Scanner scanner = new java.util.Scanner(System.in);
		
		System.out.print("Enter the weight of thine package: ");
		System.out.println(
			String.format(
				"The calculated cost of shipping is: %1.2f", 
				postage1.PostageUtil.computePostage(scanner.nextDouble())
			)
		);
		
		scanner.close();
	}
}
