package com.wilby;

import org.lwjgl.opengl.GL;

import com.wilby.display.Window;
import com.wilby.model.Mesh;

public class Game implements IGame
{

	private Renderer renderer;
	
	public Game()
	{
		renderer = new Renderer();
	}
	
	public void loop(Window window)
	{
		try
		{
			initialise(window);
			
			while(window.shouldBeRunning())
			{
				renderer.clear(window);
				input(window);
				update();
				render(window);
			}
			
			renderer.cleanup();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialise(Window window) throws Exception 
	{
		GL.createCapabilities();
		Input.setupCallbacks(window);
		renderer.initialise();
	}

	@Override
	public void input(Window window) 
	{
		Input.handle(window);
	}

	@Override
	public void update() 
	{
		
	}

	@Override
	public void render(Window window) 
	{
		renderer.render(window);
	}	
		
}
