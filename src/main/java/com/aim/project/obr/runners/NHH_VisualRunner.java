package com.aim.project.obr.runners;

import AbstractClasses.HyperHeuristic;

public class NHH_VisualRunner extends HH_Runner_Visual {
    public NHH_VisualRunner(int iInstanceId) {
        super(iInstanceId);
    }

    @Override
    protected HyperHeuristic getHyperHeuristic(long lSeed) {
        return null;
    }

    public static void main(String [] args) {

        int iInstanceId = 3;
        HH_Runner_Visual runner = new NHH_VisualRunner(iInstanceId);

        runner.run();
    }
}
