package optimization.de.mutation;

import java.util.List;
import java.util.Random;

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
	public Solution getMutant(Population pop, Random rand, int i)
	{
		final Solution diffVector = computeDiffVector(pop, rand, i);
		return pop.solutions[indices.get(1)].plus(diffVector);
	}

	protected Solution computeDiff(Population pop, int i, int j)
	{
		return pop.solutions[i].minus(pop.solutions[j]);
	}

	protected Solution computeDiffVector(Population pop, Random rand, int i)
	{
		computeIndices(pop, rand, i);
		final Solution sum = computeSum(pop, indices);
		return sum.mul(computeScalingFactor(pop.size()));
	}

	protected Solution computeSum(Population pop, List<Integer> indices)
	{
		final Solution sum = computeDiff(pop, indices.get(2), indices.get(3));
		for (int j = 4; j < INDICES_SIZE; j += 2)
		{
			sum.plus(computeDiff(pop, indices.get(j), indices.get(j + 1)));
		}
		return sum;
	}
}
