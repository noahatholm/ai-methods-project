package main.java.com.aim.project.obr.instance;


import java.util.ArrayList;
import java.util.Random;

import main.java.com.aim.project.obr.OBRObjectiveFunction;
import main.java.com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
import main.java.com.aim.project.obr.interfaces.OBRInstanceInterface;
import main.java.com.aim.project.obr.interfaces.OBRSolutionInterface;
import main.java.com.aim.project.obr.solution.OBRSolution;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OpenTopBusRoutingInstance implements OBRInstanceInterface {

    private final int m_iNumberOfLocations;
    private final Location[] m_aoLocations;
    private final Location m_oBusDepotLocation;
    private final Random m_oRandom;
    private final ObjectiveFunctionInterface m_oObjectiveFunction;

    public OpenTopBusRoutingInstance(int iNumberOfLocations, Location[] aoLocations, Location oBusDepotLocation, Random oRandom) {

        this.m_iNumberOfLocations = iNumberOfLocations;
        this.m_aoLocations = aoLocations;
        this.m_oBusDepotLocation = oBusDepotLocation;
        this.m_oRandom = oRandom;
        this.m_oObjectiveFunction = new OBRObjectiveFunction(this);
    }

	@Override
	public OBRSolution createSolution(InitialisationMode oMode) {

        // TODO
        return null;
    }

    @Override
	public ObjectiveFunctionInterface getOBRObjectiveFunction() {

        // TODO
		return null;
	}

	@Override
	public int getNumberOfLocations() {

        // TODO
        return -1;
	}

	@Override
	public Location getLocationForPoI(int iPointOfInterestId) {

        // TODO
        return null;
	}

	@Override
	public Location getLocationOfBusDepot() {

        // TODO
        return null;
	}

	@Override
	public ArrayList<Location> getSolutionAsListOfLocations(OBRSolutionInterface oSolution) {

        // TODO
        return null;
    }
}
