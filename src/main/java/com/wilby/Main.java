package com.wilby;

import org.lwjgl.Version;
import org.lwjgl.opengl.*;

import com.wilby.display.Display;
import com.wilby.display.Window;

public class Main implements Runnable 
{
	
	private Window game;
	
	public static void main(String[] args) 
	{
		new Main().run();
	}
	
	@Override
	public void run()
	{
		
		System.out.println("LWJGL Version " + Version.getVersion());
		
		game = Display.create("Argh", 300, 300);
		
		new Game().loop(game);
		
		Display.destroy(game);
	}
	
}
