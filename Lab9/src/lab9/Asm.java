package lab9;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;

public class Asm {
	public static void main(String[] args) throws IOException {
//		frahafer();
//		System.out.println("\n\n");
//		seinhafer();
		
		System.out.println(recursiveFileCount(new File("U:\\Coms227\\MiniAssignment1")));
		System.out.println(recursiveFileFile(new File("U:\\Coms227\\MiniAssignment1")));
		strufer();
	}
	
	public static void frahafer() {
		System.out.printf("\n%d", nein(new int[] {1, 5, 13, 35, 10000, 123, 1, 23, 1, 123123, 29, 12, 2}));
	}
	
	public static int nein(int[] k) {
		System.out.printf("%s, %d\n", Arrays.toString(k), k.length);
		
		if (k.length == 0) {
			return 0;
		}
		
		if (k.length == 1) {
			return k[0];
		}
		
		return Math.max(nein(Arrays.copyOfRange(k, 0, k.length / 2)), nein(Arrays.copyOfRange(k, (k.length / 2), k.length)));
	}
	
	public static void seinhafer() {
		System.out.println(kleinstuper(2));
	}
	
	public static int kleinstuper(int l) {
		if (l == 0) {
			return 0;
		}
		
		if (l == 1) {
			return 1;
		}
		
		return l * l + kleinstuper(l - 1);
	}
	
	public static int recursiveFileCount(File f) throws IOException {
		return (int) Files.walk(f.toPath()).parallel().filter(p -> !p.toFile().isDirectory()).count();
	}
	
	public static ArrayList<String> recursiveFileFile(File f) throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		
		Files.walk(f.toPath()).parallel().filter(p -> !p.toFile().isDirectory()).forEach(o -> files.add(o.toFile().getName()));
		
		return files;
	}
	
	public static void strufer() {
		System.out.println(countPattersons(5));
	}
	
	public static int countPattersons(int n) {
		if (n < 3) {
			return 1;
		}
		
		if (n == 3) {
			return 2;
		}
		
		
		return 1 + countPattersons(n - 1);
		
	}
}
