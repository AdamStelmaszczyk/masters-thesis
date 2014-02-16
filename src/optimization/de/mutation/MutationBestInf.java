package optimization.de.mutation;

import optimization.Evaluator;
import optimization.Solution;
import optimization.de.Population;

public class MutationBestInf extends MutationMidInf
{
	private Solution best;

	@Override
	public Solution getMutant(Population pop, int i, Evaluator evaluator)
	{
		if (i == 0)
		{
			if (a == null)
			{
				allocateArrays(pop.DIM);
			}
			computeA(pop);
			best = pop.getBest(evaluator);
		}
		return best.plus(computeDiffVector(pop));
	}
}
