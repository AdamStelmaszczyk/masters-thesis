package optimization.de.mutation;

import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** midpoint + sqrt(0.5 + F^2 - 0.5/n)(P[j] - P[k])) */
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
		return Math.sqrt(0.5 + DE.F * DE.F - 0.5 / NP);
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			midpoint = pop.computeMidpoint();
		}
		final int k = DE.getRandomIndex(rand, NP, i, i, i);
		final int l = DE.getRandomIndex(rand, NP, i, k, i);
		final Solution diffVector = pop.solutions[k].minus(pop.solutions[l]).mul(SCALING_FACTOR);
		return midpoint.plus(diffVector);
	}
}
