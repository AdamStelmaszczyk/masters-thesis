package optimization.de;

import javabbob.JNIfgeneric;

public interface MidpointAction
{
	boolean isMidpointBetterThanTarget(Population pop, JNIfgeneric fgeneric);
}
