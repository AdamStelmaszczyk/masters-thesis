package optimization.random;

import java.util.Random;

import javabbob.JNIfgeneric;
import optimization.Optimizer;

public class RandomOptimizer implements Optimizer
{
	public void optimize(JNIfgeneric fgeneric, int dim, int maxFunEvals, Random rand)
	{
		final double[] solution = new double[dim];

		// Obtain the target function value, which only use is termination
		final double target = fgeneric.getFtarget();

		for (int iter = 0; iter < maxFunEvals; iter++)
		{
			// Generate solution
			for (int i = 0; i < dim; i++)
			{
				solution[i] = 10 * rand.nextDouble() - 5;
			}

			final double fitness = fgeneric.evaluate(solution);

			if (fitness <= target)
			{
				break;
			}
		}
	}
}
