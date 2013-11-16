package optimization.de.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** DE/rand/k */
public class MutationRand extends Mutation
{
	protected final int K;
	protected final int INDICES_SIZE;
	/** [ i, i1, i2, i3, ... ] */
	protected final List<Integer> indices;

	public MutationRand(int K)
	{
		this.K = K;
		INDICES_SIZE = 2 * K + 2;
		indices = new ArrayList<Integer>(INDICES_SIZE);
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

	protected Solution computeDiffVector(Population pop, Random rand, int i)
	{
		computeIndices(pop, rand, i);
		final Solution sum = computeSum(pop, indices);
		final Solution diffVector = sum.mul(computeScalingFactor(pop.size()));
		return diffVector;
	}

	protected void computeIndices(Population pop, Random rand, int i)
	{
		indices.clear();
		indices.add(i);
		for (int j = 1; j < INDICES_SIZE; j++)
		{
			indices.add(DE.getRandomIndex(rand, pop.size(), indices));
		}
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

	protected Solution computeDiff(Population pop, int i, int j)
	{
		return pop.solutions[i].minus(pop.solutions[j]);
	}
}
