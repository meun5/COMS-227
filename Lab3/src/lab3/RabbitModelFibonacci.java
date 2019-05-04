package lab3;

public class RabbitModelFibonacci
	extends RabbitModel
	implements IRabbitModel
{
	protected int previousYear = 1;
	protected int yearBefore = 0;
	protected int initialPopulation = 0;
	
	public void simulateYear() {
		System.out.println(String.format("Population: %d", this.rabbitPopulation));
		System.out.println(String.format("Previous Year: %d", this.previousYear));
		System.out.println(String.format("Year Before: %d", this.yearBefore));
		
		this.rabbitPopulation = (this.previousYear + this.yearBefore);
		this.yearBefore = this.previousYear;
		this.previousYear = this.rabbitPopulation;
		
//		System.out.println(String.format("Population A: %d", this.rabbitPopulation));
//		System.out.println(String.format("Previous Year A: %d", this.previousYear));
//		System.out.println(String.format("Year Before A: %d", this.yearBefore));
	}
	
	public void reset() {
		this.previousYear = 1;
		this.yearBefore = 0;
		this.rabbitPopulation = this.initialPopulation;
	}
}
