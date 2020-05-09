package gamedata;





import definitions.Definitions;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import utilities.Rect;

public class Display {
	private  Canvas canvas;
	private  int Width,Height;
	private  GraphicsContext  Gfx;
	public Display(int width , int height)
	{
		
		this.Width = width;
		this.Height = height;
		this.CreateDisplay();
		Gfx = canvas.getGraphicsContext2D();
	}
	private void CreateDisplay()
	{
		canvas = new Canvas(Width,Height);
		Definitions.root.getChildren().add(canvas);
	}
	//dessiner l'image
	public void AddImage(Image img , int posx, int posy,int width,int height)
	{
		Gfx.drawImage(img, posx, posy,width,height);
	}
	public void AddImage(Image img , int posx, int posy)
	{
		Gfx.drawImage(img, posx, posy);
	}

	public void AddImage(Image img , Rectangle rect)
	{
		
		Gfx.drawImage(img, rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
	}
	//effacer l'écran
	public void ClearScreen()
	{
		Gfx.clearRect(0,0,canvas.getWidth(),canvas.getHeight());
	}
	public void Show()
	{
	}
	public GraphicsContext GetGfx()
	{
		return Gfx;
	}
	public Canvas GetCanvas()
	{
		return canvas;
	}
	public void AddImage(Image img, Rectangle rect, int i, int j, int w, int h) {
		Gfx.drawImage(img,i,j,w,h, rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
		
	}
	public void AddImage(Image img, Rect rect, int i, int j, int w, int h) {
		Gfx.drawImage(img,i,j,w,h, rect.getX(),rect.getY(),rect.getWidth(),rect.getHeight());
		
	}

}
