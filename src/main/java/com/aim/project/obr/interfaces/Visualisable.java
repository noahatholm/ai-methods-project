package main.java.com.aim.project.obr.interfaces;

import com.aim.project.obr.instance.Location;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public interface Visualisable {

	/**
	 * 
	 * @return The OBR route in visited location order.
	 */
	public Location[] getRouteOrderedByPoIs();
	
	/**
	 * 
	 * @return The problem instance that is currently loaded.
	 */
	public OBRInstanceInterface getLoadedInstance();

	/**
	 *
	 * @return The integer array representing the ordering of the best solution.
	 */
	public int[] getBestSolutionRepresentation();
}
