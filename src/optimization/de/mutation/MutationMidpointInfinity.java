package optimization.de.mutation;

import java.util.Random;

import optimization.de.DE;
import optimization.de.Population;
import optimization.de.Solution;

/** midpoint + sqrt(1 + 2*F*F) * v */
public class MutationMidpointInfinity extends MutationRandomInfinity
{
	private Solution midpoint;

	public MutationMidpointInfinity(int NP)
	{
		super(NP);
	}

	public double computeScalingFactor(Population pop)
	{
		return Math.sqrt(1 + 2 * DE.F * DE.F);
	}

	@Override
	public Solution getMutant(Population pop, Random rand, int i)
	{
		if (i == 0)
		{
			computeL(pop);
			midpoint = pop.computeMidpoint(pop);
		}
		final Solution diffVector = computeDiffVector(pop, rand).mul(SCALING_FACTOR);
		return midpoint.plus(diffVector);
	}
}
