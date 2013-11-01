package optimization.de;

import java.util.Random;

/** midpoint + sqrt(1 + 2*F*F) * v */
public class MutationOperatorWithMidpointInfinity extends MutationOperatorWithRandomInfinity
{
	private Solution midpoint;

	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			computeL(pop);
			midpoint = pop.computeMidpoint(pop);
		}
		final Solution diffVector = computeDiffVector(pop, rand).mul(getScalingFactor(pop));
		return midpoint.plus(diffVector);
	}

	public double getScalingFactor(Population pop)
	{
		return Math.sqrt(1 + 2 * DE.F * DE.F);
	}
}
