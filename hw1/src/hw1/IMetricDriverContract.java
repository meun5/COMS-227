package hw1;

public interface IMetricDriverContract
	extends IDriverContract
{
	public int getTotalKilometres();
	public void drive(int kilometres, int minutes);
	public void driveAtSpeed(int kilometres, double averageSpeed);
}
