package optimization.de;

import java.util.List;
import java.util.Random;

import javabbob.Experiment;
import javabbob.JNIfgeneric;
import optimization.FunEvalsCounter;
import optimization.Optimizer;
import optimization.Solution;
import optimization.de.mutation.Mutation;

public class DE extends Optimizer
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

	public void optimize(JNIfgeneric fgeneric, int dim, int maxFunEvals, Random rand)
	{
		final FunEvalsCounter funEvals = new FunEvalsCounter(maxFunEvals);
		final int NP = NP_TO_DIM_RATIO * dim;
		final Population old = new Population(NP, dim, rand, funEvals);
		final Population children = new Population(NP, dim, rand, funEvals);
		int outsiders = 0;

		while (true)
		{
			outsiders += old.getNumberOfOutsiders();

			if (funEvals.isEnough())
			{
				return;
			}

			for (int i = 0; i < NP; i++)
			{
				final Solution mutant = mutation.getMutant(old, rand, i);
				children.solutions[i] = old.solutions[i].crossover(mutant, rand);
				if (hasReachedTarget(fgeneric, children.solutions[i].getFitness(fgeneric)))
				{
					printOutsiders(outsiders, dim);
					return;
				}
				if (funEvals.isEnough())
				{
					printOutsiders(outsiders, dim);
					return;
				}
			}

			succesion(old, children, fgeneric);
		}
	}

	private void printOutsiders(int outsiders, int dim)
	{
		System.out.printf("%.1f%% ", 100.0 * outsiders / (dim * Experiment.FUN_EVALS_TO_DIM_RATIO));
	}

	private void succesion(Population old, Population children, JNIfgeneric fgeneric)
	{
		for (int i = 0; i < children.solutions.length; i++)
		{
			if (children.solutions[i].isBetter(old.solutions[i], fgeneric))
			{
				old.solutions[i] = children.solutions[i];
			}
		}
	}
}
