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
	public double computeScalingFactor(int NP)
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
		return sum.mul(computeScalingFactor(pop.size()));
	}

	protected Solution computeSum(Population pop)
	{
		double sum[] = new double[pop.DIM];
		for (int j = 0; j < K; j++)
		{
			final Solution diff = pop.getRandom().minus(pop.getRandom());
			for (int i = 0; i < sum.length; i++)
			{
				sum[i] += diff.feat[i];
			}
		}
		return new Solution(sum);
	}
}
