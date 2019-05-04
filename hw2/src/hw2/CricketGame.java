package hw2;

import java.util.HashMap;

import api.*;

/**
 * 
 * @author Alexander K. Young <youngale@iastate.edu>
 * 
 * Copyright 2019 Alexander K. Young. This file is licensed under the GNU General Public License v3.
 * 
 * This class uses HashMaps to reduce the amount of instance variables required, and to make the implementation cleaner.
 */
public class CricketGame
{
	/**
	 * These are the allowed keys for the state HashMap, and define what it stores.
	 */
	private enum StateTypes {
		Bowls,
		Overs,
		Score,
		Outs,
		Innings,
		BattingSide,
		IsInPlay,
		IsRunning,
		HasTheGameEnded,
	};
	
	/**
	 *	These are the allowed keys for the rules HashMap, and define what it stores.
	 */
	private enum RuleTypes {
		BowlsPerOver,
		OversPerInnings,
		TotalInnings,
		NumberOfPlayers
	};
	
	/**
	 * This is a HashMap of current game state.
	 */
	private HashMap<StateTypes, Object> state = new HashMap<StateTypes, Object>();
	
	/**
	 * This is a HashMap of provided rules, (Overs Per Innings, Total Amount of Innings, etc..).
	 */
	private HashMap<RuleTypes, Integer> rules = new HashMap<RuleTypes, Integer>();
	
	/**
	 * This constructor overload sets up the rules with their default values.
	 */
	public CricketGame() {
		this(
			Defaults.DEFAULT_BOWLS_PER_OVER,
			Defaults.DEFAULT_OVERS_PER_INNINGS,
			Defaults.DEFAULT_NUM_INNINGS,
			Defaults.DEFAULT_NUM_PLAYERS
		);
	}
	
	/**
	 * This constructor sets up the rules with the provided values. 
	 * 
	 * @param givenBowlsPerOver			This is the total amount of bowls allowed per over
	 * @param givenOversPerInnings		This is the total amount of overs per innings
	 * @param givenTotalInnings			This is the total amount of innings per game
	 * @param givenNumberOfPlayers		This is number of players per team
	 */
	public CricketGame(int givenBowlsPerOver, int givenOversPerInnings, int givenTotalInnings, int givenNumberOfPlayers) {
		// If the provided innings is odd, increment to be even.
		if (givenTotalInnings % 2 == 1) {
			givenTotalInnings++;
		}
		
		// Add all the rules to the Rule HashMap.
		this.rules.put(RuleTypes.BowlsPerOver, givenBowlsPerOver);
		this.rules.put(RuleTypes.OversPerInnings, givenOversPerInnings);
		this.rules.put(RuleTypes.TotalInnings, givenTotalInnings);
		this.rules.put(RuleTypes.NumberOfPlayers, givenNumberOfPlayers);
		
		// Initialize the game state.
		this.setup();
	}
	
	/**
	 * This method initializes the game state with default values.
	 */
	private void setup() {
		for (StateTypes key : StateTypes.values()) {
			this.state.computeIfAbsent(key, _key -> {
				switch (_key) {
					case HasTheGameEnded:
						return false;
					case IsInPlay:
						return false;
					case IsRunning:
						return false;
					case Score:
						// The scores are store in a nested HashMap.
						HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
						
						map.put(0, 0);
						map.put(1, 0);
						
						return map;
					default:
						return 0;
				}
			});
		}
	}
	
	/**
	 * This returns the score for either the team currently batting or the opposing team.
	 * 
	 * @param battingSide	Whether the team currently batting is requested
	 * @return				the score for either the team currently batting or the opposing team as determined by the param
	 */
	@SuppressWarnings("unchecked")
	public int getScore(boolean battingSide) {
		HashMap<Integer, Integer> scores = (HashMap<Integer, Integer>) this.state.get(
			StateTypes.Score
		);
		
		return (int) scores.getOrDefault(
			battingSide ? this.whichSideIsBatting() : this.whichSideIsBatting() == 1 ? 0 : 1, 
			0
		);
	}

	/**
	 * This returns the current bowl count in the innings.
	 * 
	 * @return 		the bowl count
	 */
	public int getBowlCount() {
		return (int) this.state.getOrDefault(StateTypes.Bowls, 0);
	}
	
	/**
	 * This returns the current over count in the innings.
	 * 
	 * @return 		the over count
	 */
	public int getOverCount() {
		return (int) this.state.getOrDefault(StateTypes.Overs, 0);
	}
	
	/**
	 * This returns the amount of outs in the innings.
	 * 
	 * @return 		the amount of outs
	 */
	public int getOuts() {
		return (int) this.state.getOrDefault(StateTypes.Outs, 0);
	}
	
	/**
	 * This returns whether the game is over.
	 * 
	 * @return 		true if game is over, false otherwise
	 */
	public boolean isGameEnded() {
		return (boolean) this.state.getOrDefault(StateTypes.HasTheGameEnded, false);
	}
	
	/**
	 * This returns with team is currently at bat.
	 * 
	 * @return		the team at bat
	 */
	public int whichSideIsBatting() {
		return (int) this.state.getOrDefault(StateTypes.BattingSide, 0);
	}
	
	/**
	 * This returns the amount of completed innings in the game.
	 * 
	 * @return 		the amount of completed innings
	 */
	public int getCompletedInnings() {
		return (int) this.state.getOrDefault(StateTypes.Innings, 0);
	}
	
	/**
	 * This returns whether the ball is in play.
	 * 
	 * @return 		true if ball is in play, false otherwise
	 */
	public boolean isInPlay() {
		return (boolean) this.state.getOrDefault(StateTypes.IsInPlay, false);
	}
	
	/**
	 * This returns whether the player is currently running.
	 * 
	 * @return 		true if player is running, false otherwise
	 */
	public boolean isRunning() {
		return (boolean) this.state.getOrDefault(StateTypes.IsRunning, false);
	}
	
	/**
	 * This method takes an bowl outcome and changes the state accordingly.
	 * 
	 * @param k		The outcome of a bowl
	 */
	public void bowl(Outcome k) {
		if ((boolean) this.state.getOrDefault(StateTypes.HasTheGameEnded, false)) {
			return;
		}
		
		if ((boolean) this.state.getOrDefault(StateTypes.IsInPlay, false)) {
			return;
		}
		
		switch (k) {
			case WICKET:
				this.increaseStateValue(StateTypes.Outs);
				this.increaseStateValue(StateTypes.Bowls);
				
				break;
			case BOUNDARY_FOUR:
				this.increaseScore(this.whichSideIsBatting(), 4);
				this.increaseStateValue(StateTypes.Bowls);
				
				break;
			case BOUNDARY_SIX:
				this.increaseScore(this.whichSideIsBatting(), 6);
				this.increaseStateValue(StateTypes.Bowls);
				
				break;
			case CAUGHT_FLY:
				this.increaseStateValue(StateTypes.Outs);
				this.increaseStateValue(StateTypes.Bowls);
				
				break;
			case HIT:
				this.state.replace(StateTypes.IsInPlay, true);
				
				break;
			case LBW:
				this.increaseStateValue(StateTypes.Outs);
				this.increaseStateValue(StateTypes.Bowls);
				
				break;
			case NO_BALL:
				this.increaseScore();
				
				break;
			case WIDE:
				this.increaseScore();
				
				break;
			default:
				break;
		}
		
		this.checkState();
		
		return;
	}
	
	/**
	 * This method will attempt a run, and if there was a previous run attempt, it is assume to have completed successfully and that increases the score.
	 */
	public void tryRun() {
		if (!((boolean) this.state.getOrDefault(StateTypes.IsInPlay, false))) {
			return;
		}
		
		if ((boolean) this.state.getOrDefault(StateTypes.HasTheGameEnded, false)) {
			return;
		}
		
		if ((boolean) this.state.get(StateTypes.IsRunning)) {
			this.increaseScore();
		}
		
		this.state.replace(StateTypes.IsRunning, true);
	}
	
	/**
	 * This method updates the state in accordance with the players choice to stay put in the safe zone after a run.
	 */
	public void safe() {
		if (!((boolean) this.state.getOrDefault(StateTypes.IsInPlay, false))) {
			return;
		}
		
		if ((boolean) this.state.getOrDefault(StateTypes.HasTheGameEnded, false)) {
			return;
		}
		
		if ((boolean) this.state.get(StateTypes.IsRunning)) {
			this.increaseScore();
		}
		
		this.state.replace(StateTypes.IsRunning, false);
		this.state.replace(StateTypes.IsInPlay, false);
		
		this.increaseStateValue(StateTypes.Bowls);
		
		this.checkState();
	}
	
	/**
	 * This method updates the state in accordance with the players choice to run, but then get hit whilst running, counting as an out.
	 */
	public void runOut() {
		if (!((boolean) this.state.getOrDefault(StateTypes.IsInPlay, false))) {
			return;
		}
		
		if (((boolean) this.state.getOrDefault(StateTypes.HasTheGameEnded, false))) {
			return;
		}
		
		if (!((boolean) this.state.getOrDefault(StateTypes.IsRunning, false))) {
			return;
		}
		
		this.increaseStateValue(StateTypes.Outs);
		this.state.replace(StateTypes.IsRunning, false);
		this.state.replace(StateTypes.IsInPlay, false);
		
		this.increaseStateValue(StateTypes.Bowls);
		
		this.checkState();
	}
	
	/**
	 * This increments a value in the state HashMap.
	 */
	private void increaseStateValue(StateTypes type) {
		this.state.replace(
			type,
			(int) this.state.getOrDefault(type, 0) + 1
		);
	}
	
	/**
	 * This overload increments the current batting teams score.
	 */
	private void increaseScore() {
		this.increaseScore(this.whichSideIsBatting());
	}
	
	/**
	 * This overload increments the score of the given teams.
	 * 
	 * @param team		The team whose score is to be updated
	 */
	private void increaseScore(int team) {
		this.increaseScore(team, 1);
	}
	
	/**
	 * This method increases the score of the given team by the given amount.
	 * 
	 * @param team		The team whose score is to be updated
	 * @param amount	The amount to update the teams score by
	 */
	@SuppressWarnings("unchecked")
	private void increaseScore(int team, int amount) {
		HashMap<Integer, Integer> scores = (HashMap<Integer, Integer>) this.state.get(
			StateTypes.Score
		);
			
		Integer teamScore = scores.get(team);
		scores.replace(this.whichSideIsBatting(), teamScore + amount);
			
		this.state.replace(
			StateTypes.Score,
			scores
		);
	}
	
	/**
	 * This method prepares the game for a new innings by clearing Outs, Bowls, and Overs, and by incrementing Innings and swapping the batting team.
	 */
	private void newInnings() {
		this.increaseStateValue(StateTypes.Innings);
		
		this.state.replace(StateTypes.Outs, 0);
		this.state.replace(StateTypes.Bowls, 0);
		this.state.replace(StateTypes.Overs, 0);
		this.state.replace(StateTypes.BattingSide, this.whichSideIsBatting() == 1 ? 0 : 1);
	}
	
	/**
	 * This method validates the state of the game and updates it if conditions for GameOver, Bowls -> Overs, or New Innings.
	 */
	private void checkState() {
		// Convert Bowls to Overs
		if (
			(int) this.state.get(StateTypes.Bowls) == (int) this.rules.get(RuleTypes.BowlsPerOver)
		) {
			this.increaseStateValue(StateTypes.Overs);
			
			this.state.replace(StateTypes.Bowls, 0);
		}
		
		// Detect innings ending conditions
		if (
			(int) this.state.get(StateTypes.Overs) == (int) this.rules.get(RuleTypes.OversPerInnings)  // If the number of overs reaches the number of overs per innings.
		) {
			this.newInnings();
		}
		
		if (
			(int) this.state.get(StateTypes.Outs) >= (int) this.rules.get(RuleTypes.NumberOfPlayers) - 1  // If the amount of outs reaches the number of players (zero indexed).
		) {
			this.newInnings();
		}
		
		// Detect game ending conditions
		if (
			(this.getCompletedInnings() == (int) this.rules.getOrDefault(RuleTypes.TotalInnings, 1) - 1) &&  // If this is the last innings.
			(this.getScore(true) > this.getScore(false))  // If the score of the batting side breaches the score of the opposing side.
		) {
			this.state.replace(StateTypes.HasTheGameEnded, true);
		}
		
		if (
			this.getCompletedInnings() >= (int) this.rules.getOrDefault(RuleTypes.TotalInnings, 1)  // If the amount of completed innings is greater than or equal to the total innings per game.
		) {
			this.state.replace(StateTypes.HasTheGameEnded, true);
		}
	}
}
