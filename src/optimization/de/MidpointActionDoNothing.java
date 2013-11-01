package optimization.de;

import javabbob.JNIfgeneric;

public class MidpointActionDoNothing implements MidpointAction
{
	public boolean isMidpointBetterThanTarget(Population pop, JNIfgeneric fgeneric)
	{
		return false;
	}
}
