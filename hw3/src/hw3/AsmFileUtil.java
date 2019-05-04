package hw3;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 
 * @author Alexander Young
 *
 */
public class AsmFileUtil {
	/**
	 * Reads the given file and assembles the program, writing the machine code to a file.
	 * Descriptions are included if the annotated parameter is true, else each line in the output file includes the integer value of the instruction.
	 * The name of the output file is the same as the name of the input file,
	 * with the file extension removed and is replaced with ".mach227".
	 * 
	 * @param filename						The file path to read from
	 * @param annotated						Whether to include annotations
	 * 
	 * @throws FileNotFoundException		If the file cannot be found
	 */
	public static void assembleAndWriteFile(String filename, boolean annotated) throws FileNotFoundException {
		byte[] write;
		
		if (!annotated) {
			// If annotated output is not wanted, drop it.
			write = assembleFromFile(filename).stream().map(s -> {
				return s.split(" ")[0];
			}).collect(Collectors.joining("\n")).getBytes();
		} else {
			// Get the byte array of the lines separated by newlines.
			write = assembleFromFile(filename).stream().collect(Collectors.joining("\n")).getBytes();
		}
		
		try {
			// Here we are using the nio (new io) library, as I like it better.
			// It creates the file if it does not exist, or truncates it if it does.
			Files.write(Paths.get(filename.substring(0, filename.lastIndexOf('.')) + ".mach227"), write, new OpenOption[] {
				StandardOpenOption.CREATE,
				StandardOpenOption.WRITE,
				StandardOpenOption.TRUNCATE_EXISTING
			});
		} catch (IOException e) {
			// ReThrow the Exception under the older FileNotFoundException from the old io library.
			throw new FileNotFoundException(e.getMessage());
		}
	}
	
	/**
	 * Reads the given file and assembles the program, returning the machine code as a list of strings (including descriptions).
	 * 
	 * @param filename						The file path to read from
	 * 
	 * @return								An ArrayList of the machine code including descriptions		
	 * 
	 * @throws FileNotFoundException		If the file cannot be found
	 */
	public static ArrayList<String> assembleFromFile(String filename) throws FileNotFoundException {
		ArrayList<String> input = readFile(filename);

		return new CS227Asm(input).assemble();
	}

	/**
	 * Reads the given file and assembles the program, returning the machine code as an array of integer values (excluding the sentinel value).
	 * 
	 * @param filename						The file path to read from
	 * 
	 * @return								An integer array of raw machine code
	 * 
	 * @throws FileNotFoundException		If the file cannot be found
	 */
	public static int[] createMemoryImageFromFile(String filename) throws FileNotFoundException {
		ArrayList<String> input = readFile(filename);
		
		// Assemble the output, then map all the values to an int, then drop the sentinel value.
		return new CS227Asm(input).assemble().stream().mapToInt(i -> {
			// Get first part, then delete the '+' sign, then parse as int.
			return Integer.parseInt(new StringBuilder(i.split(" ")[0]).deleteCharAt(0).toString());
		}).filter(i -> i != 99999).toArray();
	}
	
	/**
	 * A private method that actually reads from the specified file and returns an ArrayList with the contained lines.
	 * 
	 * @param filename						The file to read from
	 * 
	 * @return								The lines from the file
	 * 
	 * @throws FileNotFoundException		If the file cannot be found
	 */
	private static ArrayList<String> readFile(String filename) throws FileNotFoundException {
		ArrayList<String> input = new ArrayList<String>();

		// Get a stream of the lines of the file, then drop comments if there are any.
		try (Stream<String> stream = Files.lines(Paths.get(filename))) {
			stream.forEach(l -> {
				l.trim(); // Clean whitespace

				String[] parts = l.split(" "); // Split it into its parts

				// If the line has two or more parts, grab the first two.
				if (parts.length >= 2) {
					input.add(String.join(" ", parts[0], parts[1]));
				}

				// Else add just the first part.
				if (parts.length == 1) {
					input.add(parts[0]);
				}
			});
		} catch (IOException e) {
			// ReThrow the Exception under the older FileNotFoundException from the old io library.
			throw new FileNotFoundException(e.getMessage());
		}
		
		return input;
	}
}
