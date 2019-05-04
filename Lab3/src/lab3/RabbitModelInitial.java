package lab3;

import lab3.RabbitModelInitial;
//import java.util.*;

/**
 * A RabbitModel is used to simulate the growth
 * of a population of rabbits. 
 */
public class RabbitModelInitial
	extends RabbitModel
	implements IRabbitModel
{
  protected int rabbitPopulation = 0;
  protected int initialPopulation = 2;  
 
  /**
   * Returns the current number of rabbits.
   * @return
   *   current rabbit population
   */
  public int getPopulation()
  {
    return this.rabbitPopulation;
  }
  
  /**
   * Updates the population to simulate the
   * passing of one year.
   */
  public void simulateYear()
  {
    this.rabbitPopulation++;
  }
  
  /**
   * Sets or resets the state of the model to the 
   * initial conditions.
   */
  public void reset()
  {
    this.rabbitPopulation = this.initialPopulation;
  }
}