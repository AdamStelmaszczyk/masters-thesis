package optimization.de;

import java.util.Random;

import javabbob.JNIfgeneric;
import optimization.Optimizer;

public class DE implements Optimizer
{
	final static double F = 0.9;
	final static double CR = 0.9;
	final static int NP_TO_DIM_RATIO = 10;

	private final MutationOperator mutationOperator;
	private final MidpointAction midpointAction;

	public DE(MutationOperator mutationOperator, MidpointAction midpointAction)
	{
		this.mutationOperator = mutationOperator;
		this.midpointAction = midpointAction;
	}

	public DE(MutationOperator mutationOperator)
	{
		this(mutationOperator, new MidpointActionDoNothing());
	}

	public DE()
	{
		this(new MutationOperatorWithRandom());
	}

	public void optimize(JNIfgeneric fgeneric, int dim, int maxFunEvals, Random rand)
	{
		final FunEvalsCounter funEvals = new FunEvalsCounter(maxFunEvals);

		final int NP = NP_TO_DIM_RATIO * dim;
		final Population old = new Population(NP, dim, rand, funEvals);
		final Population children = new Population(NP, dim, rand, funEvals);
		final double target = fgeneric.getFtarget();

		while (true)
		{
			midpointAction.isMidpointBetterThanTarget(old, fgeneric);
			if (funEvals.isEnough())
			{
				return;
			}

			for (int i = 0; i < NP; i++)
			{
				final Solution mutant = mutationOperator.getMutant(old, rand, i);
				children.solutions[i] = old.solutions[i].crossover(mutant, rand);
				if (children.solutions[i].getFitness(fgeneric) <= target)
				{
					return;
				}
				if (funEvals.isEnough())
				{
					return;
				}
			}

			succesion(old, children, fgeneric);
		}
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

	public static int getRandomIndex(Random rand, int NP, int i, int j, int k)
	{
		int result;
		do
		{
			result = (int) (rand.nextDouble() * NP);
		}
		while (result == i || result == j || result == k);
		return result;
	}
}
