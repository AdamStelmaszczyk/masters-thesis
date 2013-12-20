package optimization.de.mutation;

import optimization.Solution;
import optimization.de.DE;
import optimization.de.Population;

/** midpoint + sqrt(1 + 2*F*F) * v */
public class MutationMidInf extends MutationRandInf
{
	private Solution midpoint;

	@Override
	public double computeScalingFactor()
	{
		return Math.sqrt(1 + 2 * DE.F * DE.F);
	}

	@Override
	public Solution getMutant(Population pop, int i)
	{
		if (i == 0)
		{
			if (a == null)
			{
				allocateArrays(pop.DIM);
			}
			computeA(pop);
			midpoint = pop.computeMidpoint();
		}
		final Solution diffVector = computeDiffVector(pop);
		return midpoint.plus(diffVector);
	}
}
