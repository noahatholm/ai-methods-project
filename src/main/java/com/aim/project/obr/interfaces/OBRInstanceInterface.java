package com.aim.project.obr.interfaces;

import java.util.ArrayList;

import com.aim.project.obr.instance.InitialisationMode;
import com.aim.project.obr.instance.Location;
import com.aim.project.obr.solution.OBRSolution;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public interface OBRInstanceInterface {

	/**
	 * 
	 * @param oMode The initialisation oMode to use. Remember to handle both RANDOM and CONSTRUCTIVE methods.
	 * @return
	 */
	public OBRSolution createSolution(InitialisationMode oMode);
	
	/**
	 * 
	 * @return The objective function used to evaluate the current problem instance.
	 */
	public ObjectiveFunctionInterface getOBRObjectiveFunction();

    /**
     * Retrieves the total number of locations associated with the current instance.
     *
     * @return The total number of locations, which includes all points-of-interest and the bus depot.
     */
	public int getNumberOfLocations();

    /**
     * Retrieves the location associated with the specified point-of-interest ID.
     *
     * @param iPointOfInterestId The ID of the point-of-interest for which the location is to be retrieved.
     *                           An ID of 0 refers to the first point-of-interest in the instance file,
     *                           an ID of 1 refers to the second point-of-interest, and so on.
     * @return The Location corresponding to the given point-of-interest ID.
     */
	public Location getLocationForPoI(int iPointOfInterestId);

    /**
     * Retrieves the location of the bus depot for the current instance.
     *
     * @return The Location object representing the bus depot.
     */
	public Location getLocationOfBusDepot();

    /**
     * Converts the given solution into a list of locations (including all associated points-of-interest and the bus depot).
     *
     * @param oSolution The solution to be converted into a list of locations.
     * @return An ArrayList containing the locations in their respective order as represented in the solution.
     */
	public ArrayList<Location> getSolutionAsListOfLocations(OBRSolutionInterface oSolution);
}
