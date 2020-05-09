package gameobjects;


import definitions.Definitions;
import gamedata.GameData;
import utilities.Rect;

public class AutoCat extends Cat {
	protected double speed=1.5;
	protected final double normalspeed =1.5; 
	private static final long serialVersionUID = 1L;
	public AutoCat(int[][] grid,int posx , int posy,Mouse m,int px,int py)
	{
		this.grid=grid;
		rect = new Rect(posx*Definitions.SQUARE,posy*Definitions.SQUARE,Definitions.SQUARE,Definitions.SQUARE);
		this.posx = posx;
		this.posy = posy;
		this.m=m;
		this.px=px;
		this.py=py;
		animationtimer.Start();
	}
	public boolean isdead()
	{
		return dead;
	}
	private void SetDirection(int dx,int dy) // mise a jour de la direction en fonction de dx et dy qui presente 
											//la difference entre la position à atteindre et la position actuelle
	{
		if(Math.abs(dx)>Math.abs(dy))
		{
			if(dx>0)
			{
				ndir=right;
			}
			else
			{
				ndir=left;
			}
			if(dy>0 && grid[posy+1][posx]!=1)
			{
				sndir=down;
			}
			else if(dy<0 && grid[posy-1][posx]!=1)
			{
				sndir=up;
			}
			else if(grid[posy-1][posx]!=1)
			{
				sndir=up;
			}
			else
			{
				sndir=down;
			}
		}
		else
		{
			if(dy>0)
			{
				ndir=down;
			}
			else
			{
				ndir=up;
			}
			if(dx>0 && grid[posy][posx+1]!=1)
			{
				sndir=right;
			}
			else if(dx<0 && grid[posy][posx-1]!=1)
			{
				sndir=left;
			}
			else if(grid[posy][posx-1]!=1)
			{
				sndir=left;
			}
			else
			{
				sndir=right;
			}
		}

	}
	public void ToPrison()
	{
		blocked=false;
		if(respawntimer.ElapsedTimeinMilliSeconds()>deathtime)
		{
			distance=0;
			dead=false;
			speed=normalspeed;
			odir=up;
			posx=Definitions.OUTOFPRISONX;
			posy=Definitions.OUTOFPRISONY;
			rect.setX(posx*Definitions.SQUARE);
			rect.setY(posy*Definitions.SQUARE);
			return;
		}
		int dx = px - posx;
		int dy = py - posy;
		if(dx==0 && dy==0)
		{
			return;
		}
		SetDirection(dx, dy);
		if(odir==up && (cantvisit.contains(grid[posy-1][posx])==false||grid[posy-1][posx]==5))
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posy--;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setY(rect.getY()-speed);
				distance+=speed;
			}
		}
		else if(odir==down && (cantvisit.contains(grid[posy+1][posx])==false || grid[posy+1][posx]==5))
		{			
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posy++;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setY(rect.getY()+speed);
				distance+=speed;
			}
		}
		else if(odir==right && (cantvisit.contains(grid[posy][posx+1])==false||grid[posy][posx+1]==5))
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posx++;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setX(rect.getX()+speed);
				distance+=speed;
			}
		}
		else if(odir==left && (cantvisit.contains(grid[posy][posx-1])==false||grid[posy][posx-1]==5))
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posx--;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setX(rect.getX()-speed);
				distance+=speed;
			}
		}
		else
		{
			blocked=true;
		}
		if(ndir==up && (cantvisit.contains(grid[posy-1][posx])==false||grid[posy-1][posx]==5) && distance == 0)
		{
			odir=ndir;
		}
		else if(ndir==down && (cantvisit.contains(grid[posy+1][posx])==false||grid[posy+1][posx]==5) && distance == 0)
		{
			odir=ndir;
		}
		else if(ndir==right && (cantvisit.contains(grid[posy][posx+1])==false||grid[posy][posx+1]==5) && distance == 0)
		{
			odir=ndir;
		}
		else if(ndir==left&& (cantvisit.contains(grid[posy][posx-1])==false||grid[posy][posx-1]==5) && distance == 0)
		{
			odir=ndir;
		}
		else if(blocked) { 
			if(sndir==up && (cantvisit.contains(grid[posy-1][posx])==false||grid[posy-1][posx]==5) && distance == 0)
			{
				odir=sndir;
			}
			else if(sndir==down && (cantvisit.contains(grid[posy+1][posx])==false||grid[posy+1][posx]==5) && distance == 0)
			{
				odir=sndir;
			}
			else if(sndir==right && (cantvisit.contains(grid[posy][posx+1])==false||grid[posy][posx+1]==5) && distance == 0)
			{
				odir=sndir;
			}
			else if(sndir==left && (cantvisit.contains(grid[posy][posx-1])==false||grid[posy][posx-1]==5) && distance == 0)
			{
				odir=sndir;
			}
		}
		animationupdate();

	}
	public void Update()
	{
		blocked=false;
		int dx = m.GetX() - posx;
		int dy = m.GetY() - posy;
		if((m.GetPos().getX()-rect.getX()==0 && Math.abs(m.GetPos().getY()-rect.getY())<=Definitions.SQUARE-collisiondistance)
			||(m.GetPos().getY()-rect.getY()==0 && Math.abs(m.GetPos().getX()-rect.getX())<=Definitions.SQUARE-collisiondistance))
		{
			if(m.isEnergized())
			{
				dead=true;
				respawntimer.Start();
				speed=toprisonspeed;
			}
			else
			{
				m.dead(true);
			}
			
		}

		if(m.isEnergized())
		{
			dx *=-1;
			dy*=-1;
		}
		SetDirection(dx, dy);
		if(odir==up && cantvisit.contains(grid[posy-1][posx])==false)
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posy--;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setY(rect.getY()-speed);
				distance+=speed;
			}
		}
		else if(odir==down && cantvisit.contains(grid[posy+1][posx])==false)
		{			
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posy++;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setY(rect.getY()+speed);
				distance+=speed;
			}
		}
		else if(odir==right && cantvisit.contains(grid[posy][posx+1])==false)
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posx++;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setX(rect.getX()+speed);
				distance+=speed;
			}
		}
		else if(odir==left && cantvisit.contains(grid[posy][posx-1])==false)
		{
			if(distance>=Definitions.SQUARE)
			{
				distance=0;
				posx--;
				rect.setX(posx*Definitions.SQUARE);
				rect.setY(posy*Definitions.SQUARE);
			}
			else
			{
				rect.setX(rect.getX()-speed);
				distance+=speed;
			}
		}
		else
		{
			blocked=true;
		}
		if(ndir==up && cantvisit.contains(grid[posy-1][posx])==false && distance == 0)
		{
			odir=ndir;
		}
		else if(ndir==down && cantvisit.contains(grid[posy+1][posx])==false && distance == 0)
		{
			odir=ndir;
		}
		else if(ndir==right && cantvisit.contains(grid[posy][posx+1])==false && distance == 0)
		{
			odir=ndir;
		}
		else if(ndir==left && cantvisit.contains(grid[posy][posx-1])==false && distance == 0)
		{
			odir=ndir;
		}
		else if(blocked) { 
			if(sndir==up && cantvisit.contains(grid[posy-1][posx])==false && distance == 0)
			{
				odir=sndir;
			}
			else if(sndir==down && cantvisit.contains(grid[posy+1][posx])==false && distance == 0)
			{
				odir=sndir;
			}
			else if(sndir==right && cantvisit.contains(grid[posy][posx+1])==false && distance == 0)
			{
				odir=sndir;
			}
			else if(sndir==left && cantvisit.contains(grid[posy][posx-1])==false && distance == 0)
			{
				odir=sndir;
			}
		}
		animationupdate();

	}
	private void animationupdate() // mise a jour de l'image à dessiner
	{
		if(animationtimer.ElapsedTimeinMilliSeconds()>animationtime)
		{
			animation = (animation+1)%2;
			animationtimer.Start();
		}
	}
	public void Draw(GameData data)
	{
		if(odir==up)
		{
			data.screen.AddImage(data.assets.GetImage("CatUp"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}
		else if(odir==down)
		{
			data.screen.AddImage(data.assets.GetImage("CatDown"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}
		else if(odir==right)
		{
			data.screen.AddImage(data.assets.GetImage("CatRight"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}
		else if(odir==left)
		{
			data.screen.AddImage(data.assets.GetImage("CatLeft"), rect,0+animation*(Definitions.SQUARE+1),0,Definitions.SQUARE,Definitions.SQUARE);
		}

	}

}
