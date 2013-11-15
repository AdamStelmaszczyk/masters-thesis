package optimization.de;

import java.util.Random;

public class Population
{
	private final double[][] covarianceMatrix;
	private final double[] mean;

	public final Solution[] solutions;
	public final int DIM;

	public Population(int NP, int DIM, Random rand, FunEvalsCounter funEvals)
	{
		covarianceMatrix = new double[DIM][DIM];
		mean = new double[DIM];
		solutions = new Solution[NP];
		for (int i = 0; i < NP; i++)
		{
			solutions[i] = new Solution(DIM, rand, funEvals);
		}
		this.DIM = DIM;
	}

	public double[][] computeCovarianceMatrix()
	{
		for (int x = 0; x < DIM; x++)
		{
			mean[x] = computeMean(x);
		}
		for (int y = 0; y < DIM; y++)
		{
			for (int x = 0; x <= y; x++)
			{
				covarianceMatrix[x][y] = cov(x, y, mean);
			}
		}
		for (int y = 0; y < DIM; y++)
		{
			for (int x = y + 1; x < DIM; x++)
			{
				covarianceMatrix[x][y] = covarianceMatrix[y][x];
			}
		}
		return covarianceMatrix;
	}

	private double cov(int x, int y, double[] mean)
	{
		double cov = 0.0;
		for (int i = 0; i < solutions.length; i++)
		{
			cov += (solutions[i].feat[x] - mean[x]) * (solutions[i].feat[y] - mean[y]);
		}
		return cov;
	}

	private double computeMean(int x)
	{
		double mean = 0.0;
		for (int i = 0; i < solutions.length; i++)
		{
			mean += solutions[i].feat[x];
		}
		return mean / solutions.length;
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

	public int size()
	{
		return solutions.length;
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
