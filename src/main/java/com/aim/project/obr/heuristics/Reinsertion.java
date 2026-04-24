package com.aim.project.obr.heuristics;

import java.util.Random;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.interfaces.HeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class Reinsertion extends HeuristicOperators implements HeuristicInterface {


	public Reinsertion(Random oRandom) {

        super(oRandom, ProblemDomain.HeuristicType.MUTATION);
	}

	@Override
	public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
        int numPoIs = oSolution.getNumberOfLocations() -1;
        int[] repr = oSolution.getSolutionRepresentation().getSolutionRepresentation();


        for (int i = 0; i < numberOfSwaps(dIntensityOfMutation); i++) {
            int l1 = m_oRandom.nextInt(numPoIs); //Location to remove
            int l2 =  m_oRandom.nextInt(numPoIs); //New place to insert after

            while (l1 == l2) { //they can't be same
                l2 = m_oRandom.nextInt(numPoIs);
            }

            reinsert(repr,l1,l2);
        }
        int objective_value = m_oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
        oSolution.setObjectiveFunctionValue(objective_value);
        return objective_value;

	}

    private void reinsert(int[] repr, int l1, int l2) {
        int repr_l1 = repr[l1];

        if (l1 < l2){
            for (int i = l1; i < l2; i++) {
                repr[i] = repr[i+1]; //Move everything down after removal
            }
            repr[l2] = repr_l1; //Insert at the new space
        }
        else if (l1 > l2){
            for (int i = l1 ; i > l2; i--) {
                repr[i] = repr[i-1];
            }
            repr[l2] = repr_l1;
        }
    }

    private int numberOfSwaps(double IOM){
        if (IOM < 0.2) return 1;
        else if (IOM < 0.4) return 2;
        else if (IOM < 0.6) return 3;
        else if (IOM < 0.8) return 4;
        else return 5;
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
