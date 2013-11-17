package optimization.de.mutation;

import java.util.Random;

import optimization.Solution;
import optimization.de.DE;
import optimization.de.Population;

/** midpoint + sqrt(1 + 2*F*F) * v */
public class MutationMidInf extends MutationRandInf
{
	private Solution midpoint;

	public double computeScalingFactor(Population pop)
	{
		return Math.sqrt(1 + 2 * DE.F * DE.F);
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			if (z == null)
			{
				allocateArrays(pop.DIM);
			}
			computeA(pop);
			midpoint = pop.computeMidpoint();
		}
		final Solution diffVector = computeDiffVector(pop, rand).mul(computeScalingFactor(pop.size()));
		return midpoint.plus(diffVector);
	}
}
