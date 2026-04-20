package com.aim.project.obr.heuristics;

import java.util.Random;

import com.aim.project.obr.interfaces.HeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	public AdjacentSwap(Random oRandom) {

		super(oRandom);
	}

    /**
     * TODO - delta evaluation
     *
     * @param oSolution The solution to which the heuristic is applied. This solution may be modified during the method execution.
     * @param dDepthOfSearch The parameter controlling the extent or depth of exploration performed during the heuristic's application.
     * @param dIntensityOfMutation The parameter controlling the extent or intensity of changes introduced by the heuristic.
     * @return
     */
	@Override
	public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {

        // TODO
        return Integer.MAX_VALUE;
	}

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

}
