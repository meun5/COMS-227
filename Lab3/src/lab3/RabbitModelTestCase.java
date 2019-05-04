package lab3;

import plotter.SimulationPlotter;

public class RabbitModelTestCase {
	public static void main(String[] args) {
		SimulationPlotter plotter = new SimulationPlotter();
//		IRabbitModel model = new RabbitModelInitial();
//		IRabbitModel model = new RabbitModelBeginZero();
//		IRabbitModel model = new RabbitModelDecrease();
//		IRabbitModel model = new RabbitModelRandom();
		IRabbitModel model = new RabbitModelFibonacci();
		
		plotter.simulate((RabbitModel) model);
//		model.simulateYear();
//		System.out.println(model.getPopulation());
	}
}
