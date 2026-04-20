package com.aim.project.obr.heuristics;

import com.aim.project.obr.interfaces.ObjectiveFunctionInterface;

import java.util.Random;

/**
 * <p>
 * This class is included (and all non-crossover heuristics subclass this class) to simplify your implementation, and it
 * is intended that you include any common operations in this class to simplify your implementation of the other heuristics.
 * Furthermore, if you implement and test common functionality here, it is less likely that you introduce a bug elsewhere!
 * <p>
 * For example, think about common neighbourhood operators and any other incremental changes that you might perform
 * while applying low-level heuristics.
 *
 * @author Warren G Jackson
 * @since 1.0.0 (15/03/2026)
 */
public class HeuristicOperators {

	protected final Random m_oRandom;

    protected ObjectiveFunctionInterface m_oObjectiveFunction;

	public HeuristicOperators(Random oRandom) {

		this.m_oRandom = oRandom;
	}

    public void setObjectiveFunction(ObjectiveFunctionInterface oObjectiveFunction) {

        this.m_oObjectiveFunction = oObjectiveFunction;
    }

    // TODO include any common operations here, for example setObjectiveFunction(...) as implemented above.
}
