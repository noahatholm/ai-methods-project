package com.aim.project.obr.interfaces;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public interface CrossoverHeuristicInterface extends HeuristicInterface {

    /**
     * Applies a crossover operation to generate a child solution based on two parent solutions.
     * The crossover may be influenced by the depth of search and intensity of mutation parameters.
     * This method should also update the objective function value of the child solution.
     *
     * @param oParent1 The first parent solution used in the crossover.
     * @param oParent2 The second parent solution used in the crossover.
     * @param oChild The child solution that is modified by the crossover operation,
     *               reflecting the result of combining the two parent solutions.
     * @param dDepthOfSearch A parameter controlling the extent or depth of exploitation
     *                       during the crossover operation.
     * @param dIntensityOfMutation A parameter controlling the extent or intensity of
     *                              exploration made during the crossover operation.
     * @return The updated objective function value of the child solution after the crossover is applied.
     */
	public int apply(OBRSolutionInterface oParent1,
                        OBRSolutionInterface oParent2,
                        OBRSolutionInterface oChild,
                        double dDepthOfSearch,
                        double dIntensityOfMutation);

}
