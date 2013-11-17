package optimization.random;

import java.util.Random;

import javabbob.JNIfgeneric;
import optimization.FunEvalsCounter;
import optimization.Optimizer;
import optimization.Solution;

public class RandomOptimizer extends Optimizer
{
	@Override
	public void optimize(JNIfgeneric fgeneric, int dim, int maxFunEvals, Random rand)
	{
		final FunEvalsCounter funEvalsCounter = new FunEvalsCounter(maxFunEvals);
		while (true)
		{
			final Solution solution = new Solution(dim, rand, funEvalsCounter);
			if (hasReachedTarget(fgeneric, solution.getFitness(fgeneric)))
			{
				break;
			}
			if (funEvalsCounter.isEnough())
			{
				break;
			}
		}
	}
}
