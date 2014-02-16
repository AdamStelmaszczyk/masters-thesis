package optimization.de.mutation;

import optimization.Evaluator;
import optimization.Solution;
import optimization.de.Population;

/** DE/best/k */
public class MutationBest extends MutationMid
{
	protected Solution best;

	public MutationBest(int k)
	{
		super(k);
	}

	@Override
	public Solution getMutant(Population pop, int i, Evaluator evaluator)
	{
		if (i == 0)
		{
			best = pop.getBest(evaluator);
		}
		return best.plus(computeDiffVector(pop));
	}
}
