package com.aim.project.obr.runners;


import java.awt.Color;

import com.aim.project.obr.OBRDomain;
import com.aim.project.obr.instance.Location;
import com.aim.project.obr.solution.OBRSolution;
import com.aim.project.obr.visualiser.OpenTopBusRouteViewer;

import AbstractClasses.HyperHeuristic;

/**
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 *
 * Runs a hyper-heuristic using a default configuration, then displays the best solution found.
 */
public abstract class HH_Runner_Visual {

	private final int INSTANCE_ID;

	public HH_Runner_Visual(int iInstanceId) {

		this.INSTANCE_ID = iInstanceId;
	}
	
	public void run() {
		
		long lSeed = "COMP2001_Open-bus_Routing_Problem".hashCode();

        // set the runtime as 5 minutes
        // TODO this needs to be changed to reflect your machine's performance - see benchmarking in lab 8
		long lTimeLimit = 5 * 60 * 1000;

		OBRDomain oProblem = new OBRDomain(lSeed);
		oProblem.loadInstance(INSTANCE_ID);
		HyperHeuristic hh = getHyperHeuristic(lSeed);
		hh.setTimeLimit(lTimeLimit);
		hh.loadProblemDomain(oProblem);
		hh.run();
		
		System.out.println("f(s_best) = " + hh.getBestSolutionValue());
		new OpenTopBusRouteViewer(oProblem.getLoadedInstance(), oProblem, Color.RED, Color.GREEN);
		
	}
	
	/** 
	 * Transforms the best oSolution found, represented as an OBRSolution, into an ordering of Locations
	 * which the visualiser tool uses to draw the route.
	 */
	protected Location[] transformSolution(OBRSolution oSolution, OBRDomain oProblem) {
		
		return oProblem.getRouteOrderedByPoIs();
	}
	
	/**
	 * Allows a general visualiser runner by making the HyperHeuristic abstract.
	 * You can subclass this class to run any hyper-heuristic that you want.
	 */
	protected abstract HyperHeuristic getHyperHeuristic(long lSeed);
}
