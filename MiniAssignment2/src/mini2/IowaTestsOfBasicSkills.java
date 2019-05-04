package mini2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class IowaTestsOfBasicSkills {
	public static void main(String[] args) {
		CS227Comp z = new CS227Comp(100);

		int[] a = { 6001, 3021, 5123, 6319, 3023, 5022, 4022, 3021, 5324, 6315, 3025, 5421, 5023, 4021, 6002, 3021,
				5224, 4021, 6002, 2022, 8000, 100, 0, 1, 2, 3 };

		ByteArrayOutputStream baosTheirs = new ByteArrayOutputStream();
		PrintStream outTheirs = new PrintStream(baosTheirs);
		PrintStream savedOut = System.out;
//		
		System.setOut(outTheirs);
		z.loadMemoryImage(a);
		z.runProgram();
//		z.runProgram();
//		System.out.println(z.peekMemory(22));

		System.setOut(savedOut);

		checkOutput("+0025", baosTheirs);

		System.out.println("\nDone");
	}

	private static void checkOutput(String desiredResult, ByteArrayOutputStream baosTheirs) {
		Scanner scan = new Scanner(new ByteArrayInputStream(baosTheirs.toByteArray()));
		String actual = scan.next();

		System.out.printf("%s | %s & %b\n", actual, desiredResult, desiredResult.equals(actual));
		if (!desiredResult.equals(actual)) {
			System.out
					.println("Expected output " + desiredResult + ", but first item in output was \"" + actual + "\".");
		} else {
			System.out.println("Output as expected.");
		}
		scan.close();
	}
}
