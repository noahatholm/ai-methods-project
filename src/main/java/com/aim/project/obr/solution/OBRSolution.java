package com.aim.project.obr.solution;

import com.aim.project.obr.OBRObjectiveFunction;
import com.aim.project.obr.instance.OpenTopBusRoutingInstance;
import com.aim.project.obr.interfaces.OBRSolutionInterface;
import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
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
}
