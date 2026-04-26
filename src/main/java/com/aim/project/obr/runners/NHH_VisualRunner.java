package com.aim.project.obr.runners;

import AbstractClasses.HyperHeuristic;
import com.aim.project.obr.hyperheuristics.NHH;

public class NHH_VisualRunner extends HH_Runner_Visual {
    public NHH_VisualRunner(int iInstanceId) {
        super(iInstanceId);
    }

    @Override
    protected HyperHeuristic getHyperHeuristic(long lSeed) {
        return new NHH(lSeed);
    }

    public static void main(String [] args) {

        int iInstanceId = 0;
        HH_Runner_Visual runner = new NHH_VisualRunner(iInstanceId);

        runner.run();
    }
}
