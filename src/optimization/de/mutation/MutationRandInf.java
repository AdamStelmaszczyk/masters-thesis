package optimization.de.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Jama.EigenvalueDecomposition;
import Jama.Matrix;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** P[j] + sqrt(2) * F * v */
public class MutationRandInf extends Mutation
{
	protected Matrix a;
	protected double[] z;
	protected double[] diffVector;

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
			if (z == null)
			{
				allocateArrays(pop.DIM);
			}
			computeA(pop);
		}
		final List<Integer> indices = new ArrayList<Integer>(2);
		indices.add(i);
		indices.add(DE.getRandomIndex(rand, pop.size(), indices));
		final Solution diffVector = computeDiffVector(pop, rand).mul(computeScalingFactor(pop.size()));
		return pop.solutions[indices.get(1)].plus(diffVector);
	}

	protected void allocateArrays(int DIM)
	{
		a = new Matrix(DIM, DIM);
		z = new double[DIM];
		diffVector = new double[DIM];
	}

	protected Solution computeDiffVector(Population pop, Random rand)
	{
		for (int x = 0; x < pop.DIM; x++)
		{
			z[x] = rand.nextGaussian();
		}

		for (int y = 0; y < pop.DIM; y++)
		{
			diffVector[y] = 0.0;
			for (int x = 0; x <= y; x++)
			{
				diffVector[y] += a.get(y, x) * z[x];
			}
		}

		return new Solution(diffVector, pop.solutions[0].getFunEvalsCounter());
	}

	protected void computeA(Population pop)
	{
		double[][] c = pop.computeCovarianceMatrix();

		EigenvalueDecomposition eigen = new EigenvalueDecomposition(new Matrix(c));

		final Matrix V = eigen.getV();
		final Matrix D = eigen.getD();
		for (int i = 0; i < pop.DIM; i++)
		{
			double value = D.get(i, i);
			D.set(i, i, Math.sqrt(value));
		}

		a = V.times(D);
	}
}
