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
        SolutionRepresentation representation = new SolutionRepresentation(locations_order);
        representation.setSolutionRepresentation(locations_order);
        if (oMode == InitialisationMode.RANDOM) { //Random Mode
            random_initialisation(locations_order);
        }
        else if (oMode == InitialisationMode.CONSTRUCTIVE) { //Constructive mode (OP)
            constructive_initialisation(locations_order);
        }
        return new OBRSolution(representation, m_oObjectiveFunction.getObjectiveFunctionValue(representation));
    }

    private void random_initialisation(int[] locations_order){
        for (int i = 0; i < locations_order.length; i++) locations_order[i] = i;

        for (int i = 0; i < locations_order.length - 1; i++) {
            int j = m_oRandom.nextInt(locations_order.length);
            int temp = locations_order[i];
            locations_order[i] = locations_order[j];
            locations_order[j] = temp;
        }
    }

    private void constructive_initialisation(int[] locations_order) {
        List<Integer> remaining = new ArrayList<>();
        for (int i = 0; i < m_iNumberOfLocations - 1; i++) remaining.add(i);

        //Start with closest node to depot
        int bestPoI = -1;
        int minDist = Integer.MAX_VALUE;
        for (int p : remaining) {
            int d = m_oObjectiveFunction.getDistanceBetweenBusDepotAndPoI(p);
            if (d < minDist) {
                minDist = d;
                bestPoI = p;
            }
        }

        List<Integer> tour = new ArrayList<>();
        tour.add(bestPoI);
        remaining.remove(Integer.valueOf(bestPoI));

        //Insert each remaining location into position where adds least cost
        while (!remaining.isEmpty()) {
            int bestToInsert = -1, bestIdx = -1;
            long bestDelta = Long.MAX_VALUE;

            for (int p : remaining) {
                for (int i = 0; i <= tour.size(); i++) {
                    int prev = (i == 0) ? -1 : tour.get(i - 1);
                    int next = (i == tour.size()) ? -1 : tour.get(i);

                    long added = (prev == -1) ? m_oObjectiveFunction.getDistanceBetweenBusDepotAndPoI(p) : m_oObjectiveFunction.getCost(prev, p);
                    added += (next == -1) ? m_oObjectiveFunction.getDistanceBetweenBusDepotAndPoI(p) : m_oObjectiveFunction.getCost(p, next);
                    long removed = (prev == -1 || next == -1) ? 0 : m_oObjectiveFunction.getCost(prev, next);

                    long delta = added - removed;
                    if (delta < bestDelta) { bestDelta = delta; bestToInsert = p; bestIdx = i; }
                }
            }
            tour.add(bestIdx, bestToInsert);
            remaining.remove(Integer.valueOf(bestToInsert));
        }
        for (int i = 0; i < locations_order.length; i++) locations_order[i] = tour.get(i);
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
