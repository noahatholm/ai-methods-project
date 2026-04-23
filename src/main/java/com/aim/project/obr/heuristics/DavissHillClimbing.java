package com.aim.project.obr.heuristics;

import java.util.*;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.interfaces.HeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;


/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class DavissHillClimbing extends HeuristicOperators implements HeuristicInterface {
    private int[] order;

	public DavissHillClimbing(Random oRandom) {
	
		super(oRandom, ProblemDomain.HeuristicType.LOCAL_SEARCH);
	}

    /**
     * DAVIS's BIT HILL CLIMBING LECTURE SLIDE PSEUDO-CODE
     *
     * <pre>
     *  bestEval = evaluate(currentSolution)
     *  perm = createRandomPermutation(length(currentSolution))
     *   for (j = 0; j < length(currentSolution); j++) { // performs a single pass of the solution
     *
     *       bitFlip(currentSolution, perm[j]) // flip the bit referenced to in perm's j^th index
     *       tempEval = evaluate(solution)
     *
     *       if(tempEval < bestEval) {
     *           bestEval = tempEval // accept the bit flip
     *       } else {
     *           bitFlip(currentSolution, perm[j]) // otherwise reject the bit flip
     *       }
     *   }
     *   </pre>
     *
     */


	@Override
	public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
        int best_eval = oSolution.getObjectiveFunctionValue();
        int numPoIs = oSolution.getNumberOfLocations() -1;
        //If order array not initialised yet initialise
        if (order == null) {
            order = new int[numPoIs];
            for (int i = 0; i < numPoIs; i++) order[i] = i;
        }

        shuffleArray(order);

        for (int i = 0; i < numberOfIterations(dDepthOfSearch); i++) {
            for (int l1 : order) {
                int l2 = order[(l1 + 1) % numPoIs];

                //Perform Swap
                adjacent_swap(l1, l2, oSolution);
                int temp_eval = delta_eval(oSolution, best_eval, l1, l2);
                if (temp_eval < best_eval) {
                    best_eval = temp_eval;
                } else {
                    adjacent_swap(l1, l2, oSolution); //Reject the change and swap it back
                }
            }
            shuffleArray(order);
        }
        oSolution.setObjectiveFunctionValue(best_eval);
        return best_eval;
	}

    private void shuffleArray(int[] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int j = m_oRandom.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
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
