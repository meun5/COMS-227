package hw1;

public interface IDriverContract {
	public int getTotalMinutes();
	public void waitAround(int minutes);
	public int getPassengerCount();
	public void pickUp();
	public void dropOff();
	public double getTotalCredits();
	public double getProfit();
	public double getAverageProfitPerHour();
}
