package com.aim.project.obr;

import VRP.Solution;
import com.aim.project.obr.heuristics.*;
import com.aim.project.obr.instance.InitialisationMode;
import com.aim.project.obr.instance.Location;
import com.aim.project.obr.instance.reader.OBRInstanceReader;
import com.aim.project.obr.interfaces.*;

import AbstractClasses.ProblemDomain;
import com.aim.project.obr.solution.OBRSolution;
import com.aim.project.obr.solution.SolutionRepresentation;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Random;



/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class OBRDomain extends ProblemDomain implements Visualisable, InLabPracticalExamInterface {
    private HeuristicOperators[] heuristics;
    private CrossoverHeuristicInterface[] crossoverHeuristics;
    private OBRSolutionInterface initial_solution;
    private OBRSolutionInterface best_solution;
    private OBRSolutionInterface[] solution_memory;
    private OBRInstanceInterface instance;
    private OBRInstanceReaderInterface instance_reader;
    private ObjectiveFunctionInterface objective_function;

    public OBRDomain(long lSeed) {

		super(lSeed);
        //Create heuristics
        this.heuristics = new HeuristicOperators[5];

        heuristics[0] = new AdjacentSwap(rng);
        heuristics[1] = new Reinsertion(rng);
        heuristics[2] = new Inversion(rng);
        heuristics[3] = new NextDescent(rng);
        heuristics[4] = new DavissHillClimbing(rng);

        //Cross Over Heuristics
        this.crossoverHeuristics = new CrossoverHeuristicInterface[2];
        crossoverHeuristics[0] = new PartiallyMappedCrossover(rng); //Index heuristics.length
        crossoverHeuristics[1] = new OrderCrossover(rng); //Index heuristics.length + 1



        this.instance_reader = new OBRInstanceReader();



	}
	
	@Override
	public double applyHeuristic(int iHeuristicIndex, int iCurrentSolutionIndex, int iCandidateSolutionIndex) {
        HeuristicInterface heuristic = (HeuristicInterface) heuristics[iHeuristicIndex];

        copySolution(iCurrentSolutionIndex, iCandidateSolutionIndex);

        updateBestSolution(iCandidateSolutionIndex);
        double new_value = heuristic.apply(solution_memory[iCandidateSolutionIndex],depthOfSearch,intensityOfMutation);
        if (new_value < 0) throw new RuntimeException("Impossible Heuristic Objective Value from Heuristic ID" + iHeuristicIndex);
        return new_value;
	}

	@Override
	public double applyHeuristic(int iHeuristicIndex, int iParent1Index, int iParent2Index, int iCandidateIndex) {
        CrossoverHeuristicInterface heuristic = crossoverHeuristics[iHeuristicIndex-heuristics.length];

        OBRSolutionInterface p1 = solution_memory[iParent1Index];
        OBRSolutionInterface p2 = solution_memory[iParent2Index];

        OBRSolutionInterface child = p1.clone();
        solution_memory[iCandidateIndex] = child;
        updateBestSolution(iCandidateIndex);
        double new_value = heuristic.apply(p1,p2,child,depthOfSearch,intensityOfMutation);
        if (new_value < 0) throw new RuntimeException("Impossible Heuristic Objective Value from Heuristic ID" + iHeuristicIndex);
        return new_value;
	}

    private String solutionToString(OBRSolutionInterface oSolution) {
        StringBuilder str = new StringBuilder();
        Location depot = instance.getLocationOfBusDepot();
        int[] representation = oSolution.getSolutionRepresentation().getSolutionRepresentation();
        str = new StringBuilder(depot.toString() + " - ");
        for (int i : representation) {
            str.append(instance.getLocationForPoI(i).toString()).append(" - ");
        }
        str = new StringBuilder(depot.toString());
        return str.toString();
    }

	@Override
	public String bestSolutionToString() {
        return solutionToString(best_solution);
	}

	@Override
	public boolean compareSolutions(int iSolutionIndexA, int iSolutionIndexB) {

        // TODO
        return false;
	}

	@Override
	public void copySolution(int iSolutionIndexFrom, int iSolutionIndexTo) {
        solution_memory[iSolutionIndexTo] = solution_memory[iSolutionIndexFrom].clone();
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
        if (oHeuristicType == HeuristicType.CROSSOVER){
            int[] crossover = new int[crossoverHeuristics.length];
            for (int i = 0; i < crossover.length; i++) {
                crossover[i] = heuristics.length + i;
            }
            return crossover;
        }
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < heuristics.length; i++) {
            if (heuristics[i].getHeuristicType() ==  oHeuristicType) list.add(i);
        }
        int[] return_array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            return_array[i] = list.get(i);
        }
        return return_array;
	}

	@Override
	public int[] getHeuristicsThatUseDepthOfSearch() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < heuristics.length; i++) {
            HeuristicInterface heuristic = (HeuristicInterface) heuristics[i];
            if (heuristic.usesDepthOfSearch()) list.add(i);
        }
        for (int i = 0; i < crossoverHeuristics.length; i++) {
            if (crossoverHeuristics[i].usesDepthOfSearch()) list.add(i+heuristics.length);
        }
        int[] return_array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            return_array[i] = list.get(i);
        }
        return return_array;
	}

	@Override
	public int[] getHeuristicsThatUseIntensityOfMutation() {
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 0; i < heuristics.length; i++) {
            HeuristicInterface heuristic = (HeuristicInterface) heuristics[i];
            if (heuristic.usesIntensityOfMutation()) list.add(i);
        }
        for (int i = 0; i < crossoverHeuristics.length; i++) {
            if (crossoverHeuristics[i].usesIntensityOfMutation()) list.add(i+heuristics.length);
        }
        int[] return_array = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            return_array[i] = list.get(i);
        }
        return return_array;
	}

	@Override
	public int getNumberOfHeuristics() {
        return 7;
	}

	@Override
	public int getNumberOfInstances() {
        return 7;
	}


	@Override
	public void initialiseSolution(int iSolutionIndex) {
		OBRSolutionInterface solution = instance.createSolution(InitialisationMode.RANDOM);
        solution_memory[iSolutionIndex] = solution;
        //Check if its the best we've seen
        if (best_solution == null || solution.getObjectiveFunctionValue() < best_solution.getObjectiveFunctionValue()) updateBestSolution(iSolutionIndex);
        if (initial_solution == null)  initial_solution = solution.clone();
    }

	@Override
	public void loadInstance(int iInstanceId) {
        String resourceName;
        switch (iInstanceId) {
            case 0:
                resourceName = "instances/obr/carparks-40.obr";
                break;
            case 1:
                resourceName = "instances/obr/chatgpt-instance-100-Pois.obr";
                break;
            case 2:
                resourceName = "instances/obr/clustered-pois.obr";
                break;
            case 3:
                resourceName = "instances/obr/grid.obr";
                break;
            case 4:
                resourceName = "instances/obr/libraries-15.obr";
                break;
            case 5:
                resourceName = "instances/obr/square.obr";
                break;
            case 6:
                resourceName = "instances/obr/tramstops-85.obr";
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

        //Set Objective Function
        this.objective_function = new OBRObjectiveFunction(instance);
        heuristics[0].setObjectiveFunction(objective_function);
        heuristics[1].setObjectiveFunction(objective_function);
        heuristics[2].setObjectiveFunction(objective_function);
        heuristics[3].setObjectiveFunction(objective_function);
        heuristics[4].setObjectiveFunction(objective_function);
        crossoverHeuristics[0].setObjectiveFunction(objective_function);
        crossoverHeuristics[1].setObjectiveFunction(objective_function);
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
        if (solution_memory[iSolutionIndex] == null) {
            throw new RuntimeException("Tried to update Best Solution using Null Solution Index");
        }
        if (best_solution == null || solution_memory[iSolutionIndex].getObjectiveFunctionValue() < best_solution.getObjectiveFunctionValue()) {
            best_solution = solution_memory[iSolutionIndex].clone();
        }
    }
	
	@Override
	public OBRInstanceInterface getLoadedInstance() {
        return instance;
	}

	/**
	 * @return The integer array representing the ordering of the best solution.
	 */
	@Override
	public int[] getBestSolutionRepresentation() {
		return best_solution.getSolutionRepresentation().getSolutionRepresentation();
	}

	@Override
	public Location[] getRouteOrderedByPoIs() {
        ArrayList<Location> locationArrayList = instance.getSolutionAsListOfLocations(best_solution);
        Location[] locations = new Location[locationArrayList.size()];
        locations = locationArrayList.toArray(locations);
        return locations;
	}

	public OBRSolutionInterface getBestSolution() {
        return best_solution;
	}

    private void printSolution(OBRSolutionInterface solution) {
        Location depot = instance.getLocationOfBusDepot();
        int[] representation = solution.getSolutionRepresentation().getSolutionRepresentation();
        System.out.print(depot.toString() + " - ");
        for (int i : representation) {
            System.out.print(instance.getLocationForPoI(i).toString() + " - ");
        }
        System.out.println(depot.toString());
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
        printSolution(best_solution);
    }

    /**
     * Prints the objective value of the best solution found.
     */
    @Override
    public void printObjectiveValueOfTheBestSolutionFound() {
        System.out.println("Objective Value of Best Solution: " + best_solution.getObjectiveFunctionValue());
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
        printSolution(initial_solution);
    }
}
