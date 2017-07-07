package com.wilby.util;

import java.io.File;
import java.io.IOException;

public class ResourceLocation 
{
	
	private String name;
	
	public ResourceLocation()
	{
		this.name = "";
	}
	
	public ResourceLocation(String location)
	{
		this.name = location;
	}
	
	public String getPath()
	{
		return this.name;
	}
	
	public File getFile()
	{
		return new File(this.name);
	}
	
	private String resourceLocation(String n)
	{
		String l = "/src/main/resources/";
		
		return l + n;
	}
	
	public ResourceLocation model(String location)
	{
		String f = resourceLocation("com/wilby/models/") + location;
		
		return new ResourceLocation(f);
	}
	
	public ResourceLocation shader(String location)
	{
		String f = resourceLocation("com/wilby/shaders/") + location;
		
		return new ResourceLocation(f);	
	}
	
}
