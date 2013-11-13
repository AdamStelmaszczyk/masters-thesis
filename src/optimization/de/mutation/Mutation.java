package optimization.de.mutation;

import java.util.Random;

import optimization.de.Population;
import optimization.de.Solution;

public abstract class Mutation
{
	public abstract Solution getMutant(Population pop, Random rand, int i);

	abstract double computeScalingFactor(int NP);
}
