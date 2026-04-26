package com.aim.project.obr.hyperheuristics;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.aim.project.obr.OBRDomain;
import com.aim.project.obr.SolutionPrinter;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

import java.util.Arrays;

public class NHH extends HyperHeuristic {

    private double current_cost;
    private double candidate_cost;
    private int numberOfheuristics;

    private ChoiceFunction choiceFunction;

    public NHH(long lSeed) {
        super(lSeed);
    }





    @Override
    protected void solve(ProblemDomain oProblem) {
        //Initialise
        int memorySize = 10;
        oProblem.setMemorySize(memorySize);

        int iCurrentSolution = 0;
        int iCandidateSolution = 1;
        //3-9 are the population

        // Set up current
        oProblem.initialiseSolution(iCurrentSolution);
        current_cost = oProblem.getFunctionValue(iCurrentSolution);

        // Create rest of population
        for (int i = 2; i < memorySize; i++) {
            oProblem.initialiseSolution(i);
        }

        numberOfheuristics = oProblem.getNumberOfHeuristics();
        int crossOverIndex = numberOfheuristics - oProblem.getHeuristicsOfType(ProblemDomain.HeuristicType.CROSSOVER).length;

        choiceFunction = new ChoiceFunction(numberOfheuristics,rng);
        int last_heuristic = 0;

        while(!hasTimeExpired()) {
            int iHeuristicId = choiceFunction.heuristicChoiceFunction(last_heuristic); //use choice function to get next heuristic
            //System.out.println("Heuristic ID:" + iHeuristicId);
            double cpuTime;
            long startTime = System.nanoTime();
            if (iHeuristicId >= crossOverIndex) { //Check if crossover heuristic
                //For now will select best solution
                int randomParentIndex = rng.nextInt(8) + 2;
                candidate_cost = oProblem.applyHeuristic(iHeuristicId,iCurrentSolution,randomParentIndex,iCandidateSolution);
            } else {
                //System.out.println("Heuristic ID" + iHeuristicId + " current solution id " + iCurrentSolution + " candidate solution id " + iCandidateSolution);
                candidate_cost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolution, iCandidateSolution);
            }
            long endTime = System.nanoTime();
            cpuTime = (endTime - startTime);


            //Update choice function
            //System.out.println(cpuTime);
            double difference = candidate_cost - current_cost;
            choiceFunction.update_heuristic_performance(iHeuristicId, difference, cpuTime);
            choiceFunction.update_order_performance(iHeuristicId,last_heuristic,difference,cpuTime);
            choiceFunction.update_starvation_counter(iHeuristicId);
            last_heuristic = iHeuristicId;


            //Move acceptance currently IE
            //TODO simulated annealing
            if (candidate_cost <= current_cost) {
                if (candidate_cost < current_cost) {
                    System.out.println("Found better cost! " + candidate_cost);
                }
                current_cost = candidate_cost;
                oProblem.copySolution(iCandidateSolution, iCurrentSolution);
            }
        }

        OBRSolutionInterface oSolution = ((OBRDomain) oProblem).getBestSolution();
        SolutionPrinter oSolutionPrinter = new SolutionPrinter("NHH-out.csv");
        oSolutionPrinter.printSolution( ((OBRDomain) oProblem).getLoadedInstance().getSolutionAsListOfLocations(oSolution));

    }

    @Override
    public String toString() {
        return "Noah's HyperHeuristics(NHH)";
    }
}
