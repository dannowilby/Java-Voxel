package com.wilby.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
		String l = "src/main/resources/";
		
		return l + n;
	}
	
	public ResourceLocation model(String location)
	{
		String f = resourceLocation("com/wilby/models/") + location;
		
		return new ResourceLocation(f);
	}
	
	public String loadShader(String shader) throws Exception
	{
		String f = resourceLocation("com/wilby/shaders/") + shader;
		
		StringBuilder src = new StringBuilder();
		
		BufferedReader br = new BufferedReader(new FileReader(f));
		
		String line;
		while((line = br.readLine()) != null)
		{
			src.append(line).append("\n");
		}
		
		return src.toString();	
	}

	public static String texture(String string) 
	{
		String t = "" + string;
		System.out.println(new File(t).getAbsolutePath());
		return t;
	}
	
}
