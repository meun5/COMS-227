package lab3;

public class RabbitModelDecrease
	extends RabbitModel
	implements IRabbitModel
{
	protected int initialPopulation = 500;
	
//	public RabbitModelDecrease() {
//		this.reset();
//	}
	
	public void simulateYear() {
		System.out.println(String.format("Population: %d", rabbitPopulation));
		this.rabbitPopulation /= 2;
	}
	
	public void reset() {
		this.rabbitPopulation = this.initialPopulation;
	}
}
