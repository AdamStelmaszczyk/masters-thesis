package javabbob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import optimization.Evaluator;
import optimization.Optimizer;
import optimization.de.DE;
import optimization.de.mutation.MutationMid;
import optimization.de.mutation.MutationMidInf;
import optimization.de.mutation.MutationRand;
import optimization.de.mutation.MutationRandInf;
import optimization.random.RandomOptimizer;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/** Wrapper class running an entire BBOB experiment. */
public class Experiment
{
	public final static int FUNCTIONS[] =
	{ 15, 16, 19, 20, 21, 22, 24 };
	public final static int RUNS = 15;
	public final static int FUN_EVALS_TO_DIM_RATIO = 100000;
	public final static int SEED = 1;

	public final static double DOMAIN_MIN = -5.0;
	public final static double DOMAIN_MAX = 5.0;

	private final static String ALGORITHM_FLAG = "a";
	private final static String DIMENSION_FLAG = "d";
	private final static String HELP_FLAG = "help";

	private final static String DEFAULT_ALGORITHM = "derand";
	private final static int DEFAULT_K = 1;
	private final static int DEFAULT_DIMENSION = 10;

	/** Main method for running the whole BBOB experiment. */
	public static void main(String[] args)
	{
		final CommandLine line = getCommandLine(args);

		String algorithm = DEFAULT_ALGORITHM;
		int k = DEFAULT_K;
		if (line.hasOption(ALGORITHM_FLAG))
		{
			final String[] algArgs = line.getOptionValues("a");
			if (algArgs != null)
			{
				algorithm = algArgs[0];
				if (algArgs.length > 1)
				{
					k = Integer.parseInt(algArgs[1]);
				}
			}
		}

		int dimension = DEFAULT_DIMENSION;
		if (line.hasOption(DIMENSION_FLAG))
		{
			dimension = Integer.parseInt(line.getOptionValue("d"));
		}

		Optimizer optimizer = null;
		String filename = algorithm;
		if (algorithm.equals("derand"))
		{
			optimizer = new DE(new MutationRand(k));
			filename += k;
		}
		else if (algorithm.equals("demid"))
		{
			optimizer = new DE(new MutationMid(k));
			filename += k;
		}
		else if (algorithm.equals("derandinf"))
		{
			optimizer = new DE(new MutationRandInf());
		}
		else if (algorithm.equals("demidinf"))
		{
			optimizer = new DE(new MutationMidInf());
		}
		else if (algorithm.equals("random"))
		{
			optimizer = new RandomOptimizer();
		}
		else
		{
			die();
		}

		filename += "_" + dimension + "D";

		// Sets default locale to always have 1.23 not 1,23 in files
		Locale.setDefault(Locale.US);

		// Loads the library cjavabbob at the core of JNIfgeneric. Throws runtime errors if the library is not found.
		final JNIfgeneric fgeneric = new JNIfgeneric();

		// Creates the folders for storing the experimental data.
		if (JNIfgeneric.makeBBOBdirs(filename, false))
		{
			System.out.println("BBOB data directory '" + filename + "' created");
		}
		else
		{
			System.out.println("Error! BBOB data directory '" + filename + "' was NOT created, stopping");
			return;
		}

		final Random rand = new Random();
		rand.setSeed(SEED);
		fgeneric.setNoiseSeed(SEED);
		System.out.println("Seed: " + SEED);

		final long startTime = System.currentTimeMillis();
		printDate();

		for (final int fun : FUNCTIONS)
		{
			for (int run = 1; run <= RUNS; run++)
			{
				fgeneric.initBBOB(fun, run, dimension, filename, new JNIfgeneric.Params());

				final int MAX_FUN_EVALS = FUN_EVALS_TO_DIM_RATIO * dimension;
				final Evaluator evaluator = new Evaluator(fgeneric, MAX_FUN_EVALS);
				optimizer.optimize(evaluator, dimension, rand);

				final double distance = fgeneric.getBest() - fgeneric.getFtarget();
				final int fes = (int) fgeneric.getEvaluations();
				final int seconds = (int) ((System.currentTimeMillis() - startTime) / 1000);
				System.out.printf("%dD f%d run %3d FEs = %7d best-target = %15.8f %ds\n", dimension, fun, run, fes,
						distance, seconds);

				fgeneric.exitBBOB();
			}
			printDate();
		}
		System.out.println("---- " + dimension + "-D done ----");
	}

	private static void die()
	{
		throw new IllegalArgumentException();
	}

	private static CommandLine getCommandLine(String[] args)
	{
		final CommandLineParser parser = new DefaultParser();
		final Options options = new Options();

		final Option alg = Option.builder(ALGORITHM_FLAG).desc("algorithm and k for derand and demid, i.e. derand 6")
				.argName("algorithm> <k").optionalArg(true).numberOfArgs(2).required().build();
		final Option dim = Option.builder(DIMENSION_FLAG).desc("number of dimensions").argName("dimensions").hasArg()
				.build();
		final Option help = new Option(HELP_FLAG, "print this message");

		options.addOption(alg);
		options.addOption(dim);
		options.addOption(help);

		CommandLine line = null;
		try
		{
			line = parser.parse(options, args);
		}
		catch (final ParseException e)
		{
			System.out.println(e.getMessage());
			printHelp(options);
			die();
		}

		if (line.hasOption(HELP_FLAG))
		{
			printHelp(options);
		}

		return line;
	}

	private static void printDate()
	{
		System.out.println(new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new Date()));
	}

	private static void printHelp(Options options)
	{
		final HelpFormatter formatter = new HelpFormatter();
		formatter.printHelp("./run.sh", options);
	}
}
