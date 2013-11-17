package optimization;

import java.util.Random;

public interface Optimizer
{
	public void optimize(Evaluator evaluator, int dim, Random rand);
}
