package optimization;

import javabbob.Main;
import optimization.de.DE;

public class Solution
{
	public final double feat[];

	/** Shallow copy. */
	public Solution(double feat[])
	{
		this.feat = feat;
	}

	/** Create random solution. */
	public Solution(int dim)
	{
		feat = new double[dim];
		for (int i = 0; i < dim; i++)
		{
			feat[i] = (Main.DOMAIN_MAX - Main.DOMAIN_MIN) * Main.rand.nextDouble() + Main.DOMAIN_MIN;
		}
	}

	/** Deep copy. */
	public Solution(Solution other)
	{
		feat = new double[other.feat.length];
		System.arraycopy(other.feat, 0, feat, 0, feat.length);
	}

	/** Doesn't modify this object. */
	public Solution crossover(Solution other)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			if (Main.rand.nextDouble() < DE.CR)
			{
				result.feat[i] = other.feat[i];
			}
		}
		return result;
	}

	/** Doesn't modify this object. */
	public Solution minus(Solution other)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			result.feat[i] -= other.feat[i];
		}
		return result;
	}

	/** Doesn't modify this object. */
	public Solution mul(double factor)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			result.feat[i] *= factor;
		}
		return result;
	}

	/** Doesn't modify this object. */
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
		final StringBuilder sb = new StringBuilder();
		for (final double f : feat)
		{
			sb.append(String.valueOf(f));
			sb.append(" ");
		}
		return sb.toString();
	}

	public boolean isOutside()
	{
		for (int i = 0; i < feat.length; i++)
		{
			if (feat[i] < Main.DOMAIN_MIN || feat[i] > Main.DOMAIN_MAX)
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