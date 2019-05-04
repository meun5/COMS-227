package hw1;

public interface IImperialDriverContract
	extends IDriverContract
{
	public int getTotalMiles();
	public void drive(int miles, int minutes);
	public void driveAtSpeed(int miles, double averageSpeed);
}
