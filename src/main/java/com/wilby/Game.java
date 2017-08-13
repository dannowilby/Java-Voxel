package com.wilby;

import org.joml.Vector2f;
import org.lwjgl.opengl.GL;

import com.wilby.display.Window;

public class Game implements IGame
{

	private Renderer renderer;
	private MouseInput mouseInput;
	
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
		KeyboardInput.initialise(window);
		mouseInput = new MouseInput();
		mouseInput.initialise(window);
		renderer.initialise(window);
	}

	@Override
	public void input(Window window) 
	{
		KeyboardInput.handle(window, mouseInput);
		mouseInput.handle(window);
	}

	private float CAMERA_POS_STEP = .1f;
	private float MOUSE_SENSITIVITY = .2f;
	
	@Override
	public void update() 
	{
		renderer.cam().movePosition(Renderer.getCamera().x * CAMERA_POS_STEP,
		        Renderer.getCamera().y * CAMERA_POS_STEP,
		        Renderer.getCamera().z * CAMERA_POS_STEP);

		    if (mouseInput.isRightButtonPressed()) {
		        Vector2f rotVec = mouseInput.getDisplVec();
		        renderer.cam().moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
		    }
	}

	@Override
	public void render(Window window) 
	{
		renderer.render(window);
	}	
		
}
