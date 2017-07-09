package com.wilby.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh 
{
	
	private int vaoId;
	
	private List<Integer> vbos = new ArrayList<Integer>();
	
	private int vboId;
	
	private int idxVboId;
	
	private int textVboId;
	
	private int vertexCount;
	
	private Texture texture;
	
	public void render()
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		
		GL30.glBindVertexArray(this.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, this.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
		GL20.glDisableVertexAttribArray(0);
		GL20.glDisableVertexAttribArray(1);
		GL30.glBindVertexArray(0);
	}
	
	public Mesh(float[] positions, int[] indices, float[] textCoords, Texture texture)
	{
		FloatBuffer verticesBuffer = null;
		FloatBuffer colourBuffer = null;
		IntBuffer indicesBuffer = null;
		
		this.texture = texture;
		
		vertexCount = indices.length;
		
		try
		{
			verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
			
			vaoId = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoId);
			
			vboId = GL15.glGenBuffers();
			vbos.add(vboId);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
			verticesBuffer.put(positions).flip();
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			idxVboId = GL15.glGenBuffers();
			vbos.add(idxVboId);
			indicesBuffer = MemoryUtil.memAllocInt(indices.length);
			indicesBuffer.put(indices).flip();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, idxVboId);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
			MemoryUtil.memFree(indicesBuffer);
			
			textVboId = GL15.glGenBuffers();
			vbos.add(textVboId);
			colourBuffer = MemoryUtil.memAllocFloat(textCoords.length);
			colourBuffer.put(textCoords).flip();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, textVboId);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colourBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 0, 0);
			
			GL30.glBindVertexArray(0);
		}
		finally
		{
			if(verticesBuffer != null)
			{
				MemoryUtil.memFree(verticesBuffer);
			}
			if(indicesBuffer != null)
			{
				MemoryUtil.memFree(indicesBuffer);
			}
			if(colourBuffer != null)
			{
				MemoryUtil.memFree(colourBuffer);
			}
		}
	}
	
	public int getVaoId()
	{
		return this.vaoId;
	}
	
	public int getVertexCount()
	{
		return this.vertexCount;
	}
	
	public void cleanup()
	{
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		vbos.forEach((i) -> {
			GL15.glDeleteBuffers(i);
		});
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
		
	}
	
}
