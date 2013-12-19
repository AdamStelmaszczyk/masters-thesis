package optimization.de.mutation;

import optimization.Solution;
import optimization.de.Population;

public abstract class Mutation
{
	protected final int K;

	public Mutation(int k)
	{
		K = k;
	}

	public abstract Solution getMutant(Population pop, int i);

	abstract double computeScalingFactor(int NP);
}
