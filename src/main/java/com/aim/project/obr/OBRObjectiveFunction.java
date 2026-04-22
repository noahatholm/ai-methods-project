package com.aim.project.obr;

import com.aim.project.obr.instance.Location;
import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;
import com.aim.project.obr.interfaces.OBRInstanceInterface;
import com.aim.project.obr.interfaces.SolutionRepresentationInterface;

import java.util.Arrays;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OBRObjectiveFunction implements ObjectiveFunctionInterface {

    private final OBRInstanceInterface m_oInstance;
    private final int depotIndex;
    private int[][] cache; //Caches distance calculations for better performance (since there's no rules on memory efficiency)

	public OBRObjectiveFunction(OBRInstanceInterface oInstance) {

        this.m_oInstance = oInstance;
        int numberOfLocations = m_oInstance.getNumberOfLocations();
        //Initialise cache
        this.cache = new int[numberOfLocations][numberOfLocations];
        this.depotIndex = numberOfLocations-1;

        for (int i = 0; i < numberOfLocations; i++) {
            Arrays.fill(cache[i], -1);
        }
	}

    @Override
    public int getObjectiveFunctionValue(SolutionRepresentationInterface oSolution) {
        int[] route = oSolution.getSolutionRepresentation();
        int length = route.length;

        int cost = getDistanceBetweenBusDepotAndPoI(route[0]);

        for (int i = 1; i < length; i++) {
            cost += getCost(route[i - 1], route[i]);
        }
        cost += getDistanceBetweenBusDepotAndPoI(route[length - 1]);

        return cost;
    }

	@Override
	public int getCost(int iLocationA, int iLocationB) {
        if (iLocationA == -1 || iLocationA == m_oInstance.getNumberOfLocations() -1) return getDistanceBetweenBusDepotAndPoI(iLocationB);
        if (iLocationB == -1 || iLocationB == m_oInstance.getNumberOfLocations() - 1) return getDistanceBetweenBusDepotAndPoI(iLocationA);

        if (cache[iLocationA][iLocationB] == -1) {
            Location locationA = m_oInstance.getLocationForPoI(iLocationA);
            Location locationB = m_oInstance.getLocationForPoI(iLocationB);
            int distance = (int) Math.ceil(Math.sqrt(Math.pow(locationA.x() - locationB.x(),2) + Math.pow(locationA.y() - locationB.y(),2)));
            cache[iLocationB][iLocationA] = distance;
            cache[iLocationA][iLocationB] = distance; //Distance is symmetrical
        }


        return cache[iLocationA][iLocationB];
	}

	@Override
	public int getDistanceBetweenBusDepotAndPoI(int iLocation) {
        if (cache[iLocation][depotIndex] == -1) {
            Location locationA = m_oInstance.getLocationOfBusDepot();
            Location locationB = m_oInstance.getLocationForPoI(iLocation);

            cache[iLocation][depotIndex] = (int) Math.ceil(Math.sqrt(Math.pow(locationA.x() - locationB.x(), 2) + Math.pow(locationA.y() - locationB.y(), 2))); //This is only checked in one direction so doesn't need to be symmetrical
        }

        return cache[iLocation][depotIndex];
	}
}
