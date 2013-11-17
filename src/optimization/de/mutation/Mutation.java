package optimization.de.mutation;

import java.util.Random;

import optimization.Solution;
import optimization.de.Population;

public abstract class Mutation
{
	public abstract Solution getMutant(Population pop, Random rand, int i);

	abstract double computeScalingFactor(int NP);
}
