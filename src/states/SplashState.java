package states;

import definitions.Definitions;
import gamedata.GameData;
import utilities.Clock;

public class SplashState extends State {
	private Boolean drawn = false;
	private Clock c = new Clock();
	private GameData data;
	public SplashState(GameData data)
	{
		this.data=data;
		data.assets.LoadImage("logo", Definitions.LOGO);
		c.Start();
	}

	@Override
	public void Update() {
		if(drawn)
		{
			data.assets.LoadSound("Click", Definitions.PATH_CLICKS);
			data.assets.LoadSound("Victory", Definitions.PATH_VICTORYS);
			data.assets.LoadSound("Fail", Definitions.PATH_FAILS);
			data.assets.LoadSound("Menu", Definitions.PATH_MENUS);
			data.assets.LoadSound("Energy", Definitions.PATH_ENERGYS);
			data.assets.LoadImage("Cheese", Definitions.PATH_CHEESE);
			data.assets.LoadImage("Won", Definitions.PATH_WIN);
			data.assets.LoadImage("Lost", Definitions.PATH_LOSE);
			data.assets.LoadImage("MouseUp", Definitions.PATH_MOUSE_UP);
			data.assets.LoadImage("MouseDown", Definitions.PATH_MOUSE_DOWN);
			data.assets.LoadImage("MouseLeft", Definitions.PATH_MOUSE_LEFT);
			data.assets.LoadImage("MouseRight", Definitions.PATH_MOUSE_RIGHT);
			data.assets.LoadImage("eMouseUp", Definitions.PATH_EMOUSE_UP);
			data.assets.LoadImage("eMouseDown", Definitions.PATH_EMOUSE_DOWN);
			data.assets.LoadImage("eMouseLeft", Definitions.PATH_EMOUSE_LEFT);
			data.assets.LoadImage("eMouseRight", Definitions.PATH_EMOUSE_RIGHT);
			data.assets.LoadImage("CatUp", Definitions.PATH_CAT_UP);
			data.assets.LoadImage("CatDown", Definitions.PATH_CAT_DOWN);
			data.assets.LoadImage("CatLeft", Definitions.PATH_CAT_LEFT);
			data.assets.LoadImage("CatRight", Definitions.PATH_CAT_RIGHT);
			data.assets.LoadImage("Map", Definitions.PATH_MAP);
			data.assets.LoadImage("MapFinish", Definitions.PATH_MAP_FINISHING);
			data.assets.LoadImage("EnergyDrink", Definitions.PATH_ENERGIE_DRINK);
			data.assets.LoadImage("bg", Definitions.PATH_BACKGROUND);
			data.assets.LoadImage("bg0", Definitions.PATH_BACKGROUND0);
			data.assets.LoadImage("Multi", Definitions.PATH_MULTIPLAYER);
			data.assets.LoadImage("MultiClicked", Definitions.PATH_MULTIPLAYER_CLICKED);
			data.assets.LoadImage("MultiHover", Definitions.PATH_MULTIPLAYER_HOVER);
			data.assets.LoadImage("Single", Definitions.PATH_SINGLEPLAYER);
			data.assets.LoadImage("SingleClicked", Definitions.PATH_SINGLEPLAYER_CLICKED);
			data.assets.LoadImage("SingleHover", Definitions.PATH_SINGLEPLAYER_HOVER);
			data.assets.LoadImage("Options", Definitions.PATH_OPTIONS);
			data.assets.LoadImage("OptionsHover", Definitions.PATH_OPTIONS_HOVER);
			data.assets.LoadImage("OptionsClicked", Definitions.PATH_OPTIONS_CLICKED);
			data.assets.LoadImage("Quit", Definitions.PATH_QUIT);
			data.assets.LoadImage("QuitHover", Definitions.PATH_QUIT_HOVER);
			data.assets.LoadImage("QuitClicked", Definitions.PATH_QUIT_CLICKED);
			data.assets.LoadImage("Resume", Definitions.PATH_RESUME);
			data.assets.LoadImage("ResumeHover", Definitions.PATH_RESUME_HOVER);
			data.assets.LoadImage("ResumeClicked", Definitions.PATH_RESUME_CLICKED);
			data.assets.LoadImage("Save", Definitions.PATH_SAVE);
			data.assets.LoadImage("SaveHover", Definitions.PATH_SAVE_HOVER);
			data.assets.LoadImage("SaveClicked", Definitions.PATH_SAVE_CLICKED);
			data.assets.LoadImage("Load", Definitions.PATH_LOAD);
			data.assets.LoadImage("LoadHover", Definitions.PATH_LOAD_HOVER);
			data.assets.LoadImage("LoadClicked", Definitions.PATH_LOAD_CLICKED);
			data.assets.LoadImage("MainMenu", Definitions.PATH_MAINMENU);
			data.assets.LoadImage("MainMenuHover", Definitions.PATH_MAINMENU_HOVER);
			data.assets.LoadImage("MainMenuClicked", Definitions.PATH_MAINMENU_CLICKED);
			data.assets.LoadImage("Restart", Definitions.PATH_RESTART);
			data.assets.LoadImage("RestartHover", Definitions.PATH_RESTART_HOVER);
			data.assets.LoadImage("RestartClicked", Definitions.PATH_RESTART_CLICKED);

			c.Pause();
			if(Definitions.SPLASH_STATE_TIME>c.ElapsedTimeinSeconds())
			{
				try {
					Thread.sleep((long) (Definitions.SPLASH_STATE_TIME*1000-c.ElapsedTimeinMilliSeconds()));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			data.machine.AddState(new MainMenuState(data));
		}
		
	}

	@Override
	public void Draw() {
		data.screen.AddImage(data.assets.GetImage("logo"), 0,0);
		drawn=true;
		
	}

	@Override
	public void Init() {
		// TODO Auto-generated method stub
		
	}

}
