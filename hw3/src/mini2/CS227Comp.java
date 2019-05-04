
package mini2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Simulation of a simple computer.
 */
public class CS227Comp
{
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
   * Opcode for the sub instruction.
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
   * The memory for this machine.
   */
  private int[] memory;

  /**
   * Accumulator.
   */
  private int accumulator;

  /**
   * Address of next instruction to be fetched and executed.
   */
  private int instructionCounter;

  /**
   * Most recent instruction to be executed.
   */
  private int instructionRegister;

  /**
   * Opcode portion of instruction register.
   */
  private int opcode;

  /**
   * Operand portion of instruction register.
   */
  private int operand;

  /**
   * Flag to indicate whether the machine is halted.
   */
  private boolean isHalted;

  /**
   * Constructs a machine with the given number of words of memory, all words
   * zero, all registers zero, in a halted state.
   */
  public CS227Comp(int memorySize)
  {
    accumulator = 0;
    instructionCounter = 0;
    instructionRegister = 0;
    opcode = 0;
    operand = 0;
    memory = new int[memorySize];
    isHalted = true;
  }

  /**
   * Constructs a machine with the given initial values in the instruction
   * counter and accumulator, and the given memory contents. The memory size
   * will be that of the given array. Immediately crashes the machine if the
   * given memory contains any invalid instructions, as specified in
   * loadMemoryImage.
   * 
   * @param initialIC
   * @param initialAC
   * @param memoryImage
   */
  public CS227Comp(int initialIC, int initialAC, int[] memoryImage)
  {
    instructionCounter = initialIC;
    accumulator = initialAC;
    memory = new int[memoryImage.length];
    loadMemoryImage(memoryImage);
  }

  /**
   * Returns the current value in the accumulator.
   * 
   * @return current value in the accumulator
   */
  public int getAC()
  {
    return accumulator;
  }

  /**
   * Returns the current value of the instruction counter.
   * 
   * @return current value of the instruction counter
   */
  public int getIC()
  {
    return instructionCounter;
  }

  /**
   * Returns the contents of the instruction register (most recently executed
   * instruction)
   * 
   * @return contents of the instruction register
   */
  public int getIR()
  {
    return instructionRegister;
  }

  /**
   * Returns the opcode for the most recently executed instruction (instruction
   * register divided by 100).
   * 
   * @return opcode for the most recently executed instruction
   */
  public int getOpcode()
  {
    return opcode;
  }

  /**
   * Returns the operand for the most recently executed instruction (instruction
   * register modulo 100).
   * 
   * @return operand for the most recently executed instruction
   */
  public int getOperand()
  {
    return operand;
  }

  /**
   * Returns true if the machine is in a halted state, false otherwise.
   * 
   * @return true if the machine is in a halted state, false otherwise
   */
  public boolean isHalted()
  {
    return isHalted;
  }

  /**
   * Returns the contents of the memory cell at the given address.
   * @param address
   *    memory address
   * @return 
   *   contents of memory cell at the given address
   */
  public int peekMemory(int address)
  {
    return memory[address];
  }

  /**
   * Returns the number of words of memory this machine has.
   * @return
   *   the number of words of memory
   */
  public int getMemorySize()
  {
    return memory.length;
  }

  /**
   * Executes one instruction at a time, as long as the machine is not halted.
   */
  public void runProgram()
  {
    while (!isHalted())
    {
      nextInstruction();
    }
  }

  /**
   * Displays complete machine state.
   */
  public void dumpCore()
  {
    System.out.printf("REGISTERS:\n");
    System.out.printf("%-20s %+05d\n", "accumulator", getAC());
    System.out.printf("%-20s    %02d\n", "instruction counter", getIC());
    System.out.printf("%-20s %+05d\n", "instruction register", getIR());
    System.out.printf("%-20s    %02d\n", "operation code", getIR() / 100);
    System.out.printf("%-20s    %02d\n", "operand", getIR() % 100);
    System.out.printf("\nMEMORY:\n  ");
    for (int i = 0; i < 10; i++)
    {
      System.out.printf("%6d", i);
    }
    int row = 0;
    for (int i = 0; i < getMemorySize(); i++)
    {
      if (i % 10 == 0)
      {
        row += 1;
        System.out.printf("\n%2d ", row * 10);
      }
      System.out.printf("%+05d ", peekMemory(i));
    }
    System.out.println();

  }

  /**
   * Loads the given values into the machine's memory. If the length of the
   * given array is smaller than this machine's memory size, the remaining cells
   * are filled with zeros; if larger, extra values are ignored. If any value is
   * encountered in the given array that is not a valid instruction, the machine
   * crashes at that point with message "Invalid input". The machine's memory
   * size is not modified. If no invalid instructions are encountered, the
   * machine will be in a non-halted state.
   * @param image
   *   memory image to load
   */
  public void loadMemoryImage(int[] image)
  {
    int size = Math.min(image.length, memory.length);
    for (int i = 0; i < size; ++i)
    {
      if (image[i] < -9999 || image[i] > 9999)
      {
        doCrash("Invalid input");
        return;
      }
      else
      {
        memory[i] = image[i];
      }
    }

    // zero out everything else
    for (int i = size; i < memory.length; ++i)
    {
      memory[i] = 0;
    }
    isHalted = false;
  }

  /**
   * Reads instructions from in, one per line, until the sentinel value (-99999)
   * is read. Instructions are decimal integers in the range [-9999,9999]. Any
   * invalid input should immediately crash the machine with error message "***
   * Invalid input ***". Each instruction should be prompted for with the
   * zero-padded, two digit sequential instruction number followed by a question
   * mark. The instruction or sentinel should then be echoed as a four digit (or
   * five, for the sentinel), signed, zero-padded decimal integer. After
   * successfully loading, display the message "*** Program Loaded ***" and move
   * to a running state.
   */
  public void loadProgramFromConsole()
  {
    int word;
    int instructionNumber = 0;
    ArrayList<Integer> data = new ArrayList<>();
    Scanner s = new Scanner(System.in);
    do
    {
      System.out.printf("%02d ? ", instructionNumber);
      if (s.hasNextInt())
      {
        word = s.nextInt();
        if (word < 10000 && word > -10000)
        {
          System.out.printf("%+05d\n", word);
          data.add(word);
          instructionNumber += 1;
        }
        else if (word != -99999)
        {
          doCrash("Invalid input");
          return;
        }
      }
      else
      {
        doCrash("Invalid input");
        return;
      }
    }
    while (word != -99999);
    
    System.out.println("-99999\n");
    s.close();
    int[] image = new int[data.size()];
    int i = 0;
    for (int w : data)
    {
      image[i++] = w;
    }
    loadMemoryImage(image);
    System.out.println("\n\n*** Program loaded ***\n\n");
  }

  /**
   * Reads instructions from the given file. Instructions are then loaded into
   * memory according to the specification for loadMemoryImage.
   * @param filename
   *    file from which to read instructions
   */
  public void loadProgramFromFile(String filename) throws FileNotFoundException
  {
    ArrayList<Integer> data = new ArrayList<>();
    Scanner s = new Scanner(new File(filename));
    while (s.hasNextInt())
    {
      int word = s.nextInt();
      if (word == -99999)
      {
        break;
      }
      else
      {
        data.add(word);
      }
    }
    s.close();
    int[] image = new int[data.size()];
    int i = 0;
    for (int word : data)
    {
      image[i++] = word;
    }
    loadMemoryImage(image);
  }

  /**
   * Loads the next instruction from memory, parses it for the opcode and
   * operand, and executes the instruction. Except in case of a jump, the
   * instruction counter is incremented by one following execution of the
   * instruction. Invalid opcodes crash the machine.
   */
  public void nextInstruction()
  {
    instructionRegister = memory[instructionCounter];
    opcode = instructionRegister / 100;
    operand = instructionRegister % 100;

    switch (opcode)
    {
      case READ:
        doRead();
        break;
      case WRITE:
        doWrite();
        break;
      case LOAD:
        doLoad();
        break;
      case STORE:
        doStore();
        break;
      case ADD:
        doAdd();
        break;
      case SUB:
        doSub();
        break;
      case DIV:
        doDiv();
        break;
      case MOD:
        doMod();
        break;
      case MUL:
        doMul();
        break;
      case JUMP:
        doJump();
        break;
      case JUMPN:
        doJumpNegative();
        break;
      case JUMPZ:
        doJumpZero();
        break;
      case HALT:
        doHalt();
        break;
      default:
        doCrash("Invalid instruction");
    }
  }

  /**
   * Increments the instruction counter. If the instruction counter reaches the
   * memory size, it rolls over to address zero.
   */
  private void updateInstructionCounter()
  {
    instructionCounter += 1;
    if (instructionCounter == memory.length)
    {
      instructionCounter = 0;
    }
  }

  /**
   * Replaces the address in the instruction counter with the value in newIC.
   * @param newIC
   *    new value of the instruction counter
   */
  private void updateInstructionCounter(int newIC)
  {
    instructionCounter = newIC;
  }

  /**
   * Executes the read instruction. Reads a decimal word from the terminal into
   * the address referenced by operand and updates the instruction counter.
   * Valid words are in the range [-9999,9999]. Out of range words are truncated
   * on the right until within range before being stored; the truncated portion
   * is discarded. For example, -723471 will be truncated to -7234.
   */
  private void doRead()
  {
    int word;
    Scanner s = new Scanner(System.in);
    word = s.nextInt();
    while (word <= -10000 || word >= 10000)
    {
      word /= 10;
    }
    memory[operand] = word;
    updateInstructionCounter();
  }

  /**
   * Displays the value stored in memory at the address referenced by the
   * operand as a signed, four digit, zero padded decimal integer and updates
   * the instruction counter.
   */
  private void doWrite()
  {
    System.out.printf("\n%+05d\n", memory[operand]);
    updateInstructionCounter();
  }

  /**
   * Loads the value stored in memory at the address referenced by operand into
   * the accumulator and updates the instruction counter.
   */
  private void doLoad()
  {
    accumulator = memory[operand];
    updateInstructionCounter();
  }

  /**
   * Stores the value in the accumulator into memory at the address referenced
   * by the accumulator and updates the instruction counter.
   */
  private void doStore()
  {
    memory[operand] = accumulator;
    updateInstructionCounter();
  }

  /**
   * Adds the value stored in memory at the address reference by operand to the
   * accumulator, accounting for overflow, and updates the instruction counter.
   */
  private void doAdd()
  {
    accumulator += memory[operand];
    handleOverflow();
    updateInstructionCounter();
  }

  /**
   * Subtracts the value stored in memory at the address reference by operand
   * from the accumulator, accounting for overflow, and updates the instruction
   * counter.
   */
  private void doSub()
  {
    accumulator -= memory[operand];
    handleOverflow();
    updateInstructionCounter();
  }

  /**
   * Divides the accumulator by the value stored in memory at the address
   * reference by operand, accounting for overflow, and updates the instruction
   * counter. All division is integer division. Division by zero crashes the
   * machine.
   */
  private void doDiv()
  {
    if (memory[operand] == 0)
    {
      doCrash("Divide by zero");
    }
    else
    {
      accumulator /= memory[operand];
      handleOverflow();
      updateInstructionCounter();
    }
  }

  /**
   * Calculates the remainder when dividing the accumulator by the value stored
   * in memory at the address reference by operand, accounting for overflow, and
   * updates the instruction counter. All division is integer division. Division
   * by zero crashes the machine.
   */
  private void doMod()
  {
    if (memory[operand] == 0)
    {
      doCrash("Divide by zero");
    }
    else
    {
      accumulator %= memory[operand];
      handleOverflow();
      updateInstructionCounter();
    }
  }

  /**
   * Multiplies the value stored in memory at the address reference by operand
   * by the accumulator, accounting for overflow, and updates the instruction
   * counter.
   */
  private void doMul()
  {
    accumulator *= memory[operand];
    handleOverflow();
    updateInstructionCounter();
  }

  /**
   * Changes the instruction counter to operand.
   */
  private void doJump()
  {
    updateInstructionCounter(operand);
  }

  /**
   * If the accumulator is negative, changes the instruction counter to operand,
   * otherwise updates the instruction counter normally.
   */
  private void doJumpNegative()
  {
    if (accumulator < 0)
    {
      updateInstructionCounter(operand);
    }
    else
    {
      updateInstructionCounter();
    }
  }

  /**
   * If the accumulator is zero, changes the instruction counter to operand,
   * otherwise updates the instruction counter normally.
   */
  private void doJumpZero()
  {
    if (accumulator == 0)
    {
      updateInstructionCounter(operand);
    }
    else
    {
      updateInstructionCounter();
    }
  }

  /**
   * Displays the message "*** Program terminated normally ***", halts the
   * machine, and dumps core.
   */
  private void doHalt()
  {
    System.out.println("\n*** Program terminated normally ***\n\n");
    isHalted = true;
    dumpCore();
  }

  /**
   * Displays message between a pair of triple asterisks ("***"", halts the
   * machine, and dumps core.
   * 
   * @param message
   *          The message to display
   */
  private void doCrash(String message)
  {
    System.out.println("\n *** " + message + " ***\n\n");
    isHalted = true;

    dumpCore();
  }

  /**
   * Truncates the accumulator by taking its remainder mod 10000. Helper method
   * for the arithmetic instructions.
   */
  private void handleOverflow()
  {
    accumulator %= 10000;
  }
}
