package optimization.de;

import java.util.Arrays;
import java.util.Random;

import javabbob.JNIfgeneric;

public class Solution
{
	public double fitness;
	public final double feat[];
	private final FunEvalsCounter funEvalsCounter;

	/** No deep copy. */
	public Solution(double feat[], FunEvalsCounter funEvals)
	{
		this.feat = feat;
		funEvalsCounter = funEvals;
		initFitness();
	}

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

	/** Deep copy. */
	public Solution(Solution other)
	{
		feat = new double[other.feat.length];
		System.arraycopy(other.feat, 0, feat, 0, feat.length);
		funEvalsCounter = other.funEvalsCounter;
		initFitness();
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

	public Solution crossover(Solution other, Random rand)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			if (rand.nextDouble() < DE.CR)
			{
				result.feat[i] = other.feat[i];
			}
		}
		return result;
	}

	public double getFitness(JNIfgeneric fgeneric)
	{
		if (fitness == Integer.MAX_VALUE)
		{
			fitness = fgeneric.evaluate(feat);
			funEvalsCounter.increment();
		}
		return fitness;
	}

	public FunEvalsCounter getFunEvalsCounter()
	{
		return funEvalsCounter;
	}

	public boolean isBetter(Solution other, JNIfgeneric fgeneric)
	{
		return getFitness(fgeneric) < other.getFitness(fgeneric);
	}

	public Solution minus(Solution other)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			result.feat[i] -= other.feat[i];
		}
		return result;
	}

	public Solution mul(double factor)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			result.feat[i] *= factor;
		}
		return result;
	}

	public Solution plus(Solution other)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			result.feat[i] += other.feat[i];
		}
		return result;
	}

	@Override
	public String toString()
	{
		return Arrays.toString(feat);
	}

	private double computeFeatMean()
	{
		return computeFeatSum() / feat.length;
	}

	private double computeFeatSum()
	{
		double sum = 0.0;
		for (final double element : feat)
		{
			sum += element;
		}
		return sum;
	}

	private void initFitness()
	{
		fitness = Integer.MAX_VALUE;
	}

}