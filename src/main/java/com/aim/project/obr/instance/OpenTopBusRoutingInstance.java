package com.aim.project.obr.instance;


import java.util.*;

import com.aim.project.obr.OBRObjectiveFunction;
import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
import com.aim.project.obr.interfaces.OBRInstanceInterface;
import com.aim.project.obr.interfaces.OBRSolutionInterface;
import com.aim.project.obr.solution.OBRSolution;
import com.aim.project.obr.solution.SolutionRepresentation;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OpenTopBusRoutingInstance implements OBRInstanceInterface {

    private final int m_iNumberOfLocations; //Including the depot
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
        int[] locations_order = new int[m_iNumberOfLocations -1];
        if (oMode == InitialisationMode.RANDOM) { //Random Mode
            for (int i = 0; i < locations_order.length; i++) locations_order[i] = i;

            for (int i = 0; i < locations_order.length - 1; i++) {
                int j = m_oRandom.nextInt(locations_order.length);
                int temp = locations_order[i];
                locations_order[i] = locations_order[j];
                locations_order[j] = temp;
            }

        }
        else if  (oMode == InitialisationMode.CONSTRUCTIVE) {//Constructive Mode


        }
        SolutionRepresentation representation = new SolutionRepresentation(locations_order);
        return new OBRSolution(representation, m_oObjectiveFunction.getObjectiveFunctionValue(representation));
    }

    @Override
	public ObjectiveFunctionInterface getOBRObjectiveFunction() {
        return m_oObjectiveFunction;
	}

	@Override
	public int getNumberOfLocations() {
        return m_iNumberOfLocations;
	}

	@Override
	public Location getLocationForPoI(int iPointOfInterestId) {
        return m_aoLocations[iPointOfInterestId];
	}

	@Override
	public Location getLocationOfBusDepot() {
        return m_oBusDepotLocation;
	}

	@Override
	public ArrayList<Location> getSolutionAsListOfLocations(OBRSolutionInterface oSolution) {
        ArrayList<Location> locations_list = new ArrayList<Location>();
        for (int location: oSolution.getSolutionRepresentation().getSolutionRepresentation()){
            locations_list.add(m_aoLocations[location]);
        }
        return locations_list;
    }
}
