package optimization.de.mutation;

import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** P[j] + sqrt(2) * F * v */
public class MutationRandomInfinity extends Mutation
{
	private double[][] L;
	private final double[] z = new double[NP];
	private final double[] diffVector = new double[NP];

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
				diffVector[y] += L[y][x] * z[x];
			}
		}

		return new Solution(diffVector, pop.solutions[0].getFunEvalsCounter());
	}

	protected void computeL(Population pop)
	{
		L = pop.computeCovarianceMatrix();

		// Use a "left-looking", dot-product, Crout/Doolittle algorithm.
		int m = L.length;
		int n = L[0].length;

		int[] piv = new int[m];
		for (int i = 0; i < m; i++)
		{
			piv[i] = i;
		}
		int pivsign = 1;
		double[] LUrowi;
		double[] LUcolj = new double[m];

		// Outer loop.
		for (int j = 0; j < n; j++)
		{
			// Make a copy of the j-th column to localize references.
			for (int i = 0; i < m; i++)
			{
				LUcolj[i] = L[i][j];
			}

			// Apply previous transformations.
			for (int i = 0; i < m; i++)
			{
				LUrowi = L[i];

				// Most of the time is spent in the following dot product.
				int kmax = Math.min(i, j);
				double s = 0.0;
				for (int k = 0; k < kmax; k++)
				{
					s += LUrowi[k] * LUcolj[k];
				}

				LUrowi[j] = LUcolj[i] -= s;
			}

			// Find pivot and exchange if necessary.
			int p = j;
			for (int i = j + 1; i < m; i++)
			{
				if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p]))
				{
					p = i;
				}
			}
			if (p != j)
			{
				for (int k = 0; k < n; k++)
				{
					double t = L[p][k];
					L[p][k] = L[j][k];
					L[j][k] = t;
				}
				int k = piv[p];
				piv[p] = piv[j];
				piv[j] = k;
				pivsign = -pivsign;
			}

			// Compute multipliers.
			if (j < m & L[j][j] != 0.0)
			{
				for (int i = j + 1; i < m; i++)
				{
					L[i][j] /= L[j][j];
				}
			}
		}

		for (int i = 0; i < NP; i++)
		{
			L[i][i] = 1.0;
		}
	}
}
