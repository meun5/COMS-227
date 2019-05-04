package mini2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import org.junit.Assert;
import org.junit.Test;
import speccheck.SpecCheckTest;

public class SpecChecker2
{
  ByteArrayOutputStream baosTheirs;
  PrintStream outTheirs;
  ByteArrayOutputStream baosOurs;
  PrintStream outOurs;
  PrintStream sysoutSaved;
  InputStream sysinSaved;
  private CS227Comp c;
  
  public SpecChecker2() {}
  
  public static void main(String[] args)
  {
    boolean testOnly = args.length > 0;
      String result = speccheck.SpecCheck.grade(SpecChecker2.class);
      System.out.println(result);
  }
  








  public void initializationError()
  {
    System.out.println("foo");
  }
  















  private static final int[] test2 = {
    1021, 
    
    3021, 
    5123, 
    6319, 
    3023, 
    5022, 
    4022, 
    3021, 
    5324, 
    6315, 
    3025, 
    5421, 
    5023, 
    4021, 
    6002, 
    3021, 
    5224, 
    4021, 
    6002, 
    2022, 
    8000, 
    

    0, 0, 1, 
    2, 
    3 };
  

  @Test
  @SpecCheckTest(order=55, nPoints=1, msg="For CS227Comp(100), loadMemoryImage with sample program 2, where input 100 is inserted into cell 21,should halt with result 25 written to console.")
  public void test3nPlusOne2()
    throws Exception
  {
    try
    {
      setSysout();
      outTheirs.println("");
      c = new CS227Comp(100);
      int[] arr = Arrays.copyOf(test2, test2.length);
      arr[0] = 6001;
      arr[21] = 100;
      c.loadMemoryImage(arr);
      c.runProgram();

      outTheirs.flush();
      

      
      
      checkHaltedOutput("+0025");
    }
    finally
    {
      restoreSysout();
    }
  }


  private void checkHaltedOutput()
  {
    checkHaltedOutput("-99999");
  }
  
  private void checkHaltedOutput(String desiredResult)
  {
    boolean checkOutput = !desiredResult.equals("-99999");
    
    Scanner scan = new Scanner(new ByteArrayInputStream(baosTheirs.toByteArray()));
    String actual = "";
    if (checkOutput)
    {
      actual = scan.next();
      if (!desiredResult.equals(actual))
      {
        Assert.fail("Expected output " + desiredResult + ", but first item in output was \"" + actual + "\".");
      }
    }
    ArrayList<String> theirs = new ArrayList();
    while (scan.hasNextLine())
    {
      theirs.add(scan.nextLine());
    }
    scan.close();
    
    int foundMessage = -1;
    int foundCoreDump = -1;
    int count = 0;
    
    for (String s : theirs)
    {
      System.out.println(count + ":" + s);
      count++;
      if (s.toLowerCase().contains("program terminated normally"))
      {
        foundMessage = count;
      }
      else if (s.contains("REGISTERS:"))
      {
        foundCoreDump = count;
        break;
      }
    }
    
    if (foundMessage < 0)
    {
      Assert.fail("Expected string \"Program terminated normally\" in console output. ");
    }
    if (foundCoreDump < 0)
    {
      Assert.fail("Expected core dump after halting. ");
    }
    if (foundMessage > foundCoreDump)
    {
      Assert.fail("Expected termination message before core dump in console output. ");
    }
  }
  


  private void checkCrashedOutput(String expectedMessage)
  {
    Scanner scan = new Scanner(new ByteArrayInputStream(baosTheirs.toByteArray()));
    ArrayList<String> theirs = new ArrayList();
    while (scan.hasNextLine())
    {
      theirs.add(scan.nextLine());
    }
    scan.close();
    
    int foundMessage = -1;
    int foundCoreDump = -1;
    int count = 0;
    
    for (String s : theirs)
    {
      System.out.println(count + ":" + s);
      count++;
      if ((expectedMessage != null) && (s.toLowerCase().contains(expectedMessage)))
      {
        foundMessage = count;
      }
      else if (s.contains("REGISTERS:"))
      {
        foundCoreDump = count;
        break;
      }
    }
    
    if ((expectedMessage != null) && (foundMessage < 0))
    {
      Assert.fail("Expected string \"" + expectedMessage + "\" in console output. ");
    }
    if (foundCoreDump < 0)
    {
      Assert.fail("Expected core dump after crash. ");
    }
  }
  
  private void setSysout()
    throws Exception
  {
    baosTheirs = new ByteArrayOutputStream();
    outTheirs = new PrintStream(baosTheirs);
    baosOurs = new ByteArrayOutputStream();
    outOurs = new PrintStream(baosOurs);
    sysoutSaved = System.out;
    System.setOut(outTheirs);
  }
  
  private void restoreSysout() throws Exception
  {
    System.setOut(sysoutSaved);
  }
  



  public static String getModifierDiff(int expected, int actual)
  {
    String msg = "";
    if (Modifier.isStatic(expected) != Modifier.isStatic(actual)) {
      msg = msg + "It should " + (Modifier.isStatic(actual) ? "not " : "") + "be static. ";
    }
    if (Modifier.isPublic(expected) != Modifier.isPublic(actual)) {
      msg = msg + "It should " + (Modifier.isPublic(actual) ? "not " : "") + "be public. ";
    }
    if (Modifier.isProtected(expected) != Modifier.isProtected(actual)) {
      msg = msg + "It should " + (Modifier.isProtected(actual) ? "not " : "") + "be protected. ";
    }
    if (Modifier.isPrivate(expected) != Modifier.isPrivate(actual)) {
      msg = msg + "It should " + (Modifier.isPrivate(actual) ? "not " : "") + "be private. ";
    }
    if (Modifier.isFinal(expected) != Modifier.isFinal(actual)) {
      msg = msg + "It should " + (Modifier.isFinal(actual) ? "not " : "") + "be final. ";
    }
    if (Modifier.isInterface(expected) != Modifier.isInterface(actual)) {
      msg = msg + "It should " + (Modifier.isInterface(actual) ? "not " : "") + "be interface. ";

    }
    else if (Modifier.isAbstract(expected) != Modifier.isAbstract(actual)) {
      msg = msg + "It should " + (Modifier.isAbstract(actual) ? "not " : "") + "be abstract. ";
    }
    
    return msg;
  }
  
  private static String getTypesList(Class<?>[] types) { String list = "";
    if (types.length > 0) {
      list = list + types[0].getCanonicalName() + ".class";
      for (int i = 1; i < types.length; i++) {
        list = list + ", " + types[i].getCanonicalName() + ".class";
      }
    }
    return list;
  }
  
  @SpecCheckTest(order=0, msg="CLASS COULD NOT BE FOUND. ")
  @Test
  public void testForClasses() throws Exception {
    try { Class.forName("mini2.CS227Comp");
    } catch (ClassNotFoundException e) {
      Assert.fail("A class by the name of mini2.CS227Comp could not be found. Check case, spelling, and that you created your class in the right package.");
    }
  }
}
