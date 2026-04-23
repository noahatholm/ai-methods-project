package com.aim.project.obr.heuristics;

import java.util.Random;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.interfaces.HeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class AdjacentSwap extends HeuristicOperators implements HeuristicInterface {

	public AdjacentSwap(Random oRandom) {

        super(oRandom, ProblemDomain.HeuristicType.MUTATION);
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
            int numPoIs = oSolution.getNumberOfLocations() -1;
            int objective_value = oSolution.getObjectiveFunctionValue();


            for (int i = 0; i < numberOfSwaps(dIntensityOfMutation); i++) {
                int l1 = m_oRandom.nextInt(numPoIs);
                int l2 = (l1 + 1) % numPoIs;

                adjacent_swap(l1,l2,oSolution);

                objective_value = delta_eval(oSolution,objective_value ,l2,l1);
            }

            oSolution.setObjectiveFunctionValue(objective_value);
            return objective_value;
        }



    private int numberOfSwaps(double IOM){
        if (IOM < 0.2) return 1;
        else if (IOM < 0.4) return 2;
        else if (IOM < 0.6) return 4;
        else if (IOM < 0.8) return 8;
        else if (IOM < 1) return 16;
        else return 32;
    }

	@Override
	public boolean isCrossover() {
		return false;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return true;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}

}
