package optimization.de;

import javabbob.JNIfgeneric;

public class MidpointActionEvaluate implements MidpointAction
{
	public boolean isMidpointBetterThanTarget(Population pop, JNIfgeneric fgeneric)
	{
		final Solution midpoint = pop.computeMidpoint(pop);
		return midpoint.getFitness(fgeneric) <= fgeneric.getFtarget();
	}
}
