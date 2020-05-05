package gameobjects;

import java.io.Serializable;

import definitions.Definitions;
import gamedata.AssetManager;
import gamedata.GameData;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import utilities.Clock;
import utilities.Rect;

public class Mouse implements Serializable {
	private static final long serialVersionUID = 1L;
	private int[][] grid;
	private int posy,posx;
	private final int up=1,right=2,down=3,left=4;
	private int ndir;
	private int odir = down;
	private final double speed=2;
	private int distance = 0;
	private boolean energized=false;
	private Clock energizedtimer = new Clock();
	private final int energizedtime = 5000;
	private Rect rect ;
	private int score =0;
	private Clock animationtimer = new Clock();
	private int animation;
	private final int animationtime = 180;
	public static int winscore = 172;
	private boolean updated=false;
	private boolean dead=false;
	private boolean won=false;
	public Mouse(int[][] grid,int posx , int posy)
	{
		this.grid=grid;
		this.posx = posx;
		this.posy = posy;
		rect = new Rect(posx*Definitions.SQUARE,posy*Definitions.SQUARE,Definitions.SQUARE,Definitions.SQUARE);
		animationtimer.Start();
	}
	public boolean ReachedObjective()
	{
		return score>=winscore;
	}
	public void init()
	{
		Definitions.stage.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
			if(e.getCode()==KeyCode.UP)
			{
				ndir=up;
			}else if(e.getCode()==KeyCode.DOWN)
			{
				ndir=down;
			}else if(e.getCode()==KeyCode.RIGHT)
			{
				ndir=right;
			}else if(e.getCode()==KeyCode.LEFT)
			{
				ndir=left;
			}
		});
		
	}
	
	public void Pause()
	{
		animationtimer.Pause();
		energizedtimer.Pause();
	}
	public void Resume()
	{
		animationtimer.Resume();
		energizedtimer.Resume();
	}

	private void animationupdate()
	{
		if(animationtimer.ElapsedTimeinMilliSeconds()>animationtime )
		{
			animation = (animation+1)%2;
			animationtimer.Start();
		}
	}

	private void onmoveupdate(int y,int x,AssetManager s)
	{
		if(grid[y][x]==Definitions.ENERGIE_DRINK)
		{
			s.Play("Energy");
			energized=true;
			energizedtimer.Start();
		}
		if(grid[y][x]==Definitions.CHEESE)
		{
			score++;
		}
		if(grid[y][x]!=10)
			grid[y][x]=0;

	}
	public int getScore()
	{
		return score;
	}
	public Rect GetPos()
	{
		return rect;
	}
	public int GetX()
	{
		return posx;
	}
	public int GetY()
	{
		return posy;
	}
	public void Update(AssetManager s)
	{
		if(odir==up && grid[(posy-1+Definitions.N)%Definitions.N][posx]!=1 && grid[(posy-1+Definitions.N)%Definitions.N][posx]!=5&& (grid[(posy-1+Definitions.N)%Definitions.N][posx]!=10 || (grid[(posy-1+Definitions.N)%Definitions.N][posx]==10 && score>=winscore)))
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posy=(posy-1+Definitions.N)%Definitions.N;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
				updated=false;
			}
			else if(distance>=Definitions.SQUARE/2 && !updated)
			{
				onmoveupdate((posy-1+Definitions.N)%Definitions.N,posx,s);
				updated=true;
			}
			else
			{
				rect.setY(rect.getY()-speed);
				distance+=speed;
			}
		}
		else if(odir==down && grid[(posy+1)%Definitions.N][posx]!=1 && grid[(posy+1)%Definitions.N][posx]!=5 && (grid[(posy+1)%Definitions.N][posx]!=10 || (grid[(posy+1)%Definitions.N][posx]==10 && score>=winscore)))
		{			
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posy=(posy+1)%Definitions.N;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
				updated=false;
			}
			else if(distance>=Definitions.SQUARE/2 && !updated)
			{
				onmoveupdate((posy+1)%Definitions.N,posx,s);
				updated=true;
			}
			else
			{
				rect.setY(rect.getY()+speed);
				distance+=speed;
			}
		}
		else if(odir==right && grid[posy][(posx+1)%Definitions.M]!=1 && grid[posy][(posx+1)%Definitions.M]!=5&& (grid[posy][(posx+1)%Definitions.M]!=10 || (grid[posy][(posx+1)%Definitions.M]==10 && score>=winscore)))
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posx=(posx+1)%Definitions.M;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
				updated=false;
			}
			else if(distance>=Definitions.SQUARE/2 && !updated)
			{
				onmoveupdate(posy,(posx+1)%Definitions.M,s);
				updated=true;
			}
			else
			{
				rect.setX(rect.getX()+speed);
				distance+=speed;
			}
		}
		else if(odir==left && grid[posy][(posx-1+Definitions.M)%Definitions.M]!=1 && grid[posy][(posx-1+Definitions.M)%Definitions.M]!=5 && (grid[posy][(posx-1+Definitions.M)%Definitions.M]!=10 || (grid[posy][(posx-1+Definitions.M)%Definitions.M]==10 && score>=winscore)) )
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posx=(posx-1+Definitions.M)%Definitions.M;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
				updated=false;
			}
			else if(distance>=Definitions.SQUARE/2 && !updated)
			{
				onmoveupdate(posy,(posx-1+Definitions.M)%Definitions.M,s);
				updated=true;
			}
			else
			{
				rect.setX(rect.getX()-speed);
				distance+=speed;
			}
		}
		if(ndir==up && grid[(posy-1+Definitions.N)%Definitions.N][posx]!=1 && grid[(posy-1+Definitions.N)%Definitions.N][posx]!=5&& (grid[(posy-1+Definitions.N)%Definitions.N][posx]!=10 || (grid[(posy-1+Definitions.N)%Definitions.N][posx]==10 && score>=winscore)) && distance ==0)
		{
			odir=ndir;
		}
		else if(ndir==down && grid[(posy+1)%Definitions.N][posx]!=1 && grid[(posy+1)%Definitions.N][posx]!=5 && (grid[(posy+1)%Definitions.N][posx]!=10 || (grid[(posy+1)%Definitions.N][posx]==10 && score>=winscore))&& distance ==0)
		{
			odir=ndir;
		}
		else if(ndir==right && grid[posy][(posx+1)%Definitions.M]!=1 && grid[posy][(posx+1)%Definitions.M]!=5&& (grid[posy][(posx+1)%Definitions.M]!=10 || (grid[posy][(posx+1)%Definitions.M]==10 && score>=winscore))&& distance ==0)
		{
			odir=ndir;
		}
		else if(ndir==left && grid[posy][(posx-1+Definitions.M)%Definitions.M]!=1 && grid[posy][(posx-1+Definitions.M)%Definitions.M]!=5 && (grid[posy][(posx-1+Definitions.M)%Definitions.M]!=10 || (grid[posy][(posx-1+Definitions.M)%Definitions.M]==10 && score>=winscore))&& distance ==0 )
		{
			odir=ndir;
		}
		if(energized && energizedtimer.ElapsedTimeinMilliSeconds()>energizedtime)
		{
			energized = false;
			energizedtimer.Reset();
		}
		if(grid[posy][posx]==10 && grid[(posy+1)%Definitions.N][posx]==10)
		{
			won=true;
		}
		animationupdate();
	}
	public boolean Won()
	{
		return won;
	}
	public boolean isEnergized()
	{
		return energized;
	}
	public void Draw(GameData data)
	{
		if(odir==up)
		{
			if(energized)
				data.screen.AddImage(data.assets.GetImage("eMouseUp"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
			else
				data.screen.AddImage(data.assets.GetImage("MouseUp"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}
		else if(odir==down)
		{
			if(energized)
				data.screen.AddImage(data.assets.GetImage("eMouseDown"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
			else
				data.screen.AddImage(data.assets.GetImage("MouseDown"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}
		else if(odir==right)
		{
			if(energized)
				data.screen.AddImage(data.assets.GetImage("eMouseRight"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
			else
				data.screen.AddImage(data.assets.GetImage("MouseRight"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}
		else if(odir==left)
		{
			if(energized)
				data.screen.AddImage(data.assets.GetImage("eMouseLeft"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
			else
				data.screen.AddImage(data.assets.GetImage("MouseLeft"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}
	}
	public void dead(boolean b) {
		dead=true;
		
	}
	public boolean isDead()
	{
		return dead;
	}

}
