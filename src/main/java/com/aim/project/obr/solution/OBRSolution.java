package com.aim.project.obr.solution;

import com.aim.project.obr.instance.OpenTopBusRoutingInstance;
import com.aim.project.obr.interfaces.OBRSolutionInterface;
import com.aim.project.obr.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OBRSolution implements OBRSolutionInterface {
    private SolutionRepresentationInterface solution;
    private int solution_objective_value;

	public OBRSolution(SolutionRepresentationInterface oRepresentation, int iObjectiveFunctionValue) {
        solution = oRepresentation;
        solution_objective_value = iObjectiveFunctionValue;
	}

	@Override
	public int getObjectiveFunctionValue() {
        return solution_objective_value;
	}

	@Override
	public void setObjectiveFunctionValue(int iObjectiveFunctionValue) {
        solution_objective_value = iObjectiveFunctionValue;
	}

	@Override
	public SolutionRepresentationInterface getSolutionRepresentation() {
        return solution;
	}
	
	@Override
	public OBRSolutionInterface clone() {
        return new OBRSolution(solution.clone(), solution_objective_value);
	}

	@Override
	public int getNumberOfLocations() {
        return solution.getTotalNumberOfLocations();
	}

    public static void main(String[] args) {
        System.out.println("--- Testing Objective Function & Solution Creation ---");

        // 1. Manually create dummy locations to test the math without needing a file!
        // We use simple coordinates so the distances are clean integers (3, 4, or 5).
        com.aim.project.obr.instance.Location depot = new com.aim.project.obr.instance.Location(0, 0);
        com.aim.project.obr.instance.Location poi0 = new com.aim.project.obr.instance.Location(0, 4);  // Distance from depot: 4
        com.aim.project.obr.instance.Location poi1 = new com.aim.project.obr.instance.Location(3, 4);  // Distance from poi0: 3
        com.aim.project.obr.instance.Location poi2 = new com.aim.project.obr.instance.Location(3, 0);  // Distance from poi1: 4, to depot: 3

        com.aim.project.obr.instance.Location[] pois = {poi0, poi1, poi2};
        java.util.Random rng = new java.util.Random(12345L);

        // 2. Instantiate the instance
        OpenTopBusRoutingInstance instance = new OpenTopBusRoutingInstance(4, pois, depot, rng);
        com.aim.project.obr.interfaces.ObjectiveFunctionInterface objFunc = instance.getOBRObjectiveFunction();

        // 3. Test distance calculation directly
        System.out.println("Distance from Depot to PoI 0 (Expected 4): " + objFunc.getDistanceBetweenBusDepotAndPoI(0));
        System.out.println("Distance from PoI 0 to PoI 1 (Expected 3): " + objFunc.getCost(0, 1));
        System.out.println("Distance from PoI 1 to PoI 2 (Expected 4): " + objFunc.getCost(1, 2));

        // 4. Test Solution Creation
        System.out.println("\nGenerating RANDOM Solution...");
        com.aim.project.obr.interfaces.OBRSolutionInterface solution = instance.createSolution(com.aim.project.obr.instance.InitialisationMode.RANDOM);

        if (solution != null && solution.getSolutionRepresentation() != null) {
            int[] route = solution.getSolutionRepresentation().getSolutionRepresentation();
            System.out.print("Route Order: ");
            for (int id : route) {
                System.out.print(id + " ");
            }
            System.out.println("\nObjective Value: " + solution.getObjectiveFunctionValue());

            // Note: A route of [0, 1, 2] would have a score of:
            // Depot->0 (4) + 0->1 (3) + 1->2 (4) + 2->Depot (3) = 14.
            // A shuffled route might be [2, 0, 1] which is 3 + 5 + 3 + 5 = 16 (since the diagonal is 5).
        } else {
            System.out.println("Solution or Representation is null. Implement createSolution to fix this!");
        }
    }
}
