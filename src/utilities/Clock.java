package utilities;

import java.io.Serializable;

public class Clock implements Serializable {
	private static final long serialVersionUID = 1L;
	private long start;
	private long time;
	private int Working ;
	private boolean started=false;
	public Clock()
	{
		Working = -1;
	}
	public void Start()
	{
		start=System.currentTimeMillis();
		Working = 1;
		time =0;
		started=true;
	}
	public void Reset()
	{
		Working = 0;
		time =0;
		started=false;
	}
	public void Resume()
	{
		if(started &&Working == 0)
		{
			start=System.currentTimeMillis();
			Working = 1;
		}
	}

	public void Pause()
	{
		if(started && Working == 1)
		{
			time += System.currentTimeMillis()-start;
			start = 0;
			Working =0;
		}

	}
	public double ElapsedTimeinSeconds()
	{
		if(Working == 1)
			return ((double)System.currentTimeMillis()-start+time)/1000;
		else 
			return ((double)time)/1000;
	}
	public double ElapsedTimeinMilliSeconds()
	{
		if(Working == 1)
			return System.currentTimeMillis()-start+time;
		else 
			return time;

	}


}
