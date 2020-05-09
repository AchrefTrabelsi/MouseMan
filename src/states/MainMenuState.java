package states;


import definitions.Definitions;
import gamedata.GameData;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


public class MainMenuState extends State {
	private GameData data;
	private Rectangle SgButton,MuButton,QButton;
	private String sgButton="Single",muButton="Multi",qButton="Quit";
	private EventHandler<MouseEvent> filter;
	private int Mousex,Mousey,rMousex,rMousey; // position de la souris lors du click et lors du lâchage
	private int x,y;//position actuelle de la souris
	//SingleClicked,MultiClicked,QuitClicked : l'état du button
	// released,clicked : l'etat de la button gauche de la souris
	private Boolean SingleClicked = false , MultiClicked = false,released = false,QuitClicked = false;
	//les dimensions et les positions des boutons
	private static double s = 0.7;
	private static double qw= 240*s;
	private static double sgw= 420*s;
	private static double muw= 380*s;
	private static double topmargin= 150;
	private static double bottommargin= 100;
	private static double bh= 85*s;
	private Boolean Clicked = false;

	
	public MainMenuState (GameData data) {
		this.data=data;
		filter = new EventHandler<javafx.scene.input.MouseEvent>() 
		{
		    public void handle(javafx.scene.input.MouseEvent e) 
		    {
				if( e.getButton()==MouseButton.PRIMARY && e.getEventType()==MouseEvent.MOUSE_PRESSED)
				{
					
					Mousex=(int) e.getX();
					Mousey=(int) e.getY();
					Clicked=true;
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
	    int m = (int) (Definitions.HEIGHT-topmargin-bottommargin-bh*3)/2;
		SgButton= new Rectangle(Definitions.WIDTH/2-sgw/2,topmargin,sgw,bh);
		MuButton= new Rectangle(Definitions.WIDTH/2-muw/2,topmargin+m+bh,muw,bh);
		QButton= new Rectangle(Definitions.WIDTH/2-qw/2,topmargin+(m+bh)*2,qw,bh);
		
	}
	@Override
	public void Delete() // arrête la musique
	{
		data.assets.MStop();
	}
	@Override
	//mise à jour de l'état des bouttons
	public void Update() {
		if(Clicked)
		{
			if(SgButton.contains(Mousex, Mousey))
			{
				SingleClicked=true;
				data.assets.Play("Click");
			}
			else if(MuButton.contains(Mousex, Mousey))
			{
				MultiClicked=true;
				data.assets.Play("Click");
			}
			else if(QButton.contains(Mousex, Mousey))
			{
				QuitClicked=true;
				data.assets.Play("Click");
			}
			Clicked=false;

		}
		if(SingleClicked && released && SgButton.contains(rMousex, rMousey) )
		{
			data.machine.AddState(new GameState(data,Definitions.SinglePlayer));
		}
		else if (MultiClicked && released && MuButton.contains(rMousex, rMousey))
		{
			data.machine.AddState(new GameState(data,Definitions.Multiplayer));
		}
		else if (QuitClicked && released && QButton.contains(rMousex, rMousey))
		{
			System.exit(0);
		}
		if(released)
		{
			released = false;
			SingleClicked=false;
			MultiClicked=false;
			QuitClicked=false;
		}
		if(SgButton.contains(x,y))
		{
			if(SingleClicked==true)
			{
				sgButton="SingleClicked";
			}
			else
			{
				sgButton = "SingleHover";
			}
			muButton="Multi";
			qButton="Quit";
		}
		else if(MuButton.contains(x,y))
		{
			if(MultiClicked==true)
			{
				muButton="MultiClicked";
			}
			else
			{
				muButton = "MultiHover";
			}
			sgButton="Single";
			qButton="Quit";
		}
		else if(QButton.contains(x,y))
		{
			if(QuitClicked==true)
			{
				qButton="QuitClicked";
			}
			else
			{
				qButton = "QuitHover";
			}
			sgButton="Single";
			muButton="Multi";
		}
		else
		{
			sgButton="Single";
			muButton="Multi";
			qButton="Quit";

		}

	}


	@Override
	//dessiner les bouttons
	public void Draw() {
		data.screen.ClearScreen();
		data.screen.AddImage(data.assets.GetImage("bg"),0,0);
		data.screen.AddImage(data.assets.GetImage(muButton), MuButton);
		data.screen.AddImage(data.assets.GetImage(sgButton), SgButton);
		data.screen.AddImage(data.assets.GetImage(qButton), QButton);
	}

	@Override
	//démarrage du son
	public void Init() {
		data.assets.MPlay("Menu",-1);
		data.assets.SetVolume(0.2);		
	}
	

}