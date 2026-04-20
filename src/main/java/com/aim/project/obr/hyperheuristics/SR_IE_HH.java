package com.aim.project.obr.hyperheuristics;


import com.aim.project.obr.OBRDomain;
import com.aim.project.obr.SolutionPrinter;
import com.aim.project.obr.interfaces.OBRSolutionInterface;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

import java.util.Arrays;

/**
 * This class extends the HyperHeuristic framework and implements a specific type of hyper-heuristic (SR_IE_HH).
 * The goal of this hyper-heuristic is to iteratively explore the solution space by applying random heuristic operators
 * and employing an improving-or-equal move acceptance criterion. This is a very basic non-learning hyper-heuristic.
 *
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class SR_IE_HH extends HyperHeuristic {

	private static final int SECOND_PARENT_INDEX = 2;

	private static final int BEST_ACCEPTED_INDEX = 3;

	public SR_IE_HH(long lSeed) {
		
		super(lSeed);
	}

	@Override
	protected void solve(ProblemDomain oProblem) {

		oProblem.setMemorySize(4);

		int iCurrentSolutionIndex = 0;
		int iCandidateSolutionIndex = 1;
		oProblem.initialiseSolution(iCurrentSolutionIndex);
        hasTimeExpired();

		oProblem.copySolution(iCurrentSolutionIndex, BEST_ACCEPTED_INDEX);

		double dCurrentCost = oProblem.getFunctionValue(iCurrentSolutionIndex);
		int iNumberOfHeuristics = oProblem.getNumberOfHeuristics();

		// cache indices of crossover heuristics
		boolean[] abIsCrossover = new boolean[iNumberOfHeuristics];
		Arrays.fill(abIsCrossover, false);

		for(int i : oProblem.getHeuristicsOfType(ProblemDomain.HeuristicType.CROSSOVER)) {

			abIsCrossover[i] = true;
		}

		// main search loop
		double dCandidateCost;
		while(!hasTimeExpired()) {

			int iHeuristicId = rng.nextInt(iNumberOfHeuristics);
			if(abIsCrossover[iHeuristicId]) {

				if(rng.nextBoolean()) {
					// randomly choose between crossover with a newly initialised solution
					oProblem.initialiseSolution(SECOND_PARENT_INDEX);
					dCandidateCost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolutionIndex, SECOND_PARENT_INDEX, iCandidateSolutionIndex);
				} else {
					// or with the best solution accepted so far
					dCandidateCost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolutionIndex, BEST_ACCEPTED_INDEX, iCandidateSolutionIndex);
				}
			} else {
				dCandidateCost = oProblem.applyHeuristic(iHeuristicId, iCurrentSolutionIndex, iCandidateSolutionIndex);
			}

			// update the best solution for use with crossover operators
			if(dCandidateCost < dCurrentCost) {

				oProblem.copySolution(iCandidateSolutionIndex, BEST_ACCEPTED_INDEX);
			}

			// accept candidate solutions using the improving or equal move acceptance method
			if(dCandidateCost <= dCurrentCost) {

				dCurrentCost = dCandidateCost;

                // swapping indices saves having to do a deep copy of the solution
				iCurrentSolutionIndex = 1 - iCurrentSolutionIndex;
				iCandidateSolutionIndex = 1 - iCandidateSolutionIndex;
			}
		}

		
		OBRSolutionInterface oSolution = ((OBRDomain) oProblem).getBestSolution();
		SolutionPrinter oSolutionPrinter = new SolutionPrinter("sr-ie-hh-out.csv");
		oSolutionPrinter.printSolution( ((OBRDomain) oProblem).getLoadedInstance().getSolutionAsListOfLocations(oSolution));
	}

	@Override
	public String toString() {

		return "SR_IE_HH";
	}
}
