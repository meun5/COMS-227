package lab3;

import java.util.Random;

public class RabbitModelRandom
	extends RabbitModel
	implements IRabbitModel
{	
	protected Random random = new Random(25565);

	public void simulateYear() {
		this.rabbitPopulation += random.nextInt(11);
	}
	
	public void reset() {
		this.rabbitPopulation = this.initialPopulation;
	}
}
