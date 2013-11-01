package optimization.de.midpoint;

import javabbob.JNIfgeneric;
import optimization.de.Population;

public class MidpointActionDoNothing extends MidpointAction
{
	@Override
	public boolean isMidpointBetterThanTarget(Population pop, JNIfgeneric fgeneric)
	{
		return false;
	}
}
