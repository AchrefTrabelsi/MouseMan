package gamedata;

import java.util.HashMap;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class AssetManager {
	private HashMap<String,Image> Assets;
	private HashMap<String,Media> Sounds;
	private MediaPlayer mp=null;
	public AssetManager()
	{
		Assets = new HashMap<String,Image>();
		Sounds = new HashMap<String,Media>();
	}
	public void LoadImage(String name,String Path)
	{
		Assets.put(name, new Image(AssetManager.class.getResourceAsStream("/textures/"+Path)));
	}
	public Image GetImage(String name)
	{
		return Assets.get(name);
	}
	public void LoadSound(String name,String Path)
	{
		Sounds.put(name, new Media(AssetManager.class.getResource("/sound/"+Path).toString()));
	}
	public Media GetSound(String name)
	{
		return Sounds.get(name);
	}
	public void MPlay(String s,int cyclecount )
	{
		if(mp!=null)
			mp.stop();
        mp = new MediaPlayer(Sounds.get(s));
        mp.setCycleCount(cyclecount);
        mp.play();
	}
	public void MPlay(String s)
	{
		if(mp!=null)
			mp.stop();
        mp = new MediaPlayer(Sounds.get(s));
        mp.play();
	}
	public void Play(String s)
	{
		MediaPlayer mp0 = new MediaPlayer(Sounds.get(s));
        mp0.play();
	}
	public void SetVolume(Double v)
	{
		if(mp!=null)
			mp.setVolume(v);
	}
	public void MStop()
	{
		if(mp!=null)
			mp.stop();
	}



}
