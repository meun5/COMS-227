package lab3;

import lab3.IRabbitModel;
import lab3.RabbitModel;

public class RabbitModelBeginZero
	extends RabbitModel
	implements IRabbitModel
{
	protected int currentYear = 0;
	protected int initialPopulation = 0;
	
	public void simulateYear() {
		System.out.println(String.format("Population: %d", this.rabbitPopulation));
		System.out.println(String.format("Year: %d", this.currentYear));
		this.currentYear++;
		this.rabbitPopulation++;
		
		if (this.currentYear % 5 == 0) {
			this.rabbitPopulation = this.initialPopulation;
		}
	}
	
	public void reset() {
		this.currentYear = 0;
		this.rabbitPopulation = this.initialPopulation;
	}
}
