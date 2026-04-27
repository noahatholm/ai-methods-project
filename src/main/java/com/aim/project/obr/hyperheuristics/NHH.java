package com.aim.project.obr.hyperheuristics;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import com.aim.project.obr.OBRDomain;
import com.aim.project.obr.SolutionPrinter;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

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

        long last_improvement_time = 0;
        int last_heuristic = 0;
        double best_cost = current_cost; // Track the all time best

        while(!hasTimeExpired()) {
            //Use choice function to pick next heuristic
            int iHeuristicId = choiceFunction.heuristicChoiceFunction(last_heuristic);

            long startTime = System.nanoTime(); //track how long it takes to tax using lots of compute

            if (iHeuristicId >= crossOverIndex) {
                int randomParentIndex = rng.nextInt(memorySize-2) + 2;
                candidate_cost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolution, randomParentIndex, iCandidateSolution); //apply crossover
            } else {
                candidate_cost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolution, iCandidateSolution); //apply mutation or hill climb
            }
            long endTime = System.nanoTime();



            // have to calc my own ms time cuz the milliseconds is buggy on my laptop
            double cpuTimeMs = Math.max(0.001, (endTime - startTime) / 1_000_000.0);

            double difference = candidate_cost - current_cost; //calculate delta
            //Update the choice function so it "learns"
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
                    last_improvement_time = getElapsedTime();
                    System.out.println("Found better cost! " + best_cost);

                    //Add this solution to  a random parent slot
                    int randomSlot = rng.nextInt(memorySize - 2) + 2;
                    oProblem.copySolution(iCandidateSolution, randomSlot);
                }
            }

            simulatedAnnealing.advanceTemperature(getElapsedTime());
            choiceFunction.updateWeights(getElapsedTime(), getTimeLimit());
            updateDOSAndIOM(oProblem);

            //If no improvement has been made in a while likely stuck in local min
            if (getElapsedTime() - last_improvement_time > 10) {
                simulatedAnnealing.reheat(); //Reset the SA rate
                //Generate a very good solution for current to help add so good genetics
                ((OBRDomain) oProblem).initaliseConstructiveRandomSolution(iCurrentSolution);

                //Add some diversity to population
                for (int i = 2; i < (memorySize / 2 + 1); i++) oProblem.initialiseSolution(i);
                last_improvement_time = getElapsedTime();
                System.out.println("No improvedment for 10 seconds reheating a bit");
                System.out.println(simulatedAnnealing.toString());
            }
        }



        OBRSolutionInterface oSolution = ((OBRDomain) oProblem).getBestSolution();
        SolutionPrinter oSolutionPrinter = new SolutionPrinter("NHH-out.csv");
        oSolutionPrinter.printSolution( ((OBRDomain) oProblem).getLoadedInstance().getSolutionAsListOfLocations(oSolution));
    }

    //Adjusts the DOS and IOM over time to hopefully get better results
    private void updateDOSAndIOM(ProblemDomain oProblem) {
        double ratio = (double) getElapsedTime() / getTimeLimit();
        oProblem.setIntensityOfMutation(0.2 + (0.6 * ratio));
        oProblem.setDepthOfSearch(0.2 + (0.8 * ratio));
    }

    @Override
    public String toString() {
        return "Noah's HyperHeuristics(NHH)";
    }
}
