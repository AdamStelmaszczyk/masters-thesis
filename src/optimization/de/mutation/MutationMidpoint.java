package optimization.de.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** DE/mid/k */
public class MutationMidpoint extends Mutation
{
	private Solution midpoint;

	public MutationMidpoint(int NP)
	{
		super(NP);
	}

	@Override
	public double computeScalingFactor()
	{
		return Math.sqrt((1 + 2 * DE.F * DE.F - 1.0 / NP) / (2 * DE.K));
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			midpoint = pop.computeMidpoint();
		}
		final int INDICES_SIZE = 2 * DE.K + 1;
		final List<Integer> indices = new ArrayList<Integer>(INDICES_SIZE);
		indices.add(i);
		for (int j = 1; j < INDICES_SIZE; j++)
		{
			indices.add(DE.getRandomIndex(rand, NP, indices));
		}
		final Solution sum = pop.solutions[indices.get(1)].minus(pop.solutions[indices.get(2)]);
		for (int j = 3; j < INDICES_SIZE; j += 2)
		{
			sum.plus(pop.solutions[indices.get(j)]).minus(pop.solutions[indices.get(j + 1)]);
		}
		final Solution diffVector = sum.mul(computeScalingFactor());
		return midpoint.plus(diffVector);
	}
}
