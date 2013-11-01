package optimization.de.mutation;

import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;
import Jama.Matrix;

/** P[j] + sqrt(2) * F * v */
public class MutationRandomInfinity extends Mutation
{
	private final double[] z = new double[NP];
	private final double[] diffVector = new double[NP];

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
		for (int x = 0; x < NP; x++)
		{
			z[x] = rand.nextGaussian();
		}

		for (int y = 0; y < NP; y++)
		{
			diffVector[y] = 0.0;
			for (int x = 0; x <= y; x++)
			{
				diffVector[y] += L.get(y, x) * z[x];
			}
		}

		return new Solution(diffVector, pop.solutions[0].getFunEvalsCounter());
	}

	protected void computeL(Population pop)
	{
		L = pop.computeCovarianceMatrix().lu().getL();
	}
}
