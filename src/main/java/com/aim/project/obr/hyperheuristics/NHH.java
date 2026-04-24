package com.aim.project.obr.hyperheuristics;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class NHH extends HyperHeuristic {
    public NHH(long lSeed) {
        super(lSeed);
    }

    @Override
    protected void solve(ProblemDomain problemDomain) {

    }

    @Override
    public String toString() {
        return "Noah's HyperHeuristics(NHH)";
    }
}
