package com.aim.project.obr.heuristics;


import java.util.Random;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.interfaces.HeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;


/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class NextDescent extends HeuristicOperators implements HeuristicInterface {

	public NextDescent(Random oRandom) {

        super(oRandom, ProblemDomain.HeuristicType.LOCAL_SEARCH);
	}

	@Override
	public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
        int best_eval = oSolution.getObjectiveFunctionValue();
        int numPoIs = oSolution.getNumberOfLocations() -1;


        for (int i = 0; i < numberOfIterations(dDepthOfSearch); i++) {
            for (int l1 = 0; l1 <  numPoIs; l1++ ) {
                int l2 = (l1 + 1) % numPoIs;

                //Perform Swap
                adjacent_swap(l1, l2, oSolution);
                int temp_eval = delta_eval(oSolution, best_eval, l1, l2);
                if (temp_eval < best_eval) {
                    best_eval = temp_eval;
                } else {
                    adjacent_swap(l1, l2, oSolution); //Reject the change and swap it back
                }
            }
        }
        oSolution.setObjectiveFunctionValue(best_eval);
        return best_eval;
	}

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return true;
	}
}
