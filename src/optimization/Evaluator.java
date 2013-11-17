package optimization;

import javabbob.JNIfgeneric;

public class Evaluator
{
	private final JNIfgeneric fgeneric;
	private final int MAX_FUN_EVALS;

	public Evaluator(JNIfgeneric fgeneric, int maxFunEvals)
	{
		this.fgeneric = fgeneric;
		MAX_FUN_EVALS = maxFunEvals;
	}

	public double evaluate(Solution solution)
	{
		return fgeneric.evaluate(solution.feat);
	}

	public boolean hasReachedTarget()
	{
		return fgeneric.getBest() < fgeneric.getFtarget();
	}

	public boolean hasReachedMaxFunEvals()
	{
		return fgeneric.getEvaluations() >= MAX_FUN_EVALS;
	}

	public int getFunEvals()
	{
		return (int) fgeneric.getEvaluations();
	}
}
