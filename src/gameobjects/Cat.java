package gameobjects;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import gamedata.GameData;
import utilities.Clock;
import utilities.Rect;

public abstract class Cat implements Serializable {
	private static final long serialVersionUID = 1L;
	protected final int deathtime = 4000; //durée du mort du chat
	protected final int animationtime = 180;	
	protected final int up=1,right=2,down=3,left=4,none=0;
	protected int[][] grid; // Grille
	protected int posy,posx; //postion dans la grille
	protected int ndir; //nouvelle direction
	protected boolean dead = false; // Etat du chat mort/vivant
	protected int sndir; // direction secondaire (utilisé lorsque le chat est bloqué)
	protected boolean blocked = false; // chat est bloqué ou pas
	protected int odir = none; // direction actuelle
	protected final double toprisonspeed = 10; //vitesse du chat lorsque il est mort
	protected double distance = 0; // distance entre le chat et la case où il est.
	protected Mouse m; // souris pour pouvoir recupérer son état et sa position
	protected int px,py; // position de la case prison
	protected Rect rect ;
	protected final static List<Integer> cantvisit = Arrays.asList(1,5,10); // les cases que le chat ne peut pas visiter
	protected int animation = 0; 

	public abstract void ToPrison(); // le chat va au prison
	public abstract void Update(); // Mise a jour de la position et la direction du chat lorsque il est vivant
	public abstract void Draw(GameData data); // Dessiner le chat
	public abstract boolean isdead(); // retourne l'etat du chat
	protected Clock respawntimer = new Clock(); // chronométre du respawn du chat
	protected Clock animationtimer = new Clock();
	protected final int collisiondistance = 5; // distance de collision
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
