package optimization.de.mutation;

import java.util.Random;

import optimization.Solution;
import optimization.de.DE;
import optimization.de.Population;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/** P[i1] + sqrt(2) * F * v */
public class MutationRandInf extends Mutation
{
	protected Matrix a;
	protected double[] z;
	protected double[] diffVector;

	public MutationRandInf()
	{
		super(0);
	}

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
			if (a == null)
			{
				allocateArrays(pop.DIM);
			}
			computeA(pop);
		}
		computeIndices(pop, rand, i);
		final Solution diffVector = computeDiffVector(pop, rand).mul(computeScalingFactor(pop.size()));
		return pop.solutions[indices.get(1)].plus(diffVector);
	}

	protected void allocateArrays(int DIM)
	{
		a = new Matrix(DIM, DIM);
		z = new double[DIM];
		diffVector = new double[DIM];
	}

	protected void computeA(Population pop)
	{
		final double[][] c = pop.computeCovarianceMatrix();
		final EigenvalueDecomposition eigen = new EigenvalueDecomposition(new Matrix(c));
		final Matrix v = eigen.getV();
		final Matrix d = eigen.getD();
		for (int x = 0; x < pop.DIM; x++)
		{
			final double value = d.get(x, x);
			d.set(x, x, Math.sqrt(value));
		}
		a = v.times(d);
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
		return new Solution(diffVector);
	}
}
