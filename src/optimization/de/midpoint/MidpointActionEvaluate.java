package optimization.de.midpoint;

import javabbob.JNIfgeneric;
import optimization.de.Population;

public class MidpointActionEvaluate extends MidpointAction
{
	@Override
	public boolean isMidpointBetterThanTarget(Population pop, JNIfgeneric fgeneric)
	{
		return pop.computeMidpoint().getFitness(fgeneric) <= fgeneric.getFtarget();
	}
}
