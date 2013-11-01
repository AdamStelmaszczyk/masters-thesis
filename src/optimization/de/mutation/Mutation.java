package optimization.de.mutation;

import java.util.Random;

import optimization.de.Population;
import optimization.de.Solution;

public abstract class Mutation
{
	protected final int NP;
	protected final double SCALING_FACTOR;

	public Mutation(int NP)
	{
		this.NP = NP;
		SCALING_FACTOR = computeScalingFactor();
	}

	public abstract Solution getMutant(Population pop, Random rand, int i);

	abstract double computeScalingFactor();
}
