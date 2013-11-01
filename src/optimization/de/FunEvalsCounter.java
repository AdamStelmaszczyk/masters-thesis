package optimization.de;

public class FunEvalsCounter
{
	private int counter = 0;
	private final int maxFunEvals;

	public FunEvalsCounter(int maxFunEvals)
	{
		this.maxFunEvals = maxFunEvals;
	}

	public int getCounter()
	{
		return counter;
	}

	public void increment()
	{
		counter++;
	}

	public boolean isEnough()
	{
		return counter >= maxFunEvals;
	}
}