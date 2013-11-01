package optimization.de;

import java.util.Random;

import javabbob.JNIfgeneric;
import optimization.Optimizer;
import optimization.de.midpoint.MidpointAction;
import optimization.de.midpoint.MidpointActionDoNothing;
import optimization.de.mutation.Mutation;
import optimization.de.mutation.MutationRandom;

public class DE implements Optimizer
{
	public final static double F = 0.9;
	public final static double CR = 0.9;
	public final static int NP_TO_DIM_RATIO = 10;

	private final Class<? extends Mutation> mutationClass;
	private final Class<? extends MidpointAction> midpointActionClass;

	private Mutation mutation;
	private MidpointAction midpointAction;

	public DE()
	{
		this(MutationRandom.class);
	}

	public DE(Class<? extends Mutation> mutationClass)
	{
		this(mutationClass, MidpointActionDoNothing.class);
	}

	public DE(Class<? extends Mutation> mutationClass, Class<? extends MidpointAction> midpointActionClass)
	{
		this.mutationClass = mutationClass;
		this.midpointActionClass = midpointActionClass;
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

	public void optimize(JNIfgeneric fgeneric, int dim, int maxFunEvals, Random rand)
	{
		final FunEvalsCounter funEvals = new FunEvalsCounter(maxFunEvals);

		final int NP = NP_TO_DIM_RATIO * dim;
		final Population old = new Population(NP, dim, rand, funEvals);
		final Population children = new Population(NP, dim, rand, funEvals);
		final double target = fgeneric.getFtarget();

		try
		{
			mutation = mutationClass.getDeclaredConstructor(int.class).newInstance(NP);
			midpointAction = midpointActionClass.newInstance();
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		while (true)
		{
			midpointAction.isMidpointBetterThanTarget(old, fgeneric);
			if (funEvals.isEnough())
			{
				return;
			}

			for (int i = 0; i < NP; i++)
			{
				final Solution mutant = mutation.getMutant(old, rand, i);
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
}
