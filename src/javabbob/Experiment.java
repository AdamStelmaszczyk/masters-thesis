package javabbob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import optimization.Optimizer;
import optimization.de.DE;
import optimization.de.MidpointActionEvaluate;
import optimization.de.MutationOperatorWithMidpoint;
import optimization.de.MutationOperatorWithMidpointInfinity;
import optimization.de.MutationOperatorWithRandomInfinity;
import optimization.random.RandomOptimizer;

/** Wrapper class running an entire BBOB experiment. */
public class Experiment
{
	final static int DIMENSIONS[] =
	{ 10 };
	final static int FUNCTIONS[] =
	{ 24, 110, 113, 116, 119, 122, 126 };
	final static int RUNS = 100;
	final static int FUN_EVALS_TO_DIM_RATIO = 100000;
	final static int SEED = 1;

	final static Map cmdToOptimizerMap = new HashMap();

	private static void initCmdToOptimizerMap()
	{
		cmdToOptimizerMap.put("derand", new DE());
		cmdToOptimizerMap.put("derandinf", new DE(new MutationOperatorWithRandomInfinity()));
		cmdToOptimizerMap.put("demid", new DE(new MutationOperatorWithMidpoint()));
		cmdToOptimizerMap.put("demidinf", new DE(new MutationOperatorWithMidpointInfinity()));
		cmdToOptimizerMap.put("demidplus", new DE(new MutationOperatorWithMidpoint(), new MidpointActionEvaluate()));
		cmdToOptimizerMap.put("random", new RandomOptimizer());
	}

	/** Main method for running the whole BBOB experiment. */
	public static void main(String[] args)
	{
		initCmdToOptimizerMap();
		if (args.length == 0 || !cmdToOptimizerMap.containsKey(args[0]))
		{
			die();
		}
		final Optimizer optimizer = (Optimizer) cmdToOptimizerMap.get(args[0]);

		// Sets default locale to always have 1.23 not 1,23 in files
		Locale.setDefault(Locale.US);

		// Loads the library cjavabbob at the core of JNIfgeneric. Throws runtime errors if the library is not found.
		final JNIfgeneric fgeneric = new JNIfgeneric();

		// Creates the folders for storing the experimental data.
		if (JNIfgeneric.makeBBOBdirs(args[0], false))
		{
			System.out.println("BBOB data directory '" + args[0] + "' created");
		}
		else
		{
			System.out.println("Error! BBOB data directory '" + args[0] + "' was NOT created, stopping");
			return;
		}

		final Random rand = new Random();
		rand.setSeed(SEED);
		fgeneric.setNoiseSeed(SEED);
		System.out.println("Seed: " + SEED);

		final long startTime = System.currentTimeMillis();
		printDate();

		for (int i = 0; i < DIMENSIONS.length; i++)
		{
			final int dim = DIMENSIONS[i];
			for (int f = 0; f < FUNCTIONS.length; f++)
			{
				final int fun = FUNCTIONS[f];
				for (int run = 1; run <= RUNS; run++)
				{
					fgeneric.initBBOB(fun, run, dim, args[0], new JNIfgeneric.Params());
					final int MAX_FUN_EVALS = FUN_EVALS_TO_DIM_RATIO * dim;
					while (fgeneric.getEvaluations() < MAX_FUN_EVALS && fgeneric.getBest() > fgeneric.getFtarget())
					{
						optimizer.optimize(fgeneric, dim, MAX_FUN_EVALS - (int) fgeneric.getEvaluations(), rand);
					}

					final double distance = fgeneric.getBest() - fgeneric.getFtarget();
					final int fes = (int) fgeneric.getEvaluations();
					final int seconds = (int) ((System.currentTimeMillis() - startTime) / 1000);
					System.out.println("f" + fun + " in " + dim + "-D, run " + run + ", FEs = " + fes
							+ " fbest-ftarget = " + distance + ", " + seconds + "s");

					fgeneric.exitBBOB();
				}
				printDate();
			}
			System.out.println("---- " + dim + "-D done ----");
		}
	}

	private static void printDate()
	{
		System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
	}

	private static void die()
	{
		throw new IllegalArgumentException("First argument must be one of the following: " + cmdToOptimizerMap.keySet());
	}
}
