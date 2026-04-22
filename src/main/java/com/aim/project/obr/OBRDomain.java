package com.aim.project.obr;

import VRP.Solution;
import com.aim.project.obr.heuristics.*;
import com.aim.project.obr.instance.InitialisationMode;
import com.aim.project.obr.instance.Location;
import com.aim.project.obr.instance.reader.OBRInstanceReader;
import com.aim.project.obr.interfaces.*;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.solution.OBRSolution;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Random;



/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OBRDomain extends ProblemDomain implements Visualisable, InLabPracticalExamInterface {
    private HeuristicOperators[] heuristics;
    private CrossoverHeuristicInterface[] crossoverHeuristics;
    private OBRSolutionInterface best_solution;
    private OBRSolutionInterface[] solution_memory;
    private OBRInstanceInterface instance;
    private OBRInstanceReaderInterface instance_reader;

    public OBRDomain(long lSeed) {

		super(lSeed);
        //Create heuristics
        this.heuristics = new HeuristicOperators[4];

        heuristics[0] = new AdjacentSwap(rng);
        heuristics[1] = new Reinsertion(rng);
        heuristics[2] = new NextDescent(rng);
        heuristics[3] = new DavissHillClimbing(rng);

        this.crossoverHeuristics = new CrossoverHeuristicInterface[1];
        crossoverHeuristics[0] = new PartiallyMappedCrossover(rng);

        this.instance_reader = new OBRInstanceReader();



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
		return best_solution.getObjectiveFunctionValue();
	}
	
	@Override
	public double getFunctionValue(int iSolutionIndex) {
        return solution_memory[iSolutionIndex].getObjectiveFunctionValue();
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
		OBRSolutionInterface solution = instance.createSolution(InitialisationMode.RANDOM);
        solution_memory[iSolutionIndex] = solution;
        //Check if its the best we've seen
        if (best_solution == null || solution.getObjectiveFunctionValue() < best_solution.getObjectiveFunctionValue()) best_solution = solution;
    }

	@Override
	public void loadInstance(int iInstanceId) {
        String resourceName;
        switch (iInstanceId) {
            case 0:
                resourceName = "instances/obr/carparks-40.obr";
                break;
            case 1:
                resourceName = "instances/obr/chatgpt-instance-100-Pols.obr/";
                break;
            case 2:
                resourceName = "instances/obr/clustered-pois.obr/";
                break;
            case 3:
                resourceName = "instances.obr/grid.obr/";
                break;
            case 4:
                resourceName = "instances.obr/libraries-15.obr/";
                break;
            case 5:
                resourceName = "instances.obr/square.obr/";
                break;
            case 6:
                resourceName = "instances.obr/tramstops-85.obr/";
                break;
            default:
                throw new RuntimeException("Invalid Instance ID");
        }
        URL url = getClass().getClassLoader().getResource(resourceName);

        if (url == null) {
            throw new RuntimeException("Could not find resource: " + resourceName);
        }

        Path instancePath = null;

        try {
            instancePath = Path.of(url.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
        instance = instance_reader.readOBRInstanceFile(instancePath,rng);
	}

	@Override
	public void setMemorySize(int iNewMemorySize) {
        solution_memory = new OBRSolution[iNewMemorySize];
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
        return best_solution;
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
