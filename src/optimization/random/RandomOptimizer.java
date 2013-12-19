package optimization.random;

import optimization.Evaluator;
import optimization.Optimizer;
import optimization.Solution;

public class RandomOptimizer implements Optimizer
{
	public void optimize(Evaluator evaluator, int dim)
	{
		do
		{
			evaluator.evaluate(new Solution(dim));
		}
		while (!evaluator.hasReachedTarget() && !evaluator.hasReachedMaxFunEvals());
	}
}
