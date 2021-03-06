package states;

import java.io.Serializable;

import definitions.Definitions;
import gamedata.GameData;
import gameobjects.AutoCat;
import gameobjects.Cat;
import gameobjects.ManualCat;
import gameobjects.Mouse;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameState extends State implements Serializable{
	private static final long serialVersionUID = 1L;
	//la grille du jeu
	private int[][] level= {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,3,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,3,1},
			{1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,1,1,1,1,6,1,1,1,6,1,1,1,1,6,1},
			{1,6,1,1,1,1,6,1,1,1,6,1,1,1,1,1,1,1,1,6,1,1,1,6,1,1,1,1,6,1},
			{1,6,6,6,6,6,6,1,1,1,6,6,6,6,6,6,6,6,6,6,1,1,1,6,6,6,6,6,6,1},
			{1,1,1,1,1,1,6,6,6,6,6,1,1,1,1,1,1,1,1,6,6,6,6,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,1,1,1,6,1,1,1,1,1,1,1,1,6,1,1,1,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,1,1,1,6,0,0,0,0,0,0,0,0,6,1,1,1,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,1,1,1,6,1,1,1,5,5,1,1,1,6,1,1,1,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,1,1,1,6,1,5,5,5,5,5,5,1,6,1,1,1,6,1,1,1,1,1,1},
			{0,0,0,0,0,0,6,1,1,1,6,1,5,5,5,5,5,5,1,6,1,1,1,6,0,0,0,0,0,0},
			{1,1,1,1,1,1,6,1,1,1,6,1,5,5,5,5,5,5,1,6,1,1,1,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,1,1,1,6,1,1,1,1,1,1,1,1,6,1,1,1,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1},
			{1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1,1,1,1,1,6,1,1,6,1,1,1,1,1,1},
			{1,6,6,6,6,6,6,1,1,6,6,6,6,1,10,10,1,6,6,6,6,1,1,6,6,6,6,6,6,1},
			{1,6,1,1,1,1,1,1,1,1,1,1,6,1,10,10,1,6,1,1,1,1,1,1,1,1,1,1,6,1},
			{1,6,1,1,1,1,1,1,1,1,1,1,6,1,10,10,1,6,1,1,1,1,1,1,1,1,1,1,6,1},
			{1,3,6,6,6,6,6,6,6,6,6,6,6,0,0,0,0,6,6,6,6,6,6,6,6,6,6,6,3,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
	};
	private int type; //multijoueur ou joueur unique
	private GameData data;
	private Mouse s; //souris
	private Cat c; // chat
	private  Boolean GameOver = false; // jeux terminer ou en cours
	private  Boolean Won = false; // souris � gagner ou jeu en cours
	public  Boolean p = false; // jeu en pause ou non 
	private EventHandler<KeyEvent> filter;
	public void SetData(GameData data)	
	{
		this.data=data;
	}
	public int GetType()
	{
		return type;
	}

	public GameState(GameData data,int type) {
		this.data=data;
		s= new Mouse(level,10,7);
		this.type=type;
		if(type==Definitions.SinglePlayer)
			c = new AutoCat(level,18,1,s,15,10);
		else
			c = new ManualCat(level,18,1,s,15,10);
	}
	@Override
	//mise � jour du jeu
	public void Update() {
		s.Update(data.assets);
		if(c.isdead())
			c.ToPrison();
		else
			c.Update();
		if(s.isDead())
		{
			GameOver=true;
			Won=false;
		}
		if(s.Won())
		{
			GameOver=true;
			Won=true;
		}
		if(GameOver)
		{
			if(Won)
			{
				data.machine.AddState(new GameOverState(data, type, "Won"));
			}
			else
			{
				data.machine.AddState(new GameOverState(data, type, "Lost"));
			}
		}
	}
	@Override
	//pauser le jeu
	public void pause()
	{
		c.Pause();
		s.Pause();
	}
	@Override
	//reprendre le jeu
	public void resume()
	{
		c.Resume();
		s.Resume();
	}

	@Override
	//dessiner
	public void Draw() {
		data.screen.ClearScreen();
		if(s.ReachedObjective())
			data.screen.AddImage(data.assets.GetImage("MapFinish"), 0, 0);
		else
			data.screen.AddImage(data.assets.GetImage("Map"), 0, 0);
		for(int i=0;i<level.length;i++)
			for(int j=0 ; j<level[i].length;j++)
			{
				if(level[i][j]==Definitions.ENERGIE_DRINK)
				{
					data.screen.AddImage(data.assets.GetImage("EnergyDrink"),j*Definitions.SQUARE, i*Definitions.SQUARE, Definitions.SQUARE, Definitions.SQUARE);
				}
				else if(level[i][j]==Definitions.CHEESE)
				{
					data.screen.AddImage(data.assets.GetImage("Cheese"),j*Definitions.SQUARE, i*Definitions.SQUARE, Definitions.SQUARE, Definitions.SQUARE);
				}
			}
		s.Draw(data);
		c.Draw(data);
	}

	@Override
	//ajout du filtre
	public void Init() {
		GameState g= this;
		filter = new EventHandler<KeyEvent>() {
		    public void handle(KeyEvent e) {
				if(e.getCode()==KeyCode.ESCAPE)
				{
					if(p==false)
					{
						data.machine.AddState(new PauseMenuState(data,g), false);
					}
					else
						data.machine.RemoveState();
					p=!p;
				}
		    }};
		Definitions.stage.addEventFilter(KeyEvent.KEY_PRESSED,filter);
		c.init();
		s.init();
	}
	@Override
	//suppression du filtre
	public void Delete()
	{
		Definitions.stage.removeEventFilter(KeyEvent.KEY_PRESSED,filter);
		filter=null;
	}
}
