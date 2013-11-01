package optimization.de;

import java.util.Arrays;
import java.util.Random;

import javabbob.JNIfgeneric;

class Solution
{
	public double fitness;
	public final double feat[];
	private final FunEvalsCounter funEvalsCounter;

	public Solution(int dim, Random rand, FunEvalsCounter funEvalsCounter)
	{
		feat = new double[dim];
		for (int i = 0; i < dim; i++)
		{
			feat[i] = 10 * rand.nextDouble() - 5; // because functions from BBOB are defined in [-5; 5] range
		}
		this.funEvalsCounter = funEvalsCounter;
		initFitness();
	}

	Solution(Solution other)
	{
		feat = new double[other.feat.length];
		System.arraycopy(other.feat, 0, feat, 0, feat.length);
		funEvalsCounter = other.funEvalsCounter;
		initFitness();
	}

	Solution(double feat[], FunEvalsCounter funEvals)
	{
		this.feat = feat;
		funEvalsCounter = funEvals;
		initFitness();
	}

	double getFitness(JNIfgeneric fgeneric)
	{
		if (fitness == Integer.MAX_VALUE)
		{
			fitness = fgeneric.evaluate(feat);
			funEvalsCounter.increment();
		}
		return fitness;
	}

	Solution minus(Solution other)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; ++i)
		{
			result.feat[i] -= other.feat[i];
		}
		return result;
	}

	Solution plus(Solution other)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; ++i)
		{
			result.feat[i] += other.feat[i];
		}
		return result;
	}

	Solution mul(double factor)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; ++i)
		{
			result.feat[i] *= factor;
		}
		return result;
	}

	Solution crossover(Solution other, Random rand)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; ++i)
		{
			if (rand.nextDouble() < DE.CR)
			{
				result.feat[i] = other.feat[i];
			}
		}
		return result;
	}

	private void initFitness()
	{
		fitness = Integer.MAX_VALUE;
	}

	public boolean isBetter(Solution other, JNIfgeneric fgeneric)
	{
		return getFitness(fgeneric) < other.getFitness(fgeneric);
	}

	public FunEvalsCounter getFunEvalsCounter()
	{
		return funEvalsCounter;
	}

	public String toString()
	{
		return Arrays.toString(feat);
	}

	private double computeFeatSum()
	{
		double sum = 0.0;
		for (int i = 0; i < feat.length; i++)
		{
			sum += feat[i];
		}
		return sum;
	}

	private double computeFeatMean()
	{
		return computeFeatSum() / feat.length;
	}

	public double cov(Solution other)
	{
		final double mean1 = computeFeatMean();
		final double mean2 = other.computeFeatMean();
		double sum = 0.0;
		for (int i = 0; i < feat.length; i++)
		{
			sum += (feat[i] - mean1) * (other.feat[i] - mean2);
		}
		return sum / feat.length;
	}

}