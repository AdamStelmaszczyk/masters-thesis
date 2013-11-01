package optimization.de;

import java.util.Random;

import Jama.Matrix;

public class Population
{
	public Solution[] solutions;
	public final int DIM;

	public Population(int n, int dim, Random rand, FunEvalsCounter funEvals)
	{
		solutions = new Solution[n];
		for (int i = 0; i < n; i++)
		{
			solutions[i] = new Solution(dim, rand, funEvals);
		}
		DIM = dim;
	}

	/** @return Solutions in columns. */
	public double[][] getData()
	{
		final double[][] data = new double[DIM][solutions.length];
		for (int x = 0; x < DIM; x++)
		{
			for (int y = 0; y < solutions.length; y++)
			{
				data[x][y] = solutions[y].feat[x];
			}
		}
		return data;
	}

	public int size()
	{
		return solutions.length;
	}

	public Matrix computeCovarianceMatrix()
	{
		final double[][] covarianceMatrix = new double[solutions.length][solutions.length];
		for (int y = 0; y < solutions.length; y++)
		{
			for (int x = 0; x < solutions.length; x++)
			{
				covarianceMatrix[x][y] = solutions[x].cov(solutions[y]);
			}
		}
		return new Matrix(covarianceMatrix);
	}

	public Solution computeMidpoint(Population pop)
	{
		return computeSum(pop).mul(1.0 / pop.size());
	}

	private static Solution computeSum(Population pop)
	{
		final Solution sum = new Solution(pop.solutions[0]);
		for (int i = 1; i < pop.size(); i++)
		{
			for (int j = 0; j < pop.solutions[i].feat.length; j++)
			{
				sum.feat[j] += pop.solutions[i].feat[j];
			}
		}
		return sum;
	}
}
