package mini2;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Stream;

public class CS227Comp {
	/**
	 * Opcode for the read instruction.
	 */
	public static final int READ = 10;

	/**
	 * Opcode for the write instruction.
	 */
	public static final int WRITE = 20;

	/**
	 * Opcode for the load instruction.
	 */
	public static final int LOAD = 30;

	/**
	 * Opcode for the store instruction.
	 */
	public static final int STORE = 40;

	/**
	 * Opcode for the add instruction.
	 */
	public static final int ADD = 50;

	/**
	 * Opcode for the sub instruction.0
	 */
	public static final int SUB = 51;

	/**
	 * Opcode for the div instruction.
	 */
	public static final int DIV = 52;

	/**
	 * Opcode for the mod instruction.
	 */
	public static final int MOD = 53;

	/**
	 * Opcode for the mul instruction.
	 */
	public static final int MUL = 54;

	/**
	 * Opcode for the jump instruction.
	 */
	public static final int JUMP = 60;

	/**
	 * Opcode for the jumpn (jump if negative) instruction.
	 */
	public static final int JUMPN = 61;

	/**
	 * Opcode for the jumpz (jump if zero) instruction.
	 */
	public static final int JUMPZ = 63;

	/**
	 * Opcode for the halt instruction.
	 */
	public static final int HALT = 80;
	
	/**
	 * TODO
	 */
	private static int[] memoryStack;

//	private int[] instructionStack;

	private static int accumulator = 0;
	
	private static int instructionIndex = 0;
	
	private static int instructionCount = 0;

	private static boolean isHalted = true;
	
	private boolean hasJumped = false;
	
	private int jumpAddr;
	
//	private class InstructionContainer {
//		public ArrayList<Integer> instructions;
//		
//		public ArrayList<String> arguments;
//		
//		public InstructionContainer(ArrayList<Integer> instructions, ArrayList<String> arguments) throws IllegalArgumentException {
//			if (instructions.size() != arguments.size()) {
//				throw new IllegalArgumentException("The size of the instructions list must match the size of the arguments list");
//			}
//			
//			this.instructions = instructions;
//			this.arguments = arguments;
//		}
//	};
	
	/**
	 * Constructs a machine with the given number of words of memory, all words
	 * zero, all registers zero, in a halted state.
	 * 
	 * @param memorySize number of words of memory for this machine
	 */
//	@SuppressWarnings("serial")
	public CS227Comp(int memorySize) {
		memoryStack = new int[memorySize];
		instructionIndex = 0;
		instructionCount = 0;
		
//		instructionStack = new InstructionContainer(
//			new ArrayList<Integer>() {{
//				add(READ);
//				add(READ);
//				add(WRITE);
//				add(LOAD);
//				add(STORE);
//				add(ADD);
//				add(SUB);
//				add(DIV);
//				add(MOD);
//				add(MUL);
//				add(JUMP);
//				add(MUL);
//				add(JUMPN);
//				add(JUMPZ);
//				add(HALT);
//			}},
//			new ArrayList<String>() {{
//				add("06");
//				add("07");
//				add("07");
//				add("06");
//				add("06");
//				add("00");
//				add("00");
//				add("00");
//				add("06");
//				add("06");
//				add("06");
//				add("06");
//				add("06");
//				add("06");
//				add("06");
//			}}
//		);
		
		isHalted = false;
	}

	/**
	 * Constructs a machine with the given initial values in the instruction counter
	 * and accumulator, and the given memory contents. The memory size will be that
	 * of the given array.
	 * 
	 * @param initialIC   initial value for the instruction counter
	 * @param initialAC   initial value for the accumulator
	 * @param memoryImage initial memory contents
	 */
	public CS227Comp(int initialIndex, int initialAcumulator, int[] memoryImage) {
		instructionIndex = initialIndex;
		instructionCount = initialIndex;
		accumulator = initialAcumulator;
		
		memoryStack = memoryImage.clone();
		
		isHalted = false;
		
//		this.loadInstructionsFromMemory();
	}
	
//	private void loadInstructionsFromMemory() {
//		for (int instruction : memoryStack) {
//			if (instruction < 10) {
//				continue;
//			}
//			
//			switch (instruction) {
//				case READ:
//				case WRITE:
//				case LOAD:
//				case STORE:
//				case ADD:
//				case SUB:
//				case DIV:
//				case MOD:
//				case MUL:
//				case JUMP:
//				case JUMPN:
//				case JUMPZ:
//				case HALT:
//					instructionStack.instructions.add(instruction / 100);
//					instructionStack.arguments.add(String.format("%0d", instruction % 100));
//					break;
//				default:
//					break;
//			}
//		}
//	}

	/**
	 * Returns the current value in the accumulator.
	 * 
	 * @return current value in the accumulator
	 */
	public int getAC() {
		return accumulator;
	}

	/**
	 * Returns the current value of the instruction counter.
	 * 
	 * @return current value of the instruction counter
	 */
	public int getIC() {
		return instructionCount;
	}

	/**
	 * Returns the contents of the instruction register (most recently executed
	 * instruction)
	 * 
	 * @return contents of the instruction register
	 */
	public int getIR() {
		if (hasJumped) {
			return memoryStack[jumpAddr];
		}
		return memoryStack[instructionIndex > getMemorySize() ? 0 : getIC() - 1];
	}

	/**
	 * Returns the opcode for the most recently executed instruction (instruction
	 * register divided by 100).
	 * 
	 * @return opcode for the most recently executed instruction
	 */
	public int getOpcode() {
		return getIR() / 100;
	}

	/**
	 * Returns the operand for the most recently executed instruction (instruction
	 * register modulo 100).
	 * 
	 * @return operand for the most recently executed instruction
	 */
	public int getOperand() {
		return getIR() % 100;
	}

	/**
	 * Returns true if the machine is in a halted state, false otherwise.
	 * 
	 * @return true if the machine is in a halted state, false otherwise
	 */
	public boolean isHalted() {
		return isHalted;
	}

	/**
	 * Returns the contents of the memory cell at the given address.
	 * 
	 * @param address memory address
	 * @return contents of memory cell at the given address
	 */
	public int peekMemory(int address) {
		return memoryStack[address];
	}

	/**
	 * Returns the number of words of memory this machine has.
	 * 
	 * @return the number of words of memory
	 */
	public int getMemorySize() {
		return memoryStack.length;
	}

	/**
	 * Executes one instruction at a time, over and over, as long as the machine is
	 * not halted.
	 * @throws CloneNotSupportedException 
	 */
	public void runProgram() {
		while(instructionIndex < memoryStack.length && !isHalted) {
			if (isHalted) {
				return;
			}
			
//			int a = instructionIndex;
//			String b = Arrays.toString(memoryStack);
//			System.out.printf("%s || %s && %d ^^ %s", (Thread.currentThread().getStackTrace())[2].getMethodName(), this.toString(), a, b);
			
			
			this.nextInstruction();
			Instructions.checkMemory();
		}
	}

	/**
	 * Displays complete machine state. This one is done for you. Observe the
	 * conversions that are used to print the values, as you'll need them elsewhere
	 * if you want a uniform look to your output.
	 */
	public void dumpCore() {
		System.out.printf("REGISTERS:\n");
		System.out.printf("%-20s %+05d\n", "accumulator", getAC());
		System.out.printf("%-20s    %02d\n", "instruction counter", getIC());
		System.out.printf("%-20s %+05d\n", "instruction register", getIR());
		System.out.printf("%-20s    %02d\n", "operation code", getIR() / 100);
		System.out.printf("%-20s    %02d\n", "operand", getIR() % 100);
		System.out.printf("\nMEMORY:\n  ");
		
		for (int i = 0; i < 10; i++) {
			System.out.printf("%6d", i);
		}
		
		int row = 0;
		for (int i = 0; i < getMemorySize(); i++) {
			if (i % 10 == 0) {
				row++;
				System.out.printf("\n%2d ", row * 10);
			}
			
			System.out.printf("%+05d ", peekMemory(i));
		}
		
		System.out.println();
	}

	/**
	 * Loads the given values into the machine's memory. If the length of the given
	 * array is smaller than this machine's memory size, the remaining cells are
	 * filled with zeros; if larger, extra values are ignored. If any value is
	 * encountered in the given array that is not between -9999 and 9999, the
	 * machine crashes at that point with message "*** Invalid input ***". The
	 * machine's memory size is not modified. If no invalid inputs are encountered,
	 * the machine will be in a non-halted state.
	 * 
	 * @param image memory image to load
	 */
	public void loadMemoryImage(int[] image) {
		for (int i = 0; i < image.length; i++) {
			if (i >= getMemorySize()) {
				continue;
			}
			
			if (image[i] < -9999 || image[i] > 9999) {
				System.out.println("*** Invalid Input ***");
				return;
			}
			
			memoryStack[i] = image[i];
		}
		
//		Arrays.f
		
		if (memoryStack.length > image.length) {
			for (int i = image.length; i < memoryStack.length; i++) {
				memoryStack[i] = 0;
			}
		}
		isHalted = false;
	}

	/**
	 * Reads input from the terminal, one value per line, until the sentinel value
	 * (-99999) is read. Inputs are stored in successive memory locations starting
	 * with address (index) 0. Inputs are decimal integers in the range
	 * [-9999,9999]. Any invalid input should immediately crash the machine with
	 * error message "*** Invalid input ***". Each input should be prompted for with
	 * the zero-padded, two digit sequential memory index followed by a question
	 * mark. (E.g., the first prompt would be 00?, the second prompt would be 01?,
	 * and so on.) The input (or sentinel) should then be printed out to the console
	 * as a four digit (or five, for the sentinel) number, preceded by a "+" or "-",
	 * and padded with zeros if needed to make it four digits. If no invalid inputs
	 * are encountered, displays the message "*** Program Loaded ***" and leaves the
	 * machine in a non-halted state.
	 */
	public void loadProgramFromConsole() {
		Scanner s = new Scanner(System.in);
		
		try {
			for (int i = 0; true; i++) {
				System.out.printf("%02d? ", i);
				String e = s.next();
				
				if (e.equals("-99999")) {
					break;
				}
				
				StringBuilder t = new StringBuilder(e);
				
				if (t.charAt(0) == '-' || t.charAt(0) == '+') {
					t.deleteCharAt(0);
				}
				
				memoryStack[i] = Integer.parseInt(t.toString());
				System.out.printf("%+04d\n", memoryStack[i]);
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		s.close();
		
		System.out.println("*** Program Loaded ***");
	}

	/**
	 * Reads input from the given file. Input is then loaded into memory according
	 * to the specification for loadMemoryImage.
	 * 
	 * @param filename file from which to read instructions
	 */
	public void loadProgramFromFile(String filename) throws FileNotFoundException {
		ByteArrayOutputStream temp = new ByteArrayOutputStream();
	      PrintWriter pw = new PrintWriter(temp);
	      try (Stream<String> stream = Files.lines(Paths.get(filename))) {
	          stream.forEach(pw::println);
	      } catch (IOException e) {
			throw new FileNotFoundException();
		}
	      pw.println("-99999");
	      pw.flush();
	      
		InputStream in = new ByteArrayInputStream(temp.toByteArray());
		InputStream sysinSaved = System.in;
	      System.setIn(in);
	      PrintStream f = System.out;
	      ByteArrayOutputStream e = new ByteArrayOutputStream();
	      System.setOut(new PrintStream(e));
	     this.loadProgramFromConsole();
	     System.setIn(sysinSaved);
	     System.setOut(f);
	     System.out.println("*** Program Loaded ***");
	}

	/**
	 * Loads the next instruction from memory, parses it for the opcode and operand,
	 * and executes the instruction. The "next instruction" is the value in memory
	 * whose address (index) is currently in the instruction counter. The opcode is
	 * the high-order two digits of the instruction; the operand is the low-order
	 * two digits. Except in case of a jump, the instruction counter is incremented
	 * by one following execution of the instruction.
	 * <p>
	 * Invalid opcodes crash the machine.
	 * <p>
	 * Descriptions of all instructions follow:
	 *
	 * <ul>
	 * <li>READ: Executes the read instruction. Reads a decimal word from the
	 * terminal into the address referenced by operand and updates the instruction
	 * counter. Valid words are in the range [-9999,9999]. Out of range words are
	 * truncated on the right until within range before being stored; the truncated
	 * portion is discarded. For example, -723471 will be truncated to -7234.
	 *
	 * <li>WRITE: Displays the value stored in memory at the address referenced by
	 * the operand as a four digit number, padded on the left as necessary to make
	 * it exactly four digits, and preceded by a "+" or "-". Updates the instruction
	 * counter.
	 *
	 * <li>LOAD: Loads the value stored in memory at the address referenced by
	 * operand into the accumulator and updates the instruction counter.
	 *
	 * <li>STORE: Stores the value in the accumulator into memory at the address
	 * referenced by the operand and updates the instruction counter.
	 *
	 * <li>ADD: Adds the value stored in memory at the address referenced by operand
	 * to the accumulator, accounting for overflow, and updates the instruction
	 * counter.
	 *
	 * <li>SUB: Subtracts the value stored in memory at the address referenced by
	 * operand from the accumulator, accounting for overflow, and updates the
	 * instruction counter.
	 *
	 * <li>DIV: Divides the accumulator by the value stored in memory at the address
	 * referenced by operand, accounting for overflow, and updates the instruction
	 * counter. All division is integer division. Division by zero crashes the
	 * machine.
	 *
	 * <li>MOD: Calculates the remainder when dividing the accumulator by the value
	 * stored in memory at the address referenced by operand, accounting for
	 * overflow, stores the result in the accumulator, and updates the instruction
	 * counter. All division is integer division. Division by zero crashes the
	 * machine.
	 *
	 * <li>MUL: Multiplies the accumulator by the value stored in memory at the
	 * address referenced by operand, accounting for overflow, and updates the
	 * instruction counter.
	 *
	 * <li>JUMP: Changes the instruction counter to operand.
	 *
	 * <li>JUMPN: If the accumulator is negative, changes the instruction counter to
	 * operand, otherwise updates the instruction counter normally.
	 *
	 * <li>JUMPZ: If the accumulator is zero, changes the instruction counter to
	 * operand, otherwise updates the instruction counter normally.
	 *
	 * <li>HALT: Displays the message "*** Program terminated normally ***", halts
	 * the machine, and dumps core.
	 * </ul>
	 * Arithmetic overflow occurs when the accumulator acquires a value outside of
	 * the range [-9999,9999]. It is handled by truncating the value of the
	 * accumulator to the low order four digits.
	 * <p>
	 * Instruction counter overflow occurs when when the value of the instruction
	 * counter matches or exceeds the memory size. It is handled by setting the
	 * instruction counter to zero.
	 * <p>
	 * All crashes dump core.
	 */
	public void nextInstruction() {
		if (isHalted) {
			return;
		}
		
		int instruction = memoryStack[(instructionIndex <= 0 || instructionIndex > getMemorySize()) ? 0 : instructionIndex] / 100;
		int arguments = memoryStack[(instructionIndex <= 0 || instructionIndex > getMemorySize()) ? 0 : instructionIndex] % 100;
		
		try {
			switch (instruction) {
				case READ:
					Instructions.read(arguments);
//					instructionCount++;
					instructionIndex++;
					break;
					
				case WRITE:
					Instructions.write(arguments);
//					instructionCount++;
					instructionIndex++;
					break;
					
				case LOAD:
					Instructions.load(arguments);
//					accumulator = memoryStack[address];
//					instructionCount++;
					instructionIndex++;
					break;
					
				case STORE:
					Instructions.store(arguments);
					instructionIndex++;
					break;
					
				case ADD:
//					System.out.println(getAC());
//					accumulator += memoryStack[address];
//					System.out.println(getAC());
					Instructions.add(arguments);
//					instructionCount++;
					instructionIndex++;
					break;
					
				case SUB:
					Instructions.sub(arguments);
//					accumulator -= memoryStack[address];
//					instructionCount++;
					instructionIndex++;
					break;
					
				case DIV:
					Instructions.div(arguments);
//					instructionCount++;
					instructionIndex++;
					break;
					
				case MOD:
					Instructions.mod(arguments);
//					instructionCount++;
					instructionIndex++;
					break;
					
				case MUL:
					Instructions.mul(arguments);
//					instructionCount++;
					instructionIndex++;
					break;
				
				case JUMP:
					hasJumped = true;
					jumpAddr = instructionIndex;
					instructionIndex = Instructions.jump(arguments);
					instructionCount = instructionIndex;
					Instructions.checkMemory();
					return;
//					instructionCount++;
					
				case JUMPN:
					hasJumped = true;
					jumpAddr = instructionIndex;
					instructionIndex = Instructions.jumpn(arguments);
//					instructionCount++;
					instructionCount = instructionIndex;
					Instructions.checkMemory();
					return;
					
				case JUMPZ:
					hasJumped = true;
					jumpAddr = instructionIndex;
					instructionIndex = Instructions.jumpz(arguments);
//					instructionCount++;
					instructionCount = instructionIndex;
					Instructions.checkMemory();
					return;	
					
				case HALT:
					Instructions.halt();
					this.dumpCore();
//					isHalted = true;
					return;
					
				default:
					if (instruction > 0) {
//						Instructions.halt();
						isHalted = true;
						this.dumpCore();
					}
					instructionIndex++;
//					instructionCount++;
					return;
			}
		} catch(IllegalArgumentException e) {
//			Instructions.halt();
			isHalted = true;
			this.dumpCore();
		}
		
		instructionCount = instructionIndex;
		
		Instructions.checkMemory();
	}
	
	private static class Instructions {
		public static void read(int arguments) {
			Scanner s = new Scanner(System.in);
			
			try {
				String value = s.next();
				int valueAsInt = Integer.parseInt(value);
				int address = arguments;
				
				if (valueAsInt < -9999 || valueAsInt > 9999) {
					int i = 4;
					StringBuilder s1 = new StringBuilder(value);
					
					if (s1.charAt(0) == '-') {
						i = 5;
					}
					
					s1.delete(i, s1.length());
					
					valueAsInt = Integer.parseInt(s1.toString());
				}
				
				memoryStack[address] = valueAsInt;
			} catch (NumberFormatException e) {
				s.close();
				throw new IllegalArgumentException();
			}
			
			s.close();
		}
		
		/**
		 * TODO
		 * 
		 * @param args
		 */
		public static void write(int address) {
			System.out.printf("+%04d\n", memoryStack[address]);
		}
		
		public static void load(int address) {
			accumulator = memoryStack[address];
		}
		
		public static void store(int address) {
			memoryStack[address] = accumulator;
		}
		
		public static void add(int address) {
			accumulator += memoryStack[address];
		}
		
		public static void sub(int address) {
			accumulator -= memoryStack[address];
		}
		
		public static void div(int address) {
//			System.out.println(memoryStack[address]);
			
			try {
				accumulator /= memoryStack[address];
			} catch (ArithmeticException e) {
				throw new IllegalArgumentException();
			}
		}
		
		public static void mod(int address) {
			accumulator = accumulator % memoryStack[address];
		}
		
		public static void mul(int address) {
			accumulator *= memoryStack[address];
		}
		
		public static int jump(int jumpAddress) {
			return jumpAddress < memoryStack.length ? jumpAddress : 0;
		}
		
		public static int jumpn(int jumpAddress) {
			if (accumulator < 0) {
				return jumpAddress < memoryStack.length ? jumpAddress : 0;	
			}
			
			return instructionIndex + 1;
		}
		
		public static int jumpz(int jumpAddress) {
			if (accumulator == 0) {
				return jumpAddress < memoryStack.length ? jumpAddress : 0;	
			}
			
			return instructionIndex + 1;
		}
		
		public static void halt() {
			isHalted = true;
			
			System.out.println("*** Program terminated normally ***");
		}
		
		private static void checkMemory() {
			if (accumulator < -9999 || accumulator > 9999) {
				int i = 0;
				StringBuilder s1 = new StringBuilder(accumulator + "");
				
				if (s1.charAt(0) == '-') {
					i = 1;
				}
				
				s1.delete(i, s1.length() - 4);
				
				accumulator = Integer.parseInt(s1.toString());
			}
			
			if (instructionIndex >= memoryStack.length) {
				instructionIndex = 0;
				instructionCount = 0;
			}
		}
	}
}
