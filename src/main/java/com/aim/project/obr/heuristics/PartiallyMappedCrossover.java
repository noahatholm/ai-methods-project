package com.aim.project.obr.heuristics;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;
import com.aim.project.obr.interfaces.CrossoverHeuristicInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class PartiallyMappedCrossover implements CrossoverHeuristicInterface {

	private final Random m_oRandom;

    private ObjectiveFunctionInterface m_oObjectiveFunction;

	public PartiallyMappedCrossover(Random oRandom) {
		
		this.m_oRandom = oRandom;
	}

	@Override
	public int apply(OBRSolutionInterface oSolution, double dDepthOfSearch, double dIntensityOfMutation) {
        throw new RuntimeException("Wrong crossover method called");
	}

	@Override
	public int apply(OBRSolutionInterface oParent1, OBRSolutionInterface oParent2, OBRSolutionInterface oChild, double dDepthOfSearch, double dIntensityOfMutation) {

        //Copy Parent2's DNA to child
        int[] child_dna= oParent2.getSolutionRepresentation().getSolutionRepresentation().clone();
        int[] parent1_dna = oParent1.getSolutionRepresentation().getSolutionRepresentation();
        int numPoIs = parent1_dna.length;

        int cut1 = m_oRandom.nextInt(numPoIs);
        int cut2 = m_oRandom.nextInt(numPoIs);
        while (cut1 == cut2) cut1 = m_oRandom.nextInt(numPoIs); //cant be the same

        if (cut2 < cut1){ //Ensure that cut1 one is left and cut2 is right
            int temp = cut1;
            cut1 = cut2;
            cut2 = temp;
        }

        //Create mappings
        int[] map = create_mapping(oParent1,oParent2,cut1,cut2);

        //Perform Crossover
        System.arraycopy(parent1_dna, cut1, child_dna, cut1, cut2 - cut1);

        //Use the mappings so new solution is complete
        for (int i = 0; i < numPoIs; i++) {
            if (i >= cut1 && i < cut2) continue; //Can skip if in the cut segment

            int val = child_dna[i]; // This is the value from Parent 2


            //Search the map for the mapping
            while (map[val] != -1) {
                val = map[val];
            }

            child_dna[i] = val;
        }

        //Set the child's repr
        oChild.getSolutionRepresentation().setSolutionRepresentation(child_dna);
        int objective_value = m_oObjectiveFunction.getObjectiveFunctionValue(oChild.getSolutionRepresentation());
        oChild.setObjectiveFunctionValue(objective_value);

        return objective_value;
	}

    //I originally used a hashmap for this but the overheads of creating, hashing, destroying everytime were quite high so gonna use an array instead which despite being O(N^2) worst case not O(1) should be quicker for most cases
    //Most examples are relatively low n e.g < 100 so this is fine.
    //For a really large number of stops hashmap would perform significantly better
    private int[] create_mapping(OBRSolutionInterface p1, OBRSolutionInterface p2, int cut1, int cut2){
        int[] p1_repr = p1.getSolutionRepresentation().getSolutionRepresentation();
        int[] p2_repr = p2.getSolutionRepresentation().getSolutionRepresentation();

        int[] map = new int[p1.getNumberOfLocations()];
        Arrays.fill(map, -1); //Initialise to -1


        for (int i = cut1; i < cut2; i++){
            if (p1_repr[i] != p2_repr[i]) { // Only map if they are different
                map[p1_repr[i]] = p2_repr[i];
            }
        }

        return map;
    }

	@Override
	public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {

        this.m_oObjectiveFunction = oObjectiveFunction;
	}

	@Override
	public boolean isCrossover() {
		return true;
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
