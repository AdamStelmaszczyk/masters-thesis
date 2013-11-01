package optimization.de.midpoint;

import javabbob.JNIfgeneric;
import optimization.de.Population;

abstract public class MidpointAction
{
	public abstract boolean isMidpointBetterThanTarget(Population pop, JNIfgeneric fgeneric);
}
