package optimization.de.mutation;

import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** P[j] + F(P[k] - P[l]) */
public class MutationRandom extends Mutation
{
	public MutationRandom(int NP)
	{
		super(NP);
	}

	@Override
	public double computeScalingFactor()
	{
		return DE.F;
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		final int j = DE.getRandomIndex(rand, NP, i, i, i);
		final int k = DE.getRandomIndex(rand, NP, i, j, i);
		final int l = DE.getRandomIndex(rand, NP, i, j, k);
		final Solution diffVector = pop.solutions[k].minus(pop.solutions[l]).mul(SCALING_FACTOR);
		return pop.solutions[j].plus(diffVector);
	}
}
