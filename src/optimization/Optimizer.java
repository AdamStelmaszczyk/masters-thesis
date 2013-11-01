package optimization;

import java.util.Random;

import javabbob.JNIfgeneric;

public interface Optimizer
{
	/** JNIfgeneric has all the information about the problem to solve. This method also takes as argument an instance of
	 * Random since one might want to use the same seed.
	 * @param fgeneric An instance JNIfgeneric object.
	 * @param dim Integer giving the dimension of the problem.
	 * @param maxFunEvals Integer giving the maximum number of function evaluations.
	 * @param rand Instance of Random. */
	public void optimize(JNIfgeneric fgeneric, int dim, int maxFunEvals, Random rand);
}
