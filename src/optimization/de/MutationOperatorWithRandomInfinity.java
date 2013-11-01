package optimization.de;

import java.util.Random;

import Jama.Matrix;

/** P[j] + sqrt(2) * F * v */
public class MutationOperatorWithRandomInfinity implements MutationOperator
{
	protected Matrix L;

	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			computeL(pop);
		}
		final int j = DE.getRandomIndex(rand, pop.size(), i, i, i);
		final Solution diffVector = computeDiffVector(pop, rand).mul(getScalingFactor(pop));
		return pop.solutions[j].plus(diffVector);
	}

	protected void computeL(Population pop)
	{
		L = pop.computeCovarianceMatrix().lu().getL();
	}

	protected Solution computeDiffVector(Population pop, Random rand)
	{
		final double[] vector = new double[pop.size()];
		for (int i = 0; i < pop.size(); i++)
		{
			vector[i] = L.get(i, 0) * rand.nextGaussian();
		}
		return new Solution(vector, pop.solutions[0].getFunEvalsCounter());
	}

	public double getScalingFactor(Population pop)
	{
		return Math.sqrt(2) * DE.F;
	}
}
