package com.aim.project.obr.heuristics;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.interfaces.HeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

import java.util.Random;

public class Inversion extends HeuristicOperators implements HeuristicInterface {

    public Inversion(Random oRandom) {
        super(oRandom, ProblemDomain.HeuristicType.MUTATION);
    }

    @Override
    public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
        int numPoIs = oSolution.getNumberOfLocations() -1;
        int objective_value = oSolution.getObjectiveFunctionValue();
        int[] repr = oSolution.getSolutionRepresentation().getSolutionRepresentation();



        for (int i = 0; i < numberOfInversions(dIntensityOfMutation); i++) {
            int l1 = m_oRandom.nextInt(numPoIs);
            int l2 = m_oRandom.nextInt(numPoIs);

            while (l1 == l2) l2 = m_oRandom.nextInt(numPoIs-1);

            if (l2 < l1){
                int temp = l2;
                l2 = l1;
                l1 = temp;
            }

            //Reverse part of the sequence
            while (l1 < l2){
                int temp = repr[l1];
                repr[l1] = repr[l2];
                repr[l2] = temp;
                l1++;
                l2--;
            }
        }
        objective_value = m_oObjectiveFunction.getObjectiveFunctionValue(oSolution.getSolutionRepresentation());
        oSolution.setObjectiveFunctionValue(objective_value);
        return objective_value;
    }
    private int numberOfInversions(double IOM){
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
