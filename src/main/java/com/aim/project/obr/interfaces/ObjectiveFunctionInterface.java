package main.java.com.aim.project.obr.interfaces;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 *
 * Interface for an objective function for OBR.
 */
public interface ObjectiveFunctionInterface {

    /**
     * Calculates and returns the objective function value for the given solution representation.
     *
     * @param solutionRepresentation The representation of the solution for which the objective function value is to be calculated.
     * @return The objective function value as an integer.
     */
	public int getObjectiveFunctionValue(SolutionRepresentationInterface solutionRepresentation);
	
    /**
     * Calculates the cost associated with travelling from one point-of-interest to another.
     *
     * @param iLocationA The ID of the starting PoI.
     * @param iLocationB The ID of the destination PoI.
     * @return An integer representing the cost of travelling between the two locations.
     */
	public int getCost(int iLocationA, int iLocationB);
	
    /**
     * Calculates the distance from the bus depot to a specified point-of-interest (PoI).
     *
     * @param iLocation The ID of the point-of-interest. This ID corresponds to the specific PoI
     *                  for which the distance from the bus depot needs to be calculated.
     * @return The distance from the bus depot to the specified point-of-interest as an integer value.
     */
	public int getDistanceBetweenBusDepotAndPoI(int iLocation);

}
