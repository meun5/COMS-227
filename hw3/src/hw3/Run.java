package hw3;

import java.io.FileNotFoundException;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class Run {
	public static void main(String[] args) throws FileNotFoundException {
//		CS227Asm asm = AsmFileUtil.interpretFromFile("src/test1.asm227");
//		
////		System.out.println(asm.getDataStream());
////		System.out.println(asm.getInstructionsStream());
////		System.out.println(asm.getLabelStream());
//		
//		asm.parseData();
//		System.out.println(ReflectionToStringBuilder.toString(asm.getData()));
//		
//		asm.parseLabels();
//		System.out.println(ReflectionToStringBuilder.toString(asm.getLabels()));
//	
//		asm.parseInstructions();
//		System.out.println(asm.getInstructionStream());
//		System.out.println(ReflectionToStringBuilder.toString(asm.getLabels()));
//		
//		asm.setOperandValues();
//		System.out.println(asm.getInstructionStream());
//		
//		asm.addLabelAnnotations();
//		System.out.println(asm.getInstructionStream());
//		
//		System.out.println(asm.writeCode());
//		
//		String filenam1e = "/mnt/c/.Source/asm.wahater.asm227";
//		System.out.println(filenam1e.substring(0, filenam1e.lastIndexOf('.')));
		
//		System.out.println(AsmFileUtil.assembleFromFile("src/test1.asm227"));
//		System.out.println(AsmFileUtil.createMemoryImageFromFile("src/test1.asm227"));
	
//		AsmFileUtil.assembleAndWriteFile("src/collatz.asm227", true);
		AsmFileUtil.assembleAndWriteFile("src/primes.asm227", true);
	}
}
