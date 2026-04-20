package main.java.com.aim.project.obr;

import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
import com.aim.project.obr.interfaces.OBRInstanceInterface;
import com.aim.project.obr.interfaces.SolutionRepresentationInterface;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OBRObjectiveFunction implements ObjectiveFunctionInterface {

    private final OBRInstanceInterface m_oInstance;

	public OBRObjectiveFunction(OBRInstanceInterface oInstance) {

        this.m_oInstance = oInstance;
	}

	@Override
	public int getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {

        // TODO
		return -1;
	}

	@Override
	public int getCost(int iLocationA, int iLocationB) {

        // TODO
        return -1;
	}

	@Override
	public int getDistanceBetweenBusDepotAndPoI(int iLocation) {

        // TODO
        return -1;
	}
}
