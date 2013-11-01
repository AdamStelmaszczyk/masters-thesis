package optimization.de.midpoint;

import javabbob.JNIfgeneric;
import optimization.de.Population;
import optimization.de.Solution;

public class MidpointActionEvaluate extends MidpointAction
{
	@Override
	public boolean isMidpointBetterThanTarget(Population pop, JNIfgeneric fgeneric)
	{
		final Solution midpoint = pop.computeMidpoint(pop);
		return midpoint.getFitness(fgeneric) <= fgeneric.getFtarget();
	}
}
