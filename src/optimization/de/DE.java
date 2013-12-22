package optimization.de;

import javabbob.Main;
import optimization.Evaluator;
import optimization.OptimizerWithPopulation;
import optimization.Solution;
import optimization.de.mutation.Mutation;
import Jama.Matrix;

public class DE implements OptimizerWithPopulation
{
	public final static double F = 0.9;
	public final static double CR = 0.9;

	private final Mutation mutation;

	public DE(Mutation mutation)
	{
		this.mutation = mutation;
	}

	public Matrix getCovarianceMatrixAfterMutation(int dim)
	{
		final Population first = new Population(dim);
		final Population second = new Population(dim);
		for (int i = 0; i < Main.NP; i++)
		{
			second.solutions[i] = mutation.getMutant(first, i);
		}
		return second.computeCovarianceMatrix();
	}

	public void optimize(Evaluator evaluator, int dim)
	{
		final Population actual = new Population(dim);
		final Population children = new Population(dim);
		int outsiders = 0;
		while (true)
		{
			for (int i = 0; i < Main.NP; i++)
			{
				final Solution mutant = mutation.getMutant(actual, i);
				children.solutions[i] = actual.solutions[i].crossover(mutant);
			}
			succesion(actual, children, evaluator);
			if (evaluator.hasReachedTarget() || evaluator.hasReachedMaxFunEvals())
			{
				printOutsiders(outsiders, dim);
				return;
			}
			outsiders += actual.getNumberOfOutsiders();
		}
	}

	private void printOutsiders(int outsiders, int dim)
	{
		System.out.printf("%3.0f%% ", 100.0 * outsiders / (dim * Main.FUN_EVALS_TO_DIM_RATIO));
	}

	private void succesion(Population actual, Population children, Evaluator evaluator)
	{
		for (int i = 0; i < children.solutions.length; i++)
		{
			if (children.solutions[i].isBetter(actual.solutions[i], evaluator))
			{
				actual.solutions[i] = new Solution(children.solutions[i]);
			}
		}
	}
}
