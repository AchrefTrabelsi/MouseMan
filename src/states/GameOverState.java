package states;


import definitions.Definitions;
import gamedata.GameData;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


public class GameOverState extends State {
	private GameData data;
	private Rectangle MButton,RButton;
	private String sgButton="MainMenu",muButton="Restart";
	private EventHandler<MouseEvent> filter;
	private int Mousex,Mousey,rMousex,rMousey;
	private int x,y;
	private Boolean MainMenuClicked = false  , RestartClicked = false,released = false; 
	private static double s = 0.7;
	private static double mw= 350*s;
	private static double rw= 300*s;
	private static double topmargin= 250;
	private static double bottommargin= 200;
	private static double bh= 85*s;
	private int type ;
	private Boolean drawn=false;
	private String wol;
	private Boolean Clicked=false;

	
	public GameOverState (GameData data,int type,String wol) {
		this.data=data;
		this.type=type;
		this.wol=wol;
		if(wol=="Won")
		{
			data.assets.Play("Victory");
		}
		else
		{
			data.assets.Play("Fail");
		}
		filter = new EventHandler<javafx.scene.input.MouseEvent>() 
		{
		    public void handle(javafx.scene.input.MouseEvent e) 
		    {
				if( e.getButton()==MouseButton.PRIMARY && e.getEventType()==MouseEvent.MOUSE_PRESSED)
				{
					Clicked=true;
					Mousex=(int) e.getX();
					Mousey=(int) e.getY();
				}
				else if( e.getButton()==MouseButton.PRIMARY && e.getEventType()==MouseEvent.MOUSE_RELEASED)
				{
					Mousex=(int) 0;
					Mousey=(int) 0;
					rMousex=(int) e.getX();
					rMousey=(int) e.getY();
					released = true;
				}
				x=(int) e.getX();
				y=(int) e.getY();

		    }
		    
	    };
	    Definitions.stage.addEventFilter(MouseEvent.ANY,filter);
	    int m = (int) (Definitions.HEIGHT-topmargin-bottommargin-bh*2);
		MButton= new Rectangle(Definitions.WIDTH/2-mw/2,topmargin+m+bh,mw,bh);
		RButton= new Rectangle(Definitions.WIDTH/2-rw/2,topmargin,rw,bh);
		
	}

	@Override
	public void Update() {
		if(Clicked)
		{
			if(MButton.contains(Mousex, Mousey))
			{
				data.assets.Play("Click");
				MainMenuClicked=true;
			}
			else if(RButton.contains(Mousex, Mousey))
			{
				data.assets.Play("Click");
				RestartClicked=true;
			}
			Clicked=false;
		}

		if(MainMenuClicked && released && MButton.contains(rMousex, rMousey) )
		{
			data.machine.AddState(new MainMenuState(data));
		}
		else if (RestartClicked && released && RButton.contains(rMousex, rMousey))
		{
			data.machine.AddState(new GameState(data,type));
		}
		if(released)
		{
			released = false;
			MainMenuClicked=false;
			RestartClicked=false;
		}
		if(MButton.contains(x,y))
		{
			if(MainMenuClicked==true)
			{
				sgButton="MainMenuClicked";
			}
			else
			{
				sgButton = "MainMenuHover";
			}
			muButton="Restart";
		}
		else if(RButton.contains(x,y))
		{
			if(RestartClicked==true)
			{
				muButton="RestartClicked";
			}
			else
			{
				muButton = "RestartHover";
			}
			sgButton="MainMenu";
		}
		else
		{
			sgButton="MainMenu";
			muButton="Restart";

		}

	}


	@Override
	public void Draw() {
		if(!drawn)
		{
		    data.screen.AddImage(data.assets.GetImage(wol), 0,0);
			drawn=true;
		}
		data.screen.AddImage(data.assets.GetImage(muButton), RButton);
		data.screen.AddImage(data.assets.GetImage(sgButton), MButton);
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}
	

}