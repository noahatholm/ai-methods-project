package com.aim.project.obr.interfaces;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 *
 */
public interface InLabPracticalExamInterface {

    /**
     * This method must print the best solution found to System.out in the form:
     * (d_x,d_y) - (l_x0,l_y0) - ... - (l_x{n-1},l_y{n-1}) - (d_x,d_y)
     * where:
     *  `d_x` is the x-coordinate of the (bus) depot.
     *  `d_y` is the y-coordinate of the (bus) depot.
     *  `l_xi` is the x-coordinate of the location in the i^th index in the solution.
     *  `l_yi` is the y-coordinate of the location in the i^th index in the solution.
     *
     * For example, "(0,0) - (1,1) - (2,2) - (3,3) - (4,4) - (0,0)"
     */
    void printBestSolutionFound();

    /**
     * Prints the objective value of the best solution found.
     */
    void printObjectiveValueOfTheBestSolutionFound();

    /**
     * This method must print the initial solution to System.out in the form:
     * (d_x,d_y) - (l_x0,l_y0) - ... - (l_x{n-1},l_y{n-1}) - (d_x,d_y)
     * where:
     *  `d_x` is the x-coordinate of the (bus) depot.
     *  `d_y` is the y-coordinate of the (bus) depot.
     *  `l_xi` is the x-coordinate of the location in the i^th index in the solution.
     *  `l_yi` is the y-coordinate of the location in the i^th index in the solution.
     *
     * For example, "(0,0) - (1,1) - (2,2) - (3,3) - (4,4) - (0,0)"
     */
    void printInitialSolution();
}
