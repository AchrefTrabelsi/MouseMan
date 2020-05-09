package gamedata;

import java.util.LinkedList;

import states.State;

public class StateMachine {
	private LinkedList<State> States;
	private boolean Adding,Replacing;
	private int Removing;
	private State NextState ;
	
	public StateMachine()
	{
		States = new LinkedList<State>();
		Adding = false;
		Replacing = false;
		Removing = 0 ;
	}
	public void  AddState(State newstate,boolean replace)
	{
		NextState =newstate;
		Replacing = replace;
		Adding = true;
	}
	public void  AddState(State newstate)
	{
		NextState =newstate;
		Replacing = true;
		Adding = true;
	}
	public void RemoveState()
	{
		Removing++;
	}
	public void RemoveState(int x)
	{
		if(x>0)
			Removing+=x;
	}
	//ajouter ou retirer un state
	public boolean ProcessStateChanges()
	{
		while(Removing>0 && States.size()>0)
		{
			States.poll().Delete();
			Removing--;
		}
		Removing=0;
		if(States.size()>0)
		{
			States.peek().resume();
		}
		if(Adding)
		{
			if(Replacing && States.size()>0)
			{
				States.poll().Delete();
				Replacing = false;
			}
			else
			{
				if(States.size()>0)
				{
					States.peek().pause();
				}
			}
			States.addFirst(NextState);
			Adding = false;
			NextState = null;
			return true;
		}
		return false;
	}
	public State CurrentState()
	{
		return States.peek();
	}


}
