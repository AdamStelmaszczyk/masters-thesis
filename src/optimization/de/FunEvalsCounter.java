package optimization.de;

public class FunEvalsCounter
{
	private int counter = 0;
	private final int maxFunEvals;

	public FunEvalsCounter(int maxFunEvals)
	{
		this.maxFunEvals = maxFunEvals;
	}

	public boolean isEnough()
	{
		return counter >= maxFunEvals;
	}

	public void increment()
	{
		counter++;
	}

	public int getCounter()
	{
		return counter;
	}
}