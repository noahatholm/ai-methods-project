package com.aim.project.obr.solution;

import com.aim.project.obr.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class SolutionRepresentation implements SolutionRepresentationInterface {
    private int[] OBR_representation;

	public SolutionRepresentation(int[] aiRepresentation) {
        this.OBR_representation = aiRepresentation;
	}

	@Override
	public int[] getSolutionRepresentation() {
        return OBR_representation;
	}

	@Override
	public void setSolutionRepresentation(int[] aiSolutionRepresentation) {
        OBR_representation = aiSolutionRepresentation;
	}

	@Override
	public int getTotalNumberOfLocations() {
        return OBR_representation.length;
	}

	@Override
	public SolutionRepresentationInterface clone() {
        return new SolutionRepresentation(OBR_representation.clone());
	}
}
