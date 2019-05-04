package lab5;

import balloon4.Balloon;

import org.junit.*;
import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;

public final class BalloonTestCase {
	protected Balloon balloon = new Balloon(15);
	
	protected static double ELIPSON = 10e-7;
	
	@Before
	public void setup() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		this.balloon = this.balloon.getClass().getConstructor(Integer.TYPE).newInstance(15);
	}
	
	@Test
	public void newlyConstructedBalloonShouldHaveARadiusOfZero() {
		assertEquals(
			"A newly constructed balloon should have a radius of zero.",
			0.0,
			this.balloon.getRadius(),
			ELIPSON
		);
	}
	
	@Test
	public void newlyConstructedBalloonShouldNotBePopped() {
		assertFalse(
			"A newly constructed balloon should not be popped.",
			this.balloon.isPopped()
		);
	}
	
	@Test
	public void afterCallingBlowFiveTimesTheRadiusShouldBeFive() {
		this.balloon.blow(5);
		assertEquals(
			"After calling blow five times, the radius should be five.",
			5.0,
			this.balloon.getRadius(),
			ELIPSON
		);
	}
	
	@Test
	public void aDeflatedBalloonShouldHaveARadiusOfZero() {
		this.balloon.blow(5);
		this.balloon.deflate();
		
		assertEquals(
			"A deflated balloon should have a radius of zero.",
			0.0,
			this.balloon.getRadius(),
			ELIPSON
		);
	}
	
	@Test
	public void aDeflatedBalloonShouldNotBePopped() {
		this.balloon.blow(5);
		this.balloon.deflate();
		
		assertFalse(
			"A deflated balloon should not be popped.",
			this.balloon.isPopped()
		);
	}
	
	@Test
	public void blowingADeflatedBalloonShouldIncreaseItsRadius() {
		this.balloon.blow(5);
		this.balloon.deflate();
		this.balloon.blow(7);
		this.balloon.blow(5);
		
		assertEquals(
			"Blowing a deflated balloon should still increase its radius.",
			13.0,
			this.balloon.getRadius(),
			ELIPSON
		);
	}
	
	@Test
	public void blowingABalloonPastItsMaximumRadiusShouldPopTheBalloon() {
		this.balloon.blow(100);
		
		assertTrue(
			"Blowing a balloon past its maximum radius should pop the balloon.",
			this.balloon.isPopped()
		);
	}
	
	@Test
	public void blowingABalloonPastItsMaximumRadiusShouldPopTheBalloonAndHaveARadiusOfZero() {
		this.balloon.blow(100);
		
		assertEquals(
			"Blowing a balloon past its maximum radius should pop the balloon and have a radius of zero.",
			0.0,
			this.balloon.getRadius(),
			ELIPSON
		);
	}
	
	@Test
	public void blowingAPoppedBalloonShouldNotIncreaseItsRadius() {
		this.balloon.blow(5);
		this.balloon.pop();
		this.balloon.blow(15);
		
		assertEquals(
			"Blowing a popped balloon should not increase its radius.",
			0.0,
			this.balloon.getRadius(),
			ELIPSON
		);
	}
	
	@Test
	public void afterPoppingABalloonItShouldBePopped() {
		this.balloon.pop();
		
		assertTrue(
			"After popping a balloon, it should be popped.",
			this.balloon.isPopped()
		);
	}
	
	@Test
	public void aPoppedBalloonShouldHaveARadiusOfZero() {
		this.balloon.pop();
		
		assertEquals(
			"A popped balloon should have a radius of zero.",
			0.0,
			this.balloon.getRadius(),
			ELIPSON
		);
	}
}
