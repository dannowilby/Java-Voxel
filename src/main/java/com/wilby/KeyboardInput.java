package com.wilby;

import org.lwjgl.glfw.GLFW;

import com.wilby.display.Display;
import com.wilby.display.Window;
import com.wilby.model.GameItem;

public class KeyboardInput 
{
	
	public static void handle(Window window, MouseInput mi)
	{
		if(KeyboardInput.isKeyPressed(window, GLFW.GLFW_KEY_ESCAPE))
		{
			window.setRunning(false);
		}
		
		Renderer.getCamera().set(0, 0, 0);
	    if (isKeyPressed(window, GLFW.GLFW_KEY_W)) 
	    {
	    	Renderer.getCamera().z = -1;
	    } 
	    else if (isKeyPressed(window, GLFW.GLFW_KEY_S)) 
	    {
	    	Renderer.getCamera().z = 1;
	    }
	    if (isKeyPressed(window, GLFW.GLFW_KEY_A)) 
	    {
	    	Renderer.getCamera().x = -1;
	    } 
	    else if (isKeyPressed(window, GLFW.GLFW_KEY_D)) 
	    {
	    	Renderer.getCamera().x = 1;
	    }
	    if (isKeyPressed(window, GLFW.GLFW_KEY_Z)) 
	    {
	    	Renderer.getCamera().y = -1;
	    } 
	    else if (isKeyPressed(window, GLFW.GLFW_KEY_X)) 
	    {
	    	Renderer.getCamera().y = 1;
	    }
	}
	
	public static void initialise(Window window)
	{	
		GLFW.glfwSetFramebufferSizeCallback(window.getWindow(), (windows, width, height) -> {
			Display.setWidth(width);
			Display.setHeight(height);
			Display.setResized(true);
		});
	}
	
	public static boolean isKeyPressed(Window window, int keyCode)
	{
		return GLFW.glfwGetKey(window.getWindow(), keyCode) == GLFW.GLFW_PRESS;
	}
	
}
