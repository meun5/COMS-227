package hw1;

/**
 * @author Alexander Young
 * 
 * Copyright 2019 Alexander Young. This file is licensed under the GNU General Public License v3.
 * 
 * This class uses conditional statements to ensure code clarity and intent for both the developer and reader.
 * Alternative implementations using Math.min/max are provided in the comments.
 */
public class UberDriver
{
	/**
	 * This constant stores the maximum capacity of the Uber drivers vehicle.
	 */
	public static final int MAX_PASSENGERS = 4;
	
	/**
	 * This constant holds the operating cost of the driver in per unit format.
	 */
	public static final double OPERATING_COST = 0.5;
	
	/**
	 * This stores the current amount of passengers the drivers vehicle currently has.
	 */
	private int amountOfPassengers = 0;
	
	/**
	 * This stores the current amount of minutes the driver has driven.
	 */
	private int totalMinutes = 0;
	
	/**
	 * This stores the current amount of units (metres/miles) the driver has driven.
	 */
	private int totalMiles = 0;
	
	/**
	 * This stores the per unit rate at which the driver accumulates credits.
	 */
	private double perUnitRate = 0.0;
	
	/**
	 * This stores the per minute rate at which the driver accumulates credits.
	 */
	private double perMinuteRate = 0.0;
	
	/**
	 * This stores the total credits that the driver has accumulated.
	 */
	private double totalCreditsAccumulated = 0.0;
	
	/**
	 * This is the constructor for the UberDriver class. It sets the given variables to the instance variables.
	 * 
	 * @param givenPerUnitRate 		The instance variable perMinuteRate gets set to this argument.
	 * @param givenPerMinuteRate	The instance variable perUnitRate gets set to this argument.
	 */
	public UberDriver(double givenPerUnitRate, double givenPerMinuteRate) {
		this.perMinuteRate = givenPerMinuteRate;
		this.perUnitRate = givenPerUnitRate;
	}

	/**
	 * Drives the vehicle for the given number of miles over the given number of minutes.
	 * 
	 * @param miles 		The miles argument is added onto the instance variable totalMiles and is then used in the accumulated credit calculation.
	 * @param minutes		The minutes argument is added onto the instance variable totalMinutes and then is used in the accumulated credit calculation.
	 */
	public void drive(int miles, int minutes) {
		this.totalMiles += miles;
		this.totalMinutes += minutes;
		
		this.totalCreditsAccumulated +=
			((this.perMinuteRate * minutes) * this.amountOfPassengers) +
			((this.perUnitRate * miles) * this.amountOfPassengers);
	}

	/**
	 * Drives the vehicle for the given number of miles at the given speed.
	 * 
	 * This is equivalent to calling drive(miles, m), where m is the actual number of minutes required,
	 * rounded to the nearest integer.
	 * 
	 * It is the responsibility of the caller of this method to ensure that averageSpeed is positive.
	 * 
	 * @param miles				The miles argument is passed verbatim to the drive function.
	 * @param averageSpeed		The averageSpeed argument is used to calculate the amount of time need to travel a certain distance with a given speed.
	 */
	public void driveAtSpeed(int miles, double averageSpeed) {
		this.drive(miles, (int) Math.round((miles / averageSpeed) * 60));
	}
	
	/**
	 * Simulates sitting in the vehicle without moving for the given number of minutes.
	 * 
	 * @param minutes		The minutes argument is added onto the totalMinutes instance variable and is then used in the accumulated credit calculation.
	 */
	public void waitAround(int minutes) {
		this.totalMinutes += minutes;

		this.totalCreditsAccumulated += (this.perMinuteRate * minutes) * this.amountOfPassengers;
	}
	
	/**
	 * Increases the passenger count by 1, bar exceeding MAX_PASSENGERS
	 */
	public void pickUp() {
		/**
		 * Alternative Implementation:
		 * 
		 * this.amountOfPassengers = Math.min(MAX_PASSENGERS, this.amountOfPassengers);
		 */
		if (this.amountOfPassengers < MAX_PASSENGERS) {
			this.amountOfPassengers++;
		}
	}

	/**
	 * Decreases the passenger count by 1, bar less than zero
	 */
	public void dropOff() {
		/**
		 * Alternative Implementation:
		 * 
		 * this.amountOfPassengers = Math.max(0, this.amountOfPassengers - 1);
		 */
		if (this.amountOfPassengers > 0) {
			this.amountOfPassengers--;
		}
	}
	
	/**
	 * Returns the number of passengers currently in the vehicle.
	 * 
	 * @return int
	 */
	public int getPassengerCount() {
		return this.amountOfPassengers;
	}

	/**
	 * Returns the total minutes driven since construction.
	 * 
	 * @return int
	 */
	public int getTotalMinutes() {
		return this.totalMinutes;
	}

	/**
	 * Returns the total miles driven since this UberDriver was constructed.
	 * 
	 * @return int
	 */
	public int getTotalMiles() {
		return this.totalMiles;
	}

	/**
	 * Returns this UberDriver's total credits (money earned before deducting operating costs).
	 * 
	 * @return int
	 */
	public double getTotalCredits() {
		return this.totalCreditsAccumulated;
	}

	/**
	 * Returns this UberDriver's profit (total credits, less operating costs).
	 * 
	 * @return double
	 */
	public double getProfit() {
		return this.totalCreditsAccumulated - (OPERATING_COST * this.totalMiles);
	}

	/**
	 * Returns this drivers average profit per hour worked. 
	 * 
	 * It is the responsibility of the caller to ensure that it is only called when the value of getTotalMinutes() is nonzero.
	 * 
	 * @return double
	 */
	public double getAverageProfitPerHour() {
		return this.getProfit() / (this.totalMinutes / 60.0);
	}
}
