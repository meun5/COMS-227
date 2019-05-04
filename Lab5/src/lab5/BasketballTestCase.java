package lab5;

import org.junit.*;
import static org.junit.Assert.*;

public final class BasketballTestCase
{
    // margin of error for floating-point comparisons
    private static final double EPSILON = 10e-07;
    
    @Test
    public void testInitial()
    {
      Basketball ball = new Basketball(5);
      assertEquals(false, ball.isDribbleable());
    }

    @Test
    public void testInitialDiametre()
    {
      Basketball ball = new Basketball(5);
      assertEquals(5.0, ball.getDiametre(), EPSILON);
    }

    @Test
    public void testInflate()
    {
      Basketball ball = new Basketball(5);
      ball.inflate();
      assertEquals(true, ball.isDribbleable());
    }

    @Test
    public void testDiametreAfterInflation()
    {
      Basketball ball = new Basketball(5);
      ball.inflate();
      assertEquals(5.0, ball.getDiametre(), EPSILON);
    }
   
  }
