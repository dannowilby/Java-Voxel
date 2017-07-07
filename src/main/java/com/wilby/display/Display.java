package com.wilby.display;

import java.nio.IntBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import com.wilby.Game;
import com.wilby.Renderer;

public class Display 
{
	
	private static long window;
	
	private static int x1 = 0, y1 = 0;
	
	private static boolean re = false;
	
	public static Window create(String title, int sizeX, int sizeY)
	{
		x1 = sizeX;
		y1 = sizeY;
		
		GLFWErrorCallback.createPrint(System.err).set();
		
		if (!GLFW.glfwInit())
		{
			throw new IllegalStateException("UNABLE TO INITIALISE");
		}
		
		GLFW.glfwDefaultWindowHints();
		GLFW.glfwWindowHint(GLFW.GLFW_VISIBLE, GLFW.GLFW_FALSE);
		GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_TRUE);
		
		window = GLFW.glfwCreateWindow(x1, y1, title, 0, 0);
		
		if (window == 0)
		{
			throw new RuntimeException("FAILED TO CREATE THE WINDOW");
		}

		try ( MemoryStack stack = MemoryStack.stackPush() ) 
		{
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			GLFW.glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

			GLFW.glfwSetWindowPos(
				window,
				(vidmode.width() - pWidth.get(0)) / 2,
				(vidmode.height() - pHeight.get(0)) / 2
			);
		}
		
		GLFW.glfwMakeContextCurrent(window);
		
		GLFW.glfwSwapInterval(1);
		
		GLFW.glfwShowWindow(window);
		
		Window w = new Window(window);
		
		return w;
	}
	
	public static int getHeight()
	{
		return y1;
	}
	
	public static int getWidth()
	{
		return x1;
	}
	
	public static void setWidth(int x)
	{
		x1 = x;
	}
	
	public static void setHeight(int y)
	{
		y1 = y;
	}
	
	public static void destroy(Window window)
	{
		
		GLFW.glfwDestroyWindow(window.getWindow());

		GLFW.glfwTerminate();
		GLFW.glfwSetErrorCallback(null).free();
		
	}

	public static void setResized(boolean b) 
	{
		re = b;
	}
	
	public static boolean isResized()
	{
		return re;
	}
	
}
