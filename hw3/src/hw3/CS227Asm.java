package hw3;

import java.util.ArrayList;
import java.util.List;

import api.Instruction;
import api.NVPair;
import api.SymbolTable;

/**
 * 
 * @author Alexander Young
 * 
 * This class uses Upper Camel Casing for instance variables, as is sometimes standard in C# style guidelines.
 */
public class CS227Asm {
	/**
	 * A Streamable list of instructions.
	 * 
	 * This is only used for the initial parsing.
	 */
	private List<String> InstructionStream = new ArrayList<String>();
	
	/**
	 * A Streamable list of data elements.
	 * 
	 * This is only used for iteration purposes, or when referral back to the initial section content is necessary.
	 */
	private List<String> DataStream = new ArrayList<String>();
	
	/**
	 * A Streamable array of instructions.
	 * 
	 * This is only used for the initial parsing.
	 */
	private List<String> LabelStream = new ArrayList<String>();
	
	/**
	 * A symbol table of all the data elements with their corresponding values.
	 */
	private SymbolTable DataTable = new SymbolTable();
	
	/**
	 * A symbol table of all the labels and their corresponding pointers.
	 */
	private SymbolTable LabelTable = new SymbolTable();
	
	/**
	 * A list of Instructions.
	 * 
	 * This is primarily used for iterative purposes, but also to calculate the pointers for data access.
	 */
	private List<Instruction> InstructionTable = new ArrayList<Instruction>();
	
	/**
	 * Constructs an assembler for the given assembly language program, given as an ArrayList of strings (one per line of the program).
	 * 
	 * @param program		An array of input lines of assembly.
	 */
	public CS227Asm(ArrayList<String> program) {
		// Here we are sublisting the input file to get the lines between the two section headers.
		
		// This gets the data sublist, which is between the data and labels section headers.
		DataStream = program.subList(
			program.indexOf("data:") + 1,
			program.indexOf("labels:")
		);
		
		// This gets the labels sublist, which is between the labels and instructions section headers.
		LabelStream = program.subList(
			program.indexOf("labels:") + 1,
			program.indexOf("instructions:")
		);
		
		// This gets the instructions sublist, which is between the instructions section headers and the end of the file.
		InstructionStream = program.subList(
			program.indexOf("instructions:") + 1,
			program.size()
		);
	}
	
	/**
	 * For each instruction in the instruction stream that is a jump target, adds the label to the instruction's description.
	 */
	public void addLabelAnnotations() {
		// Here we iterate over each label and set the label annotation to that instruction.
		LabelStream.forEach(s -> {
			InstructionTable.get(LabelTable.findByName(s).getValue()).addLabelToDescription(s);
		});
	}
	
	/**
	 * Assembles the source program represented by this assembler instance and returns the generated machine code and data as an array of strings.
	 * 
	 * @return		The generated machine code
	 */
	public ArrayList<String> assemble() {
		this.parseData();
		this.parseLabels();
		this.parseInstructions();
		this.setOperandValues();
		this.addLabelAnnotations();
		
		return this.writeCode();
	}
	
	/**
	 * Returns the symbol table for data (variables).
	 * 
	 * @return		The data symbol table
	 */
	public SymbolTable getData() {
		return DataTable;
	}
	
	/**
	 * Returns the instruction stream.
	 * 
	 * @return		The instruction stream
	 */
	public ArrayList<Instruction> getInstructionStream() {
		// Here we cast, as we use a List<> internally.
		return (ArrayList<Instruction>) InstructionTable;
	}
	
	/**
	 * Returns the symbol table for labels (jump targets).
	 * 
	 * @return		The label symbol table
	 */
	public SymbolTable getLabels() {
		return LabelTable;
	} 
	
	/**
	 * Creates the symbol table for the data section of this assembler's program.
	 */
	public void parseData() {
		DataStream.forEach(s -> {
			String[] parts = s.split(" ");
			
			if (parts.length == 1) {
				DataTable.add(parts[0]);
				return;
			}
			
			DataTable.add(parts[0], Integer.parseInt(parts[1]));
		});
	}
	
	/**
	 * Creates instruction stream from the instruction section of this assembler's program and fills in label addresses in the symbol table for labels.
	 */
	public void parseInstructions() {
		int labelIndex = 0;
		
		// Here we iterate over each instruction and check if it is a label.
		// If it is a label, we update the LabelTable with the index of the label, accounting for previous labels.
		// If it is not a label, we add the instruction to the InstructionTable.
		for(String i : InstructionStream) {
			// EDIT 05/04/2019: The edit here is that we have to split off the comments on the labels, as found out by the collatz asm.
			if (!LabelTable.containsName(i.split(" ")[0])) {
				InstructionTable.add(new Instruction(i));	
			} else {
				LabelTable.findByName(i.split(" ")[0]).setValue(InstructionStream.indexOf(i) - labelIndex++);;
			}
		}
	}
	
	/**
	 * Creates the symbol table for the label section of this assembler's program, leaving all label values as zeros.
	 */
	public void parseLabels() {
		LabelStream.forEach(s -> LabelTable.add(s, 0));
	}
	
	/**
	 * Fills in the correct operand value for all instructions (either a data address or jump target address, depending on the instruction).
	 */
	public void setOperandValues() {
		InstructionTable.forEach(i -> {
			String target = i.getOperandString();
			
			if (i.requiresDataAddress()) {
				i.setOperand(InstructionTable.size() + DataTable.indexOf(DataTable.findByName(target)));
			}
			
			if (i.requiresJumpTarget()) {
				i.setOperand(LabelTable.findByName(target).getValue());
			}
		});
	}
	
	/**
	 * Generates the machine code and data for this assembler's program, terminated by the string "-99999".
	 * 
	 * @return		The generated machine code, plus the sentinel
	 */
	public ArrayList<String> writeCode() {
		ArrayList<String> output = new ArrayList<String>();
		
		// Here we add the copy over all the instructions, and then the zero padded data.
		InstructionTable.forEach(i -> output.add(i.toString()));
		DataStream.forEach(d -> {
			NVPair s = DataTable.findByName(d.split(" ")[0]);
			output.add(String.format("+%04d %s", s.getValue(), s.getName()));
		});
		
		// Add the closing sentinel value.
		output.add("-99999");
		
		return output;
	}
}