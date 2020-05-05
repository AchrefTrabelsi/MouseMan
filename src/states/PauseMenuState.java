package states;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import definitions.Definitions;
import gamedata.GameData;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;


public class PauseMenuState extends State {
	private GameData data;
	private Rectangle RButton,SButton,LButton,QButton;
	private String rButton="Resume",sButton="Save",lButton="Load",qButton="MainMenu";
	private EventHandler<MouseEvent> filter;
	private int Mousex,Mousey,rMousex,rMousey;
	private int x,y;
	private Boolean ResumeClicked = false , LoadClicked = false , SaveClicked = false,released = false,MainMenuClicked = false;
	private static double s = 0.7;
	private static double qw= 350*s;
	private static double rw= 300*s;
	private static double lw= 240*s;
	private static double sw= 240*s;
	private static double topmargin= 150;
	private static double bottommargin= 100;
	private static double bh= 85*s;
	private Boolean drawn =false;
	private GameState gstate;
	private Boolean Clicked = false;

	
	public PauseMenuState (GameData data,GameState s) {
		this.data=data;
		gstate=s;
		
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
	    int m = (int) (Definitions.HEIGHT-topmargin-bottommargin-bh*4)/3;
		RButton= new Rectangle(Definitions.WIDTH/2-rw/2,topmargin,rw,bh);
		SButton= new Rectangle(Definitions.WIDTH/2-sw/2,topmargin+m+bh,sw,bh);
		LButton= new Rectangle(Definitions.WIDTH/2-lw/2,topmargin+(m+bh)*2,lw,bh);
		QButton= new Rectangle(Definitions.WIDTH/2-qw/2,topmargin+(m+bh)*3,qw,bh);
		
	}
	private void Save()
	{
		String myDocuments =new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		File dir = new File(myDocuments+"\\MouseMan");
		gstate.SetData(null);
		gstate.p=false;
		gstate.Delete();
		dir.mkdir();
		String path;
		if(gstate.GetType()==Definitions.SinglePlayer)
		{
			path=myDocuments+"\\MouseMan\\sdata.save";
		}
		else 
		{
			path=myDocuments+"\\MouseMan\\mdata.save";
		}
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
			objectOut.writeObject(gstate);
            objectOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			gstate.SetData(data);
			gstate.Init();
			gstate.p=true;
		} catch (IOException e) {
			e.printStackTrace();
			gstate.SetData(data);
			gstate.Init();
			gstate.p=true;
		}
		gstate.SetData(data);
		gstate.Init();
		gstate.p=true;
	}
	private void Load()
	{
		String myDocuments =new JFileChooser().getFileSystemView().getDefaultDirectory().toString();
		String path;
		if(gstate.GetType()==Definitions.SinglePlayer)
		{
			path=myDocuments+"\\MouseMan\\sdata.save";
		}
		else 
		{
			path=myDocuments+"\\MouseMan\\mdata.save";
		}

		try {
			File f = new File(path);
			if(!f.exists())
			{
				return;
			}
			FileInputStream fileInputStream = new FileInputStream(f);
			ObjectInputStream input = new ObjectInputStream(fileInputStream);
			gstate = (GameState) input.readObject();
			gstate.SetData(data);
			gstate.resume();
			data.machine.AddState(gstate);
			data.machine.RemoveState();
			input.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void Update() {
		if(Clicked)
		{
			if(RButton.contains(Mousex, Mousey))
			{
				ResumeClicked=true;
				data.assets.Play("Click");
			}
			else if(SButton.contains(Mousex, Mousey))
			{
				SaveClicked=true;
				data.assets.Play("Click");
			}
			else if(LButton.contains(Mousex, Mousey))
			{
				LoadClicked=true;
				data.assets.Play("Click");
			}
			else if(QButton.contains(Mousex, Mousey))
			{
				MainMenuClicked=true;
				data.assets.Play("Click");
			}
			Clicked=false;

		}
		if(ResumeClicked && released && RButton.contains(rMousex, rMousey) )
		{
			data.machine.RemoveState();	
			gstate.p=false;
		}
		else if (SaveClicked && released && SButton.contains(rMousex, rMousey))
		{
			Save();		
		}
		else if (LoadClicked && released && LButton.contains(rMousex, rMousey))
		{
			Load();
		}
		else if (MainMenuClicked && released && QButton.contains(rMousex, rMousey))
		{
			data.machine.RemoveState();
			data.machine.AddState(new MainMenuState(data));
		}
		if(released)
		{
			released = false;
			ResumeClicked=false;
			SaveClicked=false;
			LoadClicked=false;
			MainMenuClicked=false;
		}
		if(RButton.contains(x,y))
		{
			if(ResumeClicked==true)
			{
				rButton="ResumeClicked";
			}
			else
			{
				rButton = "ResumeHover";
			}
			sButton="Save";
			lButton="Load";
			qButton="MainMenu";
		}
		else if(LButton.contains(x,y))
		{
			if(LoadClicked==true)
			{
				lButton="LoadClicked";
			}
			else
			{
				lButton = "LoadHover";
			}
			rButton="Resume";
			sButton="Save";
			qButton="MainMenu";
		}
		else if(SButton.contains(x,y))
		{
			if(SaveClicked==true)
			{
				sButton="SaveClicked";
			}
			else
			{
				sButton = "SaveHover";
			}
			rButton="Resume";
			lButton="Load";
			qButton="MainMenu";
		}
		else if(QButton.contains(x,y))
		{
			if(MainMenuClicked==true)
			{
				qButton="MainMenuClicked";
			}
			else
			{
				qButton = "MainMenuHover";
			}
			rButton="Resume";
			sButton="Save";
			lButton="Load";
		}
		else
		{
			rButton="Resume";
			sButton="Save";
			lButton="Load";
			qButton="MainMenu";

		}

	}


	@Override
	public void Draw() {
		if(!drawn)
		{
			data.screen.AddImage(data.assets.GetImage("bg0"),0,0);
			drawn=true;
		}
		data.screen.AddImage(data.assets.GetImage(sButton), SButton);
		data.screen.AddImage(data.assets.GetImage(rButton), RButton);
		data.screen.AddImage(data.assets.GetImage(qButton), QButton);
		data.screen.AddImage(data.assets.GetImage(lButton), LButton);
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}
	

}