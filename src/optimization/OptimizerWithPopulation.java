package optimization;

import Jama.Matrix;

public interface OptimizerWithPopulation extends Optimizer
{
	public Matrix getCovarianceMatrixAfterMutation(int dim);
}
