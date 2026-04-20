package com.aim.project.obr.interfaces;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public interface OBRSolutionInterface extends Cloneable {
	
	/**
	 * 
	 * @return The objective value of the solution.
	 */
	public int getObjectiveFunctionValue();
	
	/**
	 * 
	 * Updates the objective value of the solution.
	 * @param objectiveFunctionValue The new objective function value.
	 */
	public void setObjectiveFunctionValue(int objectiveFunctionValue);
	
	/**
	 * 
	 * @return The solution's representation.
	 */
	public SolutionRepresentationInterface getSolutionRepresentation();
	
	/**
	 * 
	 * @return The total number of locations in this solution (includes the bus depot!).
	 */
	public int getNumberOfLocations();

	/**
	 * 
	 * @return A deep clone of the solution.
	 */
	public OBRSolutionInterface clone();

}
