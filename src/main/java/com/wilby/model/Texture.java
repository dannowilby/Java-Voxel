package com.wilby.model;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public class Texture 
{
	
	private int id;
	
	public int getId()
	{
		return id;
	}
	
	public Texture(String fileName) throws Exception
	{
		this.id = this.loadTexture(fileName);
	}
	
	public Texture(int id)
	{
		this.id = id;
	}
	
	public void bind()
	{
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
	}

	private int loadTexture(String fileName) throws IOException 
	{
		PNGDecoder decoder = new PNGDecoder(Texture.class.getResourceAsStream(fileName));
		ByteBuffer buf = ByteBuffer.allocateDirect(4 * decoder.getWidth() * decoder.getHeight());
		
		decoder.decode(buf, decoder.getWidth() * 4, Format.RGBA);
		
		buf.flip();
		
		int textureId = GL11.glGenTextures();
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		
		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, decoder.getWidth(), decoder.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);
		
		GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
		
		return textureId;
	}
	
	public void cleanup()
	{
		GL11.glDeleteTextures(id);
	}
}
