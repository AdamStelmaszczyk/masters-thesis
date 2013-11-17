package optimization.de;

import java.util.Arrays;
import java.util.Random;

import optimization.FunEvalsCounter;
import optimization.Solution;

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
		for (int dim = 0; dim < DIM; dim++)
		{
			mean[dim] = computeMeanInDim(dim);
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

	public Solution computeMidpoint()
	{
		final Solution sum = new Solution(solutions[0]);
		for (int i = 1; i < solutions.length; i++)
		{
			for (int j = 0; j < DIM; j++)
			{
				sum.feat[j] += solutions[i].feat[j];
			}
		}
		return sum.mul(1.0 / solutions.length);
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

	private double computeMeanInDim(int dim)
	{
		double mean = 0.0;
		for (final Solution solution : solutions)
		{
			mean += solution.feat[dim];
		}
		return mean / solutions.length;
	}

	private double cov(int x, int y, double[] mean)
	{
		double cov = 0.0;
		for (final Solution solution : solutions)
		{
			cov += (solution.feat[x] - mean[x]) * (solution.feat[y] - mean[y]);
		}
		return cov / solutions.length;
	}
	
	@Override
	public String toString()
	{
		return Arrays.toString(solutions);
	}

	public int getNumberOfOutsiders()
	{
		int outsiders = 0;
		for (int i = 0; i < solutions.length; i++)
		{
			if (solutions[i].isOutside())
			{
				outsiders++;
			}
		}
		return outsiders;
	}
}
