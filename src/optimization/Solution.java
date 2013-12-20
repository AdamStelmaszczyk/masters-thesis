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

	/** Create solution with repeated features value. */
	public Solution(int dim, double featuresValue)
	{
		feat = new double[dim];
		for (int i = 0; i < dim; i++)
		{
			feat[i] = featuresValue;
		}
	}

	/** Deep copy. */
	public Solution(Solution other)
	{
		feat = new double[other.feat.length];
		System.arraycopy(other.feat, 0, feat, 0, feat.length);
	}

	/** Doesn't modify this object.
	 * @return Child of this and other. */
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

	public boolean isBetter(Solution other, Evaluator evaluator)
	{
		return evaluator.evaluate(this) < evaluator.evaluate(other);
	}

	public boolean isOutside()
	{
		for (final double element : feat)
		{
			if (element < Main.DOMAIN_MIN || element > Main.DOMAIN_MAX)
			{
				return true;
			}
		}
		return false;
	}

	/** Doesn't modify this object.
	 * @return this - other */
	public Solution minus(Solution other)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			result.feat[i] -= other.feat[i];
		}
		return result;
	}

	/** Doesn't modify this object.
	 * @return this * factor */
	public Solution mul(double factor)
	{
		final Solution result = new Solution(this);
		for (int i = 0; i < feat.length; i++)
		{
			result.feat[i] *= factor;
		}
		return result;
	}

	/** Doesn't modify this object.
	 * @return this + other */
	public Solution plus(Solution other)
	{
		final Solution result = new Solution(this);
		result.plusEquals(other);
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

	/** this += other */
	public void plusEquals(Solution other)
	{
		for (int i = 0; i < feat.length; i++)
		{
			feat[i] += other.feat[i];
		}
	}
}