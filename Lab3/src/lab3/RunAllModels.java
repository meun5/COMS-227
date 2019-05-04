package lab3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RunAllModels {
	public static void main(String[] args) {
		List<IRabbitModel> models = new ArrayList<IRabbitModel>(Arrays.asList(
			new RabbitModelInitial(),
			new RabbitModelBeginZero(),
			new RabbitModelDecrease(),
			new RabbitModelRandom(),
			new RabbitModelFibonacci()
		));
		
		for (IRabbitModel model : models) {
			Runnable Model = new RunModel(model);
			
			new Thread(Model).start();
		}
	}
}
