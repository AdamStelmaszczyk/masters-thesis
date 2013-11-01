package optimization.de.mutation;

import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;
import Jama.Matrix;

/** P[j] + sqrt(2) * F * v */
public class MutationRandomInfinity extends Mutation
{
	protected Matrix L;

	public MutationRandomInfinity(int NP)
	{
		super(NP);
	}

	@Override
	public double computeScalingFactor()
	{
		return Math.sqrt(2) * DE.F;
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			computeL(pop);
		}
		final int j = DE.getRandomIndex(rand, NP, i, i, i);
		final Solution diffVector = computeDiffVector(pop, rand).mul(SCALING_FACTOR);
		return pop.solutions[j].plus(diffVector);
	}

	protected Solution computeDiffVector(Population pop, Random rand)
	{
		final double[] vector = new double[NP];
		for (int i = 0; i < NP; i++)
		{
			vector[i] = L.get(i, 0) * rand.nextGaussian();
		}
		return new Solution(vector, pop.solutions[0].getFunEvalsCounter());
	}

	protected void computeL(Population pop)
	{
		L = pop.computeCovarianceMatrix().lu().getL();
	}
}
