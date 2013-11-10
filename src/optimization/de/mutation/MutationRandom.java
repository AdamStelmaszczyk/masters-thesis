package optimization.de.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** DE/rand/k */
public class MutationRandom extends Mutation
{
	public MutationRandom(int NP)
	{
		super(NP);
	}

	@Override
	public double computeScalingFactor()
	{
		return DE.F / Math.sqrt(DE.K);
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		final int INDICES_SIZE = 2 * DE.K + 2;
		final List<Integer> indices = new ArrayList<Integer>(INDICES_SIZE);
		indices.add(i);
		for (int j = 1; j < INDICES_SIZE; j++)
		{
			indices.add(DE.getRandomIndex(rand, NP, indices));
		}
		final Solution sum = pop.solutions[indices.get(2)].minus(pop.solutions[indices.get(3)]);
		for (int j = 4; j < INDICES_SIZE; j += 2)
		{
			sum.plus(pop.solutions[indices.get(j)]).minus(pop.solutions[indices.get(j + 1)]);
		}
		final Solution diffVector = sum.mul(computeScalingFactor());
		return pop.solutions[indices.get(1)].plus(diffVector);
	}
}
