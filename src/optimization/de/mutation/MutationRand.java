package optimization.de.mutation;

import optimization.Evaluator;
import optimization.Solution;
import optimization.de.DE;
import optimization.de.Population;

/** DE/rand/k */
public class MutationRand extends Mutation
{
	public MutationRand(int k)
	{
		super(k);
	}

	@Override
	public double computeScalingFactor()
	{
		return DE.F / Math.sqrt(K);
	}

	@Override
	public Solution getMutant(Population pop, int i, Evaluator evaluator)
	{
		return pop.getRandom().plus(computeDiffVector(pop));
	}

	protected Solution computeDiffVector(Population pop)
	{
		return computeSum(pop).mul(F);
	}

	protected Solution computeSum(Population pop)
	{
		final Solution sum = new Solution(pop.DIM, 0);
		for (int j = 0; j < K; j++)
		{
			sum.plusEquals(pop.getRandom().minus(pop.getRandom()));
		}
		return sum;
	}
}
