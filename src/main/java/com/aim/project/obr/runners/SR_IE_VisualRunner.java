package com.aim.project.obr.runners;


import com.aim.project.obr.hyperheuristics.SR_IE_HH;

import AbstractClasses.HyperHeuristic;

/**
 * This class extends the HH_Runner_Visual abstract class and provides a visual runner specifically for
 * the SR_IE_HH hyper-heuristic. It initialises the visual runner with an instance ID, constructs the
 * SR_IE_HH hyper-heuristic, and executes it. The best solution found is then displayed visually.
 *
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class SR_IE_VisualRunner extends HH_Runner_Visual {

	public SR_IE_VisualRunner(int instanceId) {

		super(instanceId);
	}

	@Override
	protected HyperHeuristic getHyperHeuristic(long lSeed) {

		return new SR_IE_HH(lSeed);
	}
	
	public static void main(String [] args) {

        int iInstanceId = 0;
		HH_Runner_Visual runner = new SR_IE_VisualRunner(iInstanceId);

		runner.run();
	}

}
