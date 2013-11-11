package optimization.de;

import java.util.Random;

public class Population
{
	private final double[][] covarianceMatrix;

	public final Solution[] solutions;
	public final int DIM;

	public Population(int NP, int dim, Random rand, FunEvalsCounter funEvals)
	{
		covarianceMatrix = new double[NP][NP];
		solutions = new Solution[NP];
		for (int i = 0; i < NP; i++)
		{
			solutions[i] = new Solution(dim, rand, funEvals);
		}
		DIM = dim;
	}

	public double[][] computeCovarianceMatrix()
	{
		for (int y = 0; y < solutions.length; y++)
		{
			for (int x = 0; x <= y; x++)
			{
				covarianceMatrix[x][y] = solutions[x].cov(solutions[y]);
			}
		}
		for (int y = 0; y < solutions.length; y++)
		{
			for (int x = y + 1; x < solutions.length; x++)
			{
				covarianceMatrix[x][y] = covarianceMatrix[y][x];
			}
		}
		return covarianceMatrix;
	}

	public Solution computeMidpoint()
	{
		return computeSum().mul(1.0 / solutions.length);
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

	private Solution computeSum()
	{
		final Solution sum = new Solution(solutions[0]);
		for (int i = 1; i < solutions.length; i++)
		{
			for (int j = 0; j < solutions[i].feat.length; j++)
			{
				sum.feat[j] += solutions[i].feat[j];
			}
		}
		return sum;
	}
}
