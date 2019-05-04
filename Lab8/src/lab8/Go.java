package lab8;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import plotter.Plotter;
import plotter.Polyline;

public class Go {
	public static void main(String args[]) throws IllegalAccessException,Exception {
//		File f = new File("../Lab7/src/lab7/Deck.java");
//	
//		Scanner s = new Scanner(f);
//		
//		for (int z = 0; s.hasNextLine(); z++) {
//			String e = s.nextLine();
//			System.out.printf("%d %s\n", z, e);
//		}
//		
//		s.close();
//		
//		System.out.println("DoNe\n\n");
//		s();

		//NINE OF SPADES
//		ArrayList<String> e = new ArrayList<String>() {/**
//			 * 
//			 */
//			private static final long serialVersionUID = 1L;
//
//		{
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//			add("NORT");
//			add("SOUT");
//			add("EAST");
//			add("WEAST");
//		}};
//		
//		System.out.println(Arrays.toString(e.toArray()));
//		
//		e = e(e);
//		
//		System.out.println(Arrays.toString(e.toArray()));
		
		z();
	}
	
	public static void s() throws Exception {
		File f = new File("src/story.txt");
		
		Scanner s = new Scanner(f);
		
		for (int l = 0; s.hasNextLine(); l++) {
			System.out.printf("For Line %d, there are %d word(s)\n", l, s.nextLine().split(" ").length);
		}
	}
	
	public static ArrayList<String> e(ArrayList<String> e) {
		ArrayList<String> w = new ArrayList<String>();
		
		for (String q : e) {
			if (!w.contains(q)) {
				w.add(q);
			}
		}
		
		return w;
	}
	
	public static void z() throws Exception {
		Plotter n = new Plotter();
		
		try (Stream<String> stream = Files.lines(Paths.get("src/hello.txt"))) {
			stream.forEach(e -> {
				if (e.trim().isEmpty()) {
					return;
				}
				
				if (e.trim().startsWith("#")) {
					return;
				}
				
				System.out.println(e);
				
				n.plot(parseNoLines(e));
			});
	    } catch (IOException e) {
			throw new FileNotFoundException();
		}
	}
	
	public static Polyline parseNoLines(String f) {
		Scanner e = new Scanner(f);
		
		int bitWidth = 0;
		if (f.matches("^[0-9]+ +[a-z]?.*")) {
			bitWidth = e.nextInt();
			System.out.printf("BitWidth: %d\n", bitWidth);
		}
		
		final String colour = e.next();
		System.out.printf("Colour: %s\n", colour);
		Polyline d = bitWidth == 0 ? new Polyline(colour) : new Polyline(colour, bitWidth);
		
		while (e.hasNextInt()) {
//			System.out.println("E");
			d.addPoint(new Point(e.nextInt(), e.nextInt()));
		}
		
		e.close();
		System.out.println(d.getColor());
		return d;
	}
}
