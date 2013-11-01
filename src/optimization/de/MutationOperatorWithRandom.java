package optimization.de;

import java.util.Random;

/** P[j] + F(P[k] - P[l]) */
public class MutationOperatorWithRandom implements MutationOperator
{
	public Solution getMutant(Population pop, Random rand, int i)
	{
		final int j = DE.getRandomIndex(rand, pop.size(), i, i, i);
		final int k = DE.getRandomIndex(rand, pop.size(), i, j, i);
		final int l = DE.getRandomIndex(rand, pop.size(), i, j, k);
		final Solution diffVector = pop.solutions[k].minus(pop.solutions[l]).mul(getScalingFactor(pop));
		return pop.solutions[j].plus(diffVector);
	}

	public double getScalingFactor(Population pop)
	{
		return DE.F;
	}
}
