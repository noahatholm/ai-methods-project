package com.aim.project.obr;

import com.aim.project.obr.instance.Location;
import com.aim.project.obr.interfaces.*;

import AbstractClasses.ProblemDomain;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OBRDomain extends ProblemDomain implements Visualisable, InLabPracticalExamInterface {

    public OBRDomain(long lSeed) {

		super(lSeed);

        // TODO
        //  recommend to create the heuristics here
	}
	
	@Override
	public double applyHeuristic(int iHeuristicIndex, int iCurrentSolutionIndex, int iCandidateSolutionIndex) {

        // TODO
        return -1.f;
	}

	@Override
	public double applyHeuristic(int iHeuristicIndex, int iParent1Index, int iParent2Index, int iCandidateIndex) {

        // TODO
        return -1.f;
	}

	@Override
	public String bestSolutionToString() {

        // TODO
        return null;
	}

	@Override
	public boolean compareSolutions(int iSolutionIndexA, int iSolutionIndexB) {

        // TODO
        return false;
	}

	@Override
	public void copySolution(int iSolutionIndexFrom, int iSolutionIndexTo) {

        // TODO
        //  this should create a deep copy of the solution
	}

	@Override
	public double getBestSolutionValue() {

		// TODO
        return -1.f;
	}
	
	@Override
	public double getFunctionValue(int iSolutionIndex) {

        // TODO
        return -1.f;
	}

	@Override
	public int[] getHeuristicsOfType(HeuristicType oHeuristicType) {

        // TODO
        return null;
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
		
		// TODO
        return null;
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {

        // TODO
        return null;
	}

	@Override
	public int getNumberOfHeuristics() {

        // TODO
        //  Note needs hard coding due to the design of HyFlex.
        //  The ProblemDomain class calls this method upon initialisation.
		return 0;
	}

	@Override
	public int getNumberOfInstances() {

        // TODO
		return -1;
	}

	@Override
	public void initialiseSolution(int iSolutionIndex) {
		
        // TODO
        //  don't forget this might be the best solution found so far!
    }

	@Override
	public void loadInstance(int iInstanceId) {

        // TODO
        //  I recommend using <code>public URL getResource(String name)</code> and tag the folder with the
        //  instance files as a resource to help with project portability.

	}

	@Override
	public void setMemorySize(int iNewMemorySize) {

        // TODO
	}

	@Override
	public String solutionToString(int iSolutionIndex) {

        // TODO
        return null;
	}

	@Override
	public String toString() {

		return "Open-top Bus Routing";
	}
	
	private void updateBestSolution(int iSolutionIndex) {

        // TODO
        //  be careful that the reference is not saved otherwise we may modify it accidentally elsewhere in the code.
	}
	
	@Override
	public OBRInstanceInterface getLoadedInstance() {

		// TODO
		return null;
	}

	/**
	 * @return The integer array representing the ordering of the best solution.
	 */
	@Override
	public int[] getBestSolutionRepresentation() {

		// TODO
		return null;
	}

	@Override
	public Location[] getRouteOrderedByPoIs() {

		// TODO
		return null;
	}

	public OBRSolutionInterface getBestSolution() {

        // TODO
        return null;
	}

    /**
     * Should print the best solution found in the form:
     * (d_x,d_y) - (l_x0,l_y0) - ... - (l_x{n-1},l_y{n-1}) - (d_x,d_y)
     * where:
     * `d` is the (bus) depot
     * `l_xi` is the x-coordinate of the location in the i^th index in the solution.
     * `l_yi` is the y-coordinate of the location in the i^th index in the solution.
     * <p>
     * For example:
     * (0,0) - (1,1) - (2,2) - (3,3) - (4,4) - (0,0)
     */
    @Override
    public void printBestSolutionFound() {

        // TODO
    }

    /**
     * Prints the objective value of the best solution found.
     */
    @Override
    public void printObjectiveValueOfTheBestSolutionFound() {

        // TODO
    }

    /**
     * Should print the initial solution:
     * (d_x,d_y) - (l_x0,l_y0) - ... - (l_x{n-1},l_y{n-1}) - (d_x,d_y)
     * where:
     * `d` is the (bus) depot
     * `l_xi` is the x-coordinate of the location in the i^th index in the solution.
     * `l_yi` is the y-coordinate of the location in the i^th index in the solution.
     * <p>
     * For example:
     * (0,0) - (2,2) - (1,1) - (3,3) - (4,4) - (0,0)
     */
    @Override
    public void printInitialSolution() {

        // TODO
    }


    public static void main(String[] args) {
        System.out.println("--- Testing OBR Domain ---");

        // 1. Create the domain
        OBRDomain domain = new OBRDomain(12345L);

        // 2. Set memory size (needs to be at least 1 to store a solution)
        domain.setMemorySize(2);

        // 3. Load the square.obr instance (Instance ID 0)
        System.out.println("Loading instance 0...");
        domain.loadInstance(0);

        // 4. Initialise a solution at memory index 0
        System.out.println("Initialising a random solution...");
        domain.initialiseSolution(0);

        // 5. Print it out to see if it worked!
        System.out.println("Initial solution route:");
        domain.printInitialSolution();

        System.out.println("Objective Value (Total Distance): " + domain.getFunctionValue(0));
    }
}
