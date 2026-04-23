package com.aim.project.obr.heuristics;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.interfaces.OBRSolutionInterface;
import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;

import java.util.Random;

/**
 * <p>
 * This class is included (and all non-crossover heuristics subclass this class) to simplify your implementation, and it
 * is intended that you include any common operations in this class to simplify your implementation of the other heuristics.
 * Furthermore, if you implement and test common functionality here, it is less likely that you introduce a bug elsewhere!
 * <p>
 * For example, think about common neighbourhood operators and any other incremental changes that you might perform
 * while applying low-level heuristics.
 *
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class HeuristicOperators {

	protected final Random m_oRandom;
    protected final ProblemDomain.HeuristicType m_oHeuristicType;
    protected ObjectiveFunctionInterface m_oObjectiveFunction;

	public HeuristicOperators(Random oRandom, ProblemDomain.HeuristicType oHeuristicType) {
        this.m_oHeuristicType = oHeuristicType;
		this.m_oRandom = oRandom;
	}

    public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {

        this.m_oObjectiveFunction = oObjectiveFunction;
    }

    public ProblemDomain.HeuristicType getHeuristicType() {
        return m_oHeuristicType;
    }


    protected int delta_eval(OBRSolutionInterface oSolution, int current_obj, int i, int j) { //Only works for adjacent swap and assumes swap has already happened
        int[] repr = oSolution.getSolutionRepresentation().getSolutionRepresentation();
        int numPoIs = repr.length;

        // Ensure l1 is always smaller one
        int l1 = Math.min(i, j);
        int l2 = Math.max(i, j);

        int l1_id = repr[l1];
        int l2_id = repr[l2];

        if (l1 == 0 && l2 == numPoIs - 1) { //First and last
            int neighbour1 = repr[1];      // Neighbour of the first index
            int neighbour2 = repr[l2 - 1]; // Neighbour of the last index

            // Depot edges don't matter so only internal ones
            current_obj -= m_oObjectiveFunction.getCost(l1_id, neighbour2);
            current_obj -= m_oObjectiveFunction.getCost(l2_id, neighbour1);

            current_obj += m_oObjectiveFunction.getCost(l1_id, neighbour1);
            current_obj += m_oObjectiveFunction.getCost(l2_id, neighbour2);

        } else { // Normal Adjacent swap
            int n1_id = (l1 == 0) ? -1 : repr[l1 - 1];
            int n2_id = (l2 == numPoIs - 1) ? oSolution.getNumberOfLocations() - 1 : repr[l2 + 1];

            //Remove old edges
            current_obj -= m_oObjectiveFunction.getCost(n1_id, l1_id);
            current_obj -= m_oObjectiveFunction.getCost(l2_id, n2_id);

            // Add new edges
            current_obj += m_oObjectiveFunction.getCost(n1_id, l2_id);
            current_obj += m_oObjectiveFunction.getCost(l1_id, n2_id);
        }

        return current_obj;
    }

    protected void adjacent_swap(int l1,int l2,OBRSolutionInterface oSolution) {
        int[] repr = oSolution.getSolutionRepresentation().getSolutionRepresentation();

        int l1_id = repr[l1];
        int l2_id = repr[l2];

        //Perform Swap
        repr[l1] = l2_id;
        repr[l2] = l1_id;
    }

    protected int numberOfIterations(double dos){
        if (dos < 0.2) return 1;
        else if (dos < 0.4) return 2;
        else if (dos < 0.6) return 3;
        else if (dos < 0.8) return 4;
        else return 5;
    }
}
