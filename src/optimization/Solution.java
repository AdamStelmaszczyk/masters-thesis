package optimization;

import java.util.Arrays;
import java.util.Random;

import javabbob.Experiment;
import optimization.de.DE;

public class Solution
{
	public final double feat[];

	/** No deep copy. */
	public Solution(double feat[])
	{
		this.feat = feat;
	}

	/** Create random solution. */
	public Solution(int dim, Random rand)
	{
		feat = new double[dim];
		for (int i = 0; i < dim; i++)
		{
			feat[i] = (Experiment.DOMAIN_MAX - Experiment.DOMAIN_MIN) * rand.nextDouble() + Experiment.DOMAIN_MIN;
		}
	}

	/** Deep copy. */
	public Solution(Solution other)
	{
		feat = new double[other.feat.length];
		System.arraycopy(other.feat, 0, feat, 0, feat.length);
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

	public boolean isOutside()
	{
		for (int i = 0; i < feat.length; i++)
		{
			if (feat[i] < Experiment.DOMAIN_MIN || feat[i] > Experiment.DOMAIN_MAX)
			{
				return true;
			}
		}
		return false;
	}

	public boolean isBetter(Solution other, Evaluator evaluator)
	{
		return evaluator.evaluate(this) < evaluator.evaluate(other);
	}
}