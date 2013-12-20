package optimization.de.mutation;

import javabbob.Main;
import optimization.Solution;
import optimization.de.DE;
import optimization.de.Population;
import Jama.EigenvalueDecomposition;
import Jama.Matrix;

/** P[i1] + sqrt(2) * F * v */
public class MutationRandInf extends Mutation
{
	protected Matrix a;
	protected Matrix z;
	protected Matrix diffVector;
	protected Solution diffVectorSolution;

	public MutationRandInf()
	{
		super(0);
	}

	@Override
	public double computeScalingFactor()
	{
		return Math.sqrt(2) * DE.F;
	}

	@Override
	public Solution getMutant(Population pop, int i)
	{
		if (i == 0)
		{
			if (a == null)
			{
				allocateArrays(pop.DIM);
			}
			computeA(pop);
		}
		final Solution diffVector = computeDiffVector(pop);
		return pop.getRandom().plus(diffVector);
	}

	protected void allocateArrays(int DIM)
	{
		a = new Matrix(DIM, DIM);
		z = new Matrix(DIM, 1);
		diffVector = new Matrix(DIM, 1);
		diffVectorSolution = new Solution(DIM);
	}

	protected void computeA(Population pop)
	{
		final Matrix cov = pop.computeCovarianceMatrix();
		final EigenvalueDecomposition eigen = cov.eig();
		final Matrix v = eigen.getV();
		final Matrix d = eigen.getD();
		// Square root of a diagonal matrix = square roots on its diagonal
		for (int x = 0; x < d.getColumnDimension(); x++)
		{
			final double value = d.get(x, x);
			d.set(x, x, Math.sqrt(value));
		}
		a = v.times(d);
	}

	protected Solution computeDiffVector(Population pop)
	{
		for (int y = 0; y < pop.DIM; y++)
		{
			z.set(y, 0, Main.rand.nextGaussian());
		}
		diffVector = a.times(z);
		for (int x = 0; x < pop.DIM; x++)
		{
			diffVectorSolution.feat[x] = diffVector.get(x, 0) * F;
		}
		return diffVectorSolution;
	}
}
