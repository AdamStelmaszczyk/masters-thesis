package optimization.de.mutation;

import optimization.Solution;
import optimization.de.Population;

public abstract class Mutation
{
	protected final int K;
	protected final double F;

	public Mutation(int k)
	{
		K = k;
		F = computeScalingFactor();
	}

	public abstract Solution getMutant(Population pop, int i);

	protected abstract double computeScalingFactor();
}
