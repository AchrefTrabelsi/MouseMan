package utilities;

import java.io.Serializable;


public class Rect  implements Serializable{
	private static final long serialVersionUID = 1L;
	private double x,y,w,h;
	public Rect(double x,double y,double w,double h)
	{
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}
	public double getX()
	{
		return x;
	}
	public double getY()
	{
		return y;
	}
	public void setX(double a)
	{
		x=a;
	}
	public void setY(double a)
	{
		y=a;
	}
	public double getWidth() {
		return w;
	}
	public double getHeight() {
		return h;
	}


}
