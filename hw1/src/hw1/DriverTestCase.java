package hw1;

public class DriverTestCase {
	public static void main(String[] args) {
		UberDriver driver = new UberDriver(1.00, 0.20);
		
		driver.pickUp();
		driver.drive(10, 25);
		
		System.out.println(driver.getProfit());
		System.out.println(driver.getTotalMinutes());
		System.out.println(driver.getAverageProfitPerHour());
	}
}
