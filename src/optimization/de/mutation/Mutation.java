package optimization.de.mutation;

import optimization.Evaluator;
import optimization.Solution;
import optimization.de.Population;

public abstract class Mutation
{
	/** Number of difference vectors. */
	protected final int K;
	/** Scaling factor. */
	protected final double F;

	public Mutation(int k)
	{
		K = k;
		F = computeScalingFactor();
	}

	public abstract Solution getMutant(Population pop, int i, Evaluator evaluator);

	protected abstract double computeScalingFactor();
}
