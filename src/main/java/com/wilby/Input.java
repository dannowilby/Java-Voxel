package com.wilby;

import org.lwjgl.glfw.GLFW;

import com.wilby.display.Display;
import com.wilby.display.Window;
import com.wilby.model.GameItem;

public class Input 
{
	
	public static void handle(Window window)
	{
		if(Input.isKeyPressed(window, GLFW.GLFW_KEY_ESCAPE))
		{
			window.setRunning(false);
		}
	}
	
	public static void setupCallbacks(Window window)
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
