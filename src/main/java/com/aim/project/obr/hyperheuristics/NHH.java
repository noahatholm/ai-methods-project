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
    private SimulatedAnnealing simulatedAnnealing;

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
        simulatedAnnealing = new SimulatedAnnealing(current_cost,getTimeLimit());
        int last_heuristic = 0;
        double best_cost = current_cost; // Track the all time best

        while(!hasTimeExpired()) {
            int iHeuristicId = choiceFunction.heuristicChoiceFunction(last_heuristic);

            long startTime = System.nanoTime();

            if (iHeuristicId >= crossOverIndex) {
                int randomParentIndex = rng.nextInt(memorySize-2) + 2;
                candidate_cost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolution, randomParentIndex, iCandidateSolution);
            } else {
                candidate_cost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolution, iCandidateSolution);
            }

            long endTime = System.nanoTime();

            // have to calc my own ms time cuz the milliseconds is buggy on my laptop
            double cpuTimeMs = Math.max(0.001, (endTime - startTime) / 1_000_000.0);

            double difference = candidate_cost - current_cost;
            choiceFunction.update_heuristic_performance(iHeuristicId, difference, cpuTimeMs);
            choiceFunction.update_order_performance(iHeuristicId, last_heuristic, difference, cpuTimeMs);
            choiceFunction.update_starvation_counter(iHeuristicId);

            last_heuristic = iHeuristicId;

            // SA acceptance logic
            boolean accepted = false;

            if (candidate_cost <= current_cost) {
                accepted = true;
            } else {
                if (rng.nextDouble() < simulatedAnnealing.get_acceptance_probability(difference)) {
                    accepted = true;
                }
            }

            if (accepted) {
                current_cost = candidate_cost;
                oProblem.copySolution(iCandidateSolution, iCurrentSolution);

                // Keep population healthy
                if (candidate_cost < best_cost) {
                    best_cost = candidate_cost;
                    System.out.println("Found better cost! " + best_cost);

                    int randomSlot = rng.nextInt(memorySize - 2) + 2;
                    oProblem.copySolution(iCurrentSolution, randomSlot);
                }
            }

            simulatedAnnealing.advanceTemperature(getElapsedTime());
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
