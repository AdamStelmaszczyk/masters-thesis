package optimization.de;

import javabbob.Main;
import optimization.Evaluator;
import optimization.Solution;

public class Population
{
	private final double[][] covarianceMatrix;
	private final double[] mean;

	public final Solution[] solutions;
	public final int DIM;

	/** Create random population. */
	public Population(int NP, int DIM)
	{
		covarianceMatrix = new double[DIM][DIM];
		mean = new double[DIM];
		solutions = new Solution[NP];
		for (int i = 0; i < NP; i++)
		{
			solutions[i] = new Solution(DIM);
		}
		this.DIM = DIM;
	}

	public double[][] computeCovarianceMatrix()
	{
		for (int dim = 0; dim < DIM; dim++)
		{
			mean[dim] = computeMeanInDim(dim);
		}
		// Calculate lower triangle
		for (int y = 0; y < DIM; y++)
		{
			for (int x = 0; x <= y; x++)
			{
				covarianceMatrix[x][y] = cov(x, y, mean);
			}
		}
		// Fill upper triangle by symmetry
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

	public Solution getBest(Evaluator evaluator)
	{
		Solution best = solutions[0];
		for (int i = 1; i < solutions.length; i++)
		{
			if (solutions[i].isBetter(best, evaluator))
			{
				best = solutions[i];
			}
		}
		return best;
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

	public int getNumberOfOutsiders()
	{
		int outsiders = 0;
		for (final Solution solution : solutions)
		{
			if (solution.isOutside())
			{
				outsiders++;
			}
		}
		return outsiders;
	}

	/** @return Deep copy of randomly chosen solution. */
	public Solution getRandom()
	{
		return new Solution(solutions[Main.rand.nextInt(solutions.length)]);
	}

	public int size()
	{
		return solutions.length;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder();
		final String lineSeperator = System.getProperty("line.separator");
		for (final Solution s : solutions)
		{
			sb.append(s);
			sb.append(lineSeperator);
		}
		return sb.toString();
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
}
