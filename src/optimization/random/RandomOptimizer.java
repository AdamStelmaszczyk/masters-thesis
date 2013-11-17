package optimization.random;

import java.util.Random;

import optimization.Evaluator;
import optimization.Optimizer;
import optimization.Solution;

public class RandomOptimizer implements Optimizer
{
	public void optimize(Evaluator evaluator, int dim, Random rand)
	{
		while (true)
		{
			final Solution solution = new Solution(dim, rand);
			evaluator.evaluate(solution);
			if (evaluator.hasReachedTarget() || evaluator.hasReachedMaxFunEvals())
			{
				break;
			}
		}
	}
}
