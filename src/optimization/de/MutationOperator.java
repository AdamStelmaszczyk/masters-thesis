package optimization.de;

import java.util.Random;

public interface MutationOperator
{
	Solution getMutant(Population pop, Random rand, int i);

	double getScalingFactor(Population pop);
}
