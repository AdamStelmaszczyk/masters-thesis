package optimization.de.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** P[j] + sqrt(2) * F * v */
public class MutationRandInf extends Mutation
{
	private double[][] l;
	private double[] z;
	private double[] diffVector;

	@Override
	public double computeScalingFactor(int NP)
	{
		return Math.sqrt(2) * DE.F;
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			computeL(pop);
			if (z == null)
			{
				z = new double[pop.size()];
			}
			if (diffVector == null)
			{
				diffVector = new double[pop.size()];
			}
		}
		final List<Integer> indices = new ArrayList<Integer>(2);
		indices.add(i);
		indices.add(DE.getRandomIndex(rand, pop.size(), indices));
		final Solution diffVector = computeDiffVector(pop, rand).mul(computeScalingFactor(pop.size()));
		return pop.solutions[indices.get(1)].plus(diffVector);
	}

	protected Solution computeDiffVector(Population pop, Random rand)
	{
		for (int x = 0; x < pop.size(); x++)
		{
			z[x] = rand.nextGaussian();
		}

		for (int y = 0; y < pop.size(); y++)
		{
			diffVector[y] = 0.0;
			for (int x = 0; x <= y; x++)
			{
				diffVector[y] += l[y][x] * z[x];
			}
		}

		return new Solution(diffVector, pop.solutions[0].getFunEvalsCounter());
	}

	protected void computeL(Population pop)
	{
		l = pop.computeCovarianceMatrix();

		// Use a "left-looking", dot-product, Crout/Doolittle algorithm.
		final int NP = l.length;

		final int[] piv = new int[NP];
		for (int i = 0; i < NP; i++)
		{
			piv[i] = i;
		}
		int pivsign = 1;
		double[] LUrowi;
		final double[] LUcolj = new double[NP];

		// Outer loop.
		for (int j = 0; j < NP; j++)
		{
			// Make a copy of the j-th column to localize references.
			for (int i = 0; i < NP; i++)
			{
				LUcolj[i] = l[i][j];
			}

			// Apply previous transformations.
			for (int i = 0; i < NP; i++)
			{
				LUrowi = l[i];

				// Most of the time is spent in the following dot product.
				final int kmax = Math.min(i, j);
				double s = 0.0;
				for (int k = 0; k < kmax; k++)
				{
					s += LUrowi[k] * LUcolj[k];
				}

				LUrowi[j] = LUcolj[i] -= s;
			}

			// Find pivot and exchange if necessary.
			int p = j;
			for (int i = j + 1; i < NP; i++)
			{
				if (Math.abs(LUcolj[i]) > Math.abs(LUcolj[p]))
				{
					p = i;
				}
			}
			if (p != j)
			{
				for (int k = 0; k < NP; k++)
				{
					final double t = l[p][k];
					l[p][k] = l[j][k];
					l[j][k] = t;
				}
				final int k = piv[p];
				piv[p] = piv[j];
				piv[j] = k;
				pivsign = -pivsign;
			}

			// Compute multipliers.
			if (j < NP && l[j][j] != 0.0)
			{
				for (int i = j + 1; i < NP; i++)
				{
					l[i][j] /= l[j][j];
				}
			}
		}

		for (int i = 0; i < NP; i++)
		{
			l[i][i] = 1.0;
		}
	}
}
