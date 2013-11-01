package optimization.de;

import java.util.Random;

/** midpoint + sqrt(0.5 + F^2 - 0.5/n)(P[j] - P[k])) */
public class MutationOperatorWithMidpoint implements MutationOperator
{
	private Solution midpoint;

	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			midpoint = pop.computeMidpoint(pop);
		}
		final int k = DE.getRandomIndex(rand, pop.size(), i, i, i);
		final int l = DE.getRandomIndex(rand, pop.size(), i, k, i);
		final Solution diffVector = pop.solutions[k].minus(pop.solutions[l]).mul(getScalingFactor(pop));
		return midpoint.plus(diffVector);
	}

	public double getScalingFactor(Population pop)
	{
		return Math.sqrt(0.5 + DE.F * DE.F - 0.5 / pop.size());
	}
}
