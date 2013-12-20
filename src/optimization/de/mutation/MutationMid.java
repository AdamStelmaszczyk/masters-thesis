package optimization.de.mutation;

import optimization.Solution;
import optimization.de.DE;
import optimization.de.Population;

/** DE/mid/k */
public class MutationMid extends MutationRand
{
	protected Solution midpoint;

	public MutationMid(int k)
	{
		super(k);
	}

	@Override
	public double computeScalingFactor()
	{
		return Math.sqrt((1 + 2 * DE.F * DE.F) / (2 * K));
	}

	@Override
	public Solution getMutant(Population pop, int i)
	{
		if (i == 0)
		{
			midpoint = pop.computeMidpoint();
		}
		return midpoint.plus(computeDiffVector(pop));
	}
}
