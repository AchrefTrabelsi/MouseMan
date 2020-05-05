package gameobjects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import gamedata.GameData;
import utilities.Clock;
import utilities.Rect;

public abstract class Cat implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final int deathtime = 4000;
	protected final int animationtime = 180;	
	protected final int up=1,right=2,down=3,left=4,none=0;
	protected int[][] grid;
	protected int posy,posx;
	protected int ndir;
	protected boolean dead = false;
	protected int sndir;
	protected boolean blocked = false;
	protected int odir = none;
	protected final double toprisonspeed = 10;
	protected double distance = 0;
	protected Mouse m;
	protected int px,py;
	protected Rect rect ;
	protected final static List<Integer> cantvisit = Arrays.asList(1,5,10);
	protected int animation = 0;

	public abstract void ToPrison();
	public abstract void Update();
	public abstract void Draw(GameData data);
	public abstract boolean isdead();
	protected Clock respawntimer = new Clock();
	protected Clock animationtimer = new Clock();
	protected final int collisiondistance = 5;


	public void Pause()
	{
		animationtimer.Pause();
		respawntimer.Pause();
	}
	public void Resume()
	{
		animationtimer.Resume();
		respawntimer.Resume();
	}
	public void init()
	{
		
	}



}
