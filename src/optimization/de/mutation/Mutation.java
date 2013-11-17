package optimization.de.mutation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import optimization.Solution;
import optimization.de.DE;
import optimization.de.Population;

public abstract class Mutation
{
	protected final int K;
	protected final int INDICES_SIZE;
	/** [ i, i1, i2, i3, ... ] */
	protected final List<Integer> indices;

	public Mutation(int k)
	{
		K = k;
		INDICES_SIZE = 2 * k + 2;
		indices = new ArrayList<Integer>(INDICES_SIZE);
	}

	public abstract Solution getMutant(Population pop, Random rand, int i);

	abstract double computeScalingFactor(int NP);

	protected void computeIndices(Population pop, Random rand, int i)
	{
		indices.clear();
		indices.add(i);
		for (int j = 1; j < INDICES_SIZE; j++)
		{
			indices.add(DE.getRandomIndex(rand, pop.size(), indices));
		}
	}
}
