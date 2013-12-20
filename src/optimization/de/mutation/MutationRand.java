package optimization.de.mutation;

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
	public Solution getMutant(Population pop, int i)
	{
		final Solution diffVector = computeDiffVector(pop);
		return pop.getRandom().plus(diffVector);
	}

	protected Solution computeDiffVector(Population pop)
	{
		final Solution sum = computeSum(pop);
		return sum.mul(F);
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
