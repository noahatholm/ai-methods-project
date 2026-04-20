package main.java.com.aim.project.obr.heuristics;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import main.java.com.aim.project.obr.interfaces.CrossoverHeuristicInterface;
import main.java.com.aim.project.obr.interfaces.OBRSolutionInterface;
import main.java.com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
import com.aim.project.obr.solution.OBRSolution;

/**
 * This crossover type heuristic should take two parent solutions and create a single child
 * (chosen randomly) according to the explanation of OX from the lectures (see lecture 6).
 * When selecting the two cut points, you should ensure that it is not possible that all
 * sightseeing locations are within the segment to be copied. There is no requirement to
 * use either of the intensity of mutation or depth of search settings for this
 * implementation.
 *
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OrderCrossover implements CrossoverHeuristicInterface {
	
	private final Random m_oRandom;
	
	private ObjectiveFunctionInterface m_oObjectiveFunction;

	public OrderCrossover(Random oRandom) {
		
		this.m_oRandom = oRandom;
	}

	@Override
	public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

        // TODO
        return Integer.MAX_VALUE;
	}

	@Override
	public int apply(OBRSolutionInterface oParent1, OBRSolutionInterface oParent2,
                        OBRSolutionInterface oChild, double dDepthOfSearch, double dIntensityOfMutation) {

        // TODO
        return Integer.MAX_VALUE;
	}

	/*
	 * TODO update the methods below to return the correct boolean value.
	 */

	@Override
	public boolean isCrossover() {

        // TODO
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {

        // TODO
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {

        // TODO
		return false;
	}

	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {
		
		this.m_oObjectiveFunction = oObjectiveFunction;
	}
}
