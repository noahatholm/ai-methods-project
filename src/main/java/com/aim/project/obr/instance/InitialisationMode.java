package com.aim.project.obr.instance;

/**
 * Represents the initialisation modes for generating solutions to the Open-top Bus Routing problem.
 * This enumeration is used to determine the method by which the initial solutions should be created.
 *
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public enum InitialisationMode {

    /**
     * Specifies the mode of initialisation to use when creating a solution.
     * RANDOM mode initialises the solution in an entirely random manner with
     * the help of the experimental random number generator but must visit all
     * points-of-interest in the tourist route.
     *
     * This is the default mode that should be used when running your learning-based selection hyper-heuristic.
     */
	RANDOM,
    /**
     * Specifies the mode of initialisation to use when creating a solution.
     * CONSTRUCTIVE mode generates a solution using the nearest neighbour
     * algorithm and ensuring all points-of-interest in the tourist route are visited.
     * If there are multiple nearest neighbours, the next point-of-interest chosen must
     * be the point-of-interest that appears earliest in the problem instance file.
     *
     * You may be asked to demonstrate this in the practical exam.
     */
    CONSTRUCTIVE
}
