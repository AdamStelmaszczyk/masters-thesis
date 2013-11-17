package optimization.de;

import java.util.List;
import java.util.Random;

import javabbob.Experiment;
import optimization.Evaluator;
import optimization.Optimizer;
import optimization.Solution;
import optimization.de.mutation.Mutation;

public class DE implements Optimizer
{
	public final static double F = 0.9;
	public final static double CR = 0.9;
	public final static int NP_TO_DIM_RATIO = 10;

	private final Mutation mutation;

	public DE(Mutation mutation)
	{
		this.mutation = mutation;
	}

	public static int getRandomIndex(Random rand, int NP, List<Integer> excluded)
	{
		int result;
		do
		{
			result = rand.nextInt(NP);
		}
		while (excluded.contains(result));
		return result;
	}

	public void optimize(Evaluator evaluator, int dim, Random rand)
	{
		final int NP = NP_TO_DIM_RATIO * dim;
		final Population actual = new Population(NP, dim, rand);
		final Population children = new Population(NP, dim, rand);
		int outsiders = 0;
		while (true)
		{
			for (int i = 0; i < NP; i++)
			{
				final Solution mutant = mutation.getMutant(actual, rand, i);
				children.solutions[i] = actual.solutions[i].crossover(mutant, rand);
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
		System.out.printf("%3.0f%% ", 100.0 * outsiders / (dim * Experiment.FUN_EVALS_TO_DIM_RATIO));
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
