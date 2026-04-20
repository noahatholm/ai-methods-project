package com.aim.project.obr.interfaces;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public interface SolutionRepresentationInterface extends Cloneable {
	
	/**
	 * 
	 * @return The representation of the solution as an array of integers.
	 */
	public int[] getSolutionRepresentation();
	
	/**
	 * Sets the representation of the solution to the new representation.
	 * @param aiRepresentation The new representation
	 */
	public void setSolutionRepresentation(int[] aiRepresentation);
	
	/**
	 * 
	 * @return The total number of locations in this instance (includes the bus depot!).
	 */
	public int getTotalNumberOfLocations();

	/**
	 * 
	 * @return A deep clone of the solution representation.
	 */
	public SolutionRepresentationInterface clone();
}
