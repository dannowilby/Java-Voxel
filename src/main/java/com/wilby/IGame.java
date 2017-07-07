package com.wilby;

import com.wilby.display.Window;

public interface IGame 
{
	
	void initialise(Window window) throws Exception;
	
	void input(Window window);
	
	void update();
	
	void render(Window window);
	
}
