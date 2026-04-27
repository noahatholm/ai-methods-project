package com.aim.project.obr.heuristics;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.interfaces.HeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

import java.util.Random;

public class NormalSwap extends HeuristicOperators implements HeuristicInterface {

    public NormalSwap(Random oRandom) {
        super(oRandom, ProblemDomain.HeuristicType.MUTATION);
    }

    @Override
    public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
        int numPoIs = oSolution.getNumberOfLocations() -1;
        int[] repr = oSolution.getSolutionRepresentation().getSolutionRepresentation();


        for (int i = 0; i < numberOfSwaps(dIntensityOfMutation); i++) {
            int l1 = m_oRandom.nextInt(numPoIs);
            int l2 = m_oRandom.nextInt(numPoIs);

            while (l1 == l2) l2 =  m_oRandom.nextInt(numPoIs);

            //Swap
            int temp = repr[l2];
            repr[l2] = repr[l1];
            repr[l1] = temp;


        }

        int objective_value = m_oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
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
