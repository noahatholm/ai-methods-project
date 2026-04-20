package main.java.com.aim.project.obr.interfaces;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public interface HeuristicInterface {

    /**
     * Applies a heuristic operation to the provided solution. This method modifies the solution using
     * the given depth of search and intensity of mutation parameters, potentially improving or diversifying it.
     * The operation performed is implementation-specific and defined by the heuristic's logic but must also update the
     * objective function value of the modified solution.
     *
     * @param oSolution The solution to which the heuristic is applied. This solution may be modified during the method execution.
     * @param dDepthOfSearch The parameter controlling the extent or depth of exploration performed during the heuristic's application.
     * @param dIntensityOfMutation The parameter controlling the extent or intensity of changes introduced by the heuristic.
     * @return The updated objective function value of the modified solution after the heuristic has been applied.
     */
	public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation);

    /**
     * Determines if the heuristic uses crossover operations.
     *
     * @return true if the heuristic is a crossover type operator, false otherwise.
     */
	public boolean isCrossover();

    /**
     * Determines whether the heuristic uses the "intensity of mutation" parameter
     * while applying its operations.
     *
     * @return true if the heuristic uses intensity of mutation, false otherwise.
     */
	public boolean usesIntensityOfMutation();

    /**
     * Determines whether the heuristic uses the "depth of search" parameter
     * while performing its operations.
     *
     * @return true if the heuristic uses depth of search, false otherwise.
     */
	public boolean usesDepthOfSearch();

    /**
     * Sets the objective function to be used for evaluating solutions.
     *
     * @param oObjectiveFunction The instance of the objective function implementing the ObjectiveFunctionInterface.
     */
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction);
}
