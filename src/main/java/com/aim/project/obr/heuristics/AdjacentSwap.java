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
        int[] repr = oSolution.getSolutionRepresentation().getSolutionRepresentation();
        int numPoIs = repr.length;
        int objective_value = oSolution.getObjectiveFunctionValue();


        for (int i = 0; i < numberOfSwaps(dIntensityOfMutation); i++) {
            int l1 = m_oRandom.nextInt(numPoIs);
            int l2 = (l1 + 1) % numPoIs;

            int l1_id = repr[l1];
            int l2_id = repr[l2];

            //Perform Swap
            repr[l1] = l2_id;
            repr[l2] = l1_id;

            if (l1 == numPoIs - 1) { //First and Last
                int neighbour1 = repr[1];      // l2 + 1
                int neighbour2 = repr[l1 - 1]; // l1 -1

                objective_value -= m_oObjectiveFunction.getCost(l1_id, neighbour2);
                objective_value -= m_oObjectiveFunction.getCost(l2_id, neighbour1);

                objective_value += m_oObjectiveFunction.getCost(l1_id, neighbour1);
                objective_value += m_oObjectiveFunction.getCost(l2_id, neighbour2);

            } else { // Normal Adjacent Swap get cost handles depot cases
                int n1_id = (l1 == 0) ? -1 : repr[l1 - 1];
                int n2_id = (l2 == numPoIs - 1) ? oSolution.getNumberOfLocations() - 1 : repr[l2 + 1];

                objective_value -= m_oObjectiveFunction.getCost(n1_id, l1_id);
                objective_value -= m_oObjectiveFunction.getCost(l2_id, n2_id);

                objective_value += m_oObjectiveFunction.getCost(n1_id, l2_id);
                objective_value += m_oObjectiveFunction.getCost(l1_id, n2_id);
            }
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
