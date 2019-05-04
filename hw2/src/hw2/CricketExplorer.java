package hw2;

//import java.lang.reflect.Field;

import api.Outcome;

import hw2.SpecChecker;

public class CricketExplorer {
	public static void main(String[] args) {
//		CricketGame game = new CricketGame(2, 1, 4, 6);
//		
//		System.out.println(String.format("Before Team 0: %d", game.getScore(false)));
//		System.out.println(String.format("Before Team 1: %d", game.getScore(true)));
//		System.out.println(game.getCompletedInnings());
//		System.out.println(game.whichSideIsBatting());
//		System.out.println(game.isGameEnded());
//		
//		System.out.println("=====");
//		for (int z = 0; z < 8; z++) {
//			game.bowl(Outcome.BOUNDARY_SIX);
//		}
//		System.out.println("=====");
//		
//		System.out.println(String.format("After Team 0: %d", game.getScore(false)));
//		System.out.println(String.format("After Team 1: %d", game.getScore(true)));
//		System.out.println(game.getCompletedInnings());
//		System.out.println(game.whichSideIsBatting());
//		System.out.println(game.isGameEnded());
//		SpecChecker a = new SpecChecker();
//		a.setup();
//		
//		try {
//			a.test80longerScenarioThreeInnings();
//		} catch (org.junit.ComparisonFailure e) {
//			e.printStackTrace();
//		}
//		
//		try {
//			Field f = a.getClass().getDeclaredField("g");
//			f.setAccessible(true);
//			
//			CricketGame g = (CricketGame) f.get(a);
//			
//			System.out.println(SpecChecker.createString(g));
//		} catch (NoSuchFieldException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalArgumentException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		CricketGame g = new CricketGame(2, 3, 4, 6);
		g.bowl(Outcome.WIDE);
		g.bowl(Outcome.BOUNDARY_FOUR);
		g.bowl(Outcome.HIT);
		g.tryRun();
		g.tryRun();
		g.tryRun();
		g.runOut();
		g.bowl(Outcome.CAUGHT_FLY);
		g.bowl(Outcome.HIT);
		g.tryRun();
		g.tryRun();
		g.tryRun();
		g.safe();
		g.bowl(Outcome.LBW);
		g.bowl(Outcome.WICKET);
		System.out.println("expected 1: " + g.getCompletedInnings()); // expected 1
		System.out.println("expected 0: " + g.getOverCount()); // expected 0
		System.out.println("expected 0: " + g.getBowlCount()); // expected 0
		System.out.println("expected 0: " + g.getOuts()); // expected 0
		System.out.println("expected 10: " + g.getScore(false)); // expected 10
		System.out.println("expected 0: " + g.getScore(true)); // expected 0
		System.out.println("Team Batting: " + g.whichSideIsBatting());
		System.out.println(" ");
		
		g.bowl(Outcome.BOUNDARY_FOUR);
		g.bowl(Outcome.BOUNDARY_SIX);
		g.bowl(Outcome.NO_BALL);
		g.bowl(Outcome.NO_BALL);
		g.bowl(Outcome.NO_BALL);
		g.bowl(Outcome.NO_BALL);
		g.bowl(Outcome.NO_BALL);
		g.bowl(Outcome.NO_BALL);
		g.bowl(Outcome.HIT);
		g.tryRun();
		g.runOut();
		g.bowl(Outcome.CAUGHT_FLY);
		g.bowl(Outcome.CAUGHT_FLY);
		g.bowl(Outcome.LBW);
		System.out.println("expected 2: " + g.getCompletedInnings()); // expected 2
		System.out.println("expected 0: " + g.getOverCount()); // expected 0
		System.out.println("expected 0: " + g.getBowlCount()); // expected 0
		System.out.println("expected 0: " + g.getOuts()); // expected 0
		System.out.println("expected 10: " + g.getScore(true)); // expected 10
		System.out.println("expected 16: " + g.getScore(false)); // expected 16
		System.out.println("Team Batting: " + g.whichSideIsBatting());
		System.out.println(" ");
//		
		g.bowl(Outcome.HIT);
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.safe();
		System.out.println(SpecChecker.createString(g));
		g.bowl(Outcome.HIT);
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.runOut();
		System.out.println("Out");
		System.out.println(SpecChecker.createString(g));
		g.bowl(Outcome.HIT);
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.runOut();
		System.out.println("Out");
		System.out.println(SpecChecker.createString(g));
		g.bowl(Outcome.BOUNDARY_FOUR);
		System.out.println(SpecChecker.createString(g));
		g.bowl(Outcome.WICKET);
		System.out.println("Wicket");
		System.out.println(SpecChecker.createString(g));
		g.bowl(Outcome.HIT);
		System.out.println(SpecChecker.createString(g));
		g.tryRun();
		System.out.println(SpecChecker.createString(g));
		g.safe();
		System.out.println(SpecChecker.createString(g));
		System.out.println("expected 3: " + g.getCompletedInnings()); // expected 3
		System.out.println("expected 0: " + g.getOverCount()); // expected 0
		System.out.println("expected 0: " + g.getBowlCount()); // expected 0
		System.out.println("expected 0: " + g.getOuts()); // expected 0
		System.out.println("expected 36: " + g.getScore(false)); // expected 36
		System.out.println("expected 16: " + g.getScore(true)); // expected 16
		System.out.println("Team Batting: " + g.whichSideIsBatting());
		System.out.println(" ");
//		
//		g.bowl(Outcome.CAUGHT_FLY);
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.safe();
//		g.bowl(Outcome.WIDE);
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.runOut();
//		g.bowl(Outcome.BOUNDARY_SIX);
//		g.bowl(Outcome.HIT);
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.tryRun();
//		g.safe();
//		System.out.println("expected 3: " + g.getCompletedInnings()); // expected 3
//		System.out.println("expected 2: " + g.getOverCount()); // expected 2
//		System.out.println("expected 1: " + g.getBowlCount()); // expected 1
//		System.out.println("expected 2: " + g.getOuts()); // expected 2
//		System.out.println("expected 36: " + g.getScore(false)); // expected 36
//		System.out.println("expected 38: " + g.getScore(true)); // expected 38
//		System.out.println("Team Batting: " + g.whichSideIsBatting());
//		System.out.println(" ");
	}
}
