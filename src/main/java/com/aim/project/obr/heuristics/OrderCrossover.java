package com.aim.project.obr.heuristics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import com.aim.project.obr.interfaces.CrossoverHeuristicInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;
import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
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
        throw new RuntimeException("Wrong crossover method called");
	}

	@Override
	public int apply(OBRSolutionInterface oParent1, OBRSolutionInterface oParent2,
                        OBRSolutionInterface oChild, double dDepthOfSearch, double dIntensityOfMutation) {

        int numPoIs = oParent1.getNumberOfLocations() -1;

        //Copy Parent2's DNA to child
        int[] child_dna= oParent2.getSolutionRepresentation().getSolutionRepresentation().clone();
        int[] parent1_dna = oParent1.getSolutionRepresentation().getSolutionRepresentation();
        int[] parent2_dna = oParent2.getSolutionRepresentation().getSolutionRepresentation();


        int cut1 = m_oRandom.nextInt(numPoIs);
        int cut2 = m_oRandom.nextInt(numPoIs);
        while (cut1 == cut2) cut1 = m_oRandom.nextInt(numPoIs); //cant be the same

        if (cut2 < cut1){ //Ensure that cut1 one is left and cut2 is right
            int temp = cut1;
            cut1 = cut2;
            cut2 = temp;
        }

        //Bit map for the parent1 cut section
        int[] bit_map = new int[oParent1.getNumberOfLocations()];
        Arrays.fill(bit_map, 0); //Initialise to 0


        //Cut Parent1
        for (int i = cut1; i < cut2; i++) {
            child_dna[i] = parent1_dna[i];
            bit_map[parent1_dna[i]] = 1;
        }

        //Fill the rest with the sequence
        int write_pos = 0;
        for (int i = 0; i < numPoIs; i++) {
            if (write_pos == cut1) {
                write_pos = cut2;
            }

            // If the value not in child already write it to the gap.
            if (bit_map[parent2_dna[i]] == 0) {
                child_dna[write_pos] = parent2_dna[i];
                write_pos++;
            }
        }

        oChild.getSolutionRepresentation().setSolutionRepresentation(child_dna);
        int objective_value = m_oObjectiveFunction.getObjectiveFunctionValue(oChild.getSolutionRepresentation());
        oChild.setObjectiveFunctionValue(objective_value);
        return objective_value;
	}


	@Override
	public boolean isCrossover() {
		return true;
	}

	@Override
	public boolean usesIntensityOfMutation() {
		return false;
	}

	@Override
	public boolean usesDepthOfSearch() {
		return false;
	}

	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {
		
		this.m_oObjectiveFunction = oObjectiveFunction;
	}
}
