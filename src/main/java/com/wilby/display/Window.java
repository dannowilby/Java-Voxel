package com.wilby.display;

public class Window 
{
	
	private long window;
	
	private boolean run = true;
	
	private double fps = 1d;
	
	public Window(long window)
	{
		this.window = window;
	}
	
	public void setWindow(long window)
	{
		this.window = window;
	}
	
	public long getWindow()
	{
		return this.window;
	}
	
	public boolean shouldBeRunning()
	{
		return this.run;
	}
	
	public void setRunning(boolean run)
	{
		this.run = run;
	}
	
	public double getFPS()
	{
		return this.fps;
	}
	
	public void setFPS(double newFPS)
	{
		this.fps = newFPS;
	}
}
