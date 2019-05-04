package lab3;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;

import plotter.SimulationPlotter;

public class RunModel
	implements Runnable
{
	protected IRabbitModel model;
	
	public RunModel(IRabbitModel model) {
		this.model = model;
	}

	public void run() {
		SimulationPlotter plotter = new SimulationPlotter();
		
		try {
			Field f = plotter.getClass().getDeclaredField("frame");
			f.setAccessible(true);
			
			Method m = plotter.getClass().getDeclaredMethod("createAndShow");
			m.setAccessible(true);
			
			m.invoke(plotter);
			
			JFrame frame = (JFrame) f.get(plotter);
			
			frame.setTitle(model.getClass().getSimpleName());
			frame.setAlwaysOnTop(false);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		plotter.simulate((RabbitModel) this.model);
	}

}
