package com.wilby.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

public class Mesh 
{
	
	private int vaoId;
	
	private int vboId;
	
	private int idxVboId;
	
	private int colourVboId;
	
	private int vertexCount;
	
	public Mesh(float[] positions, int[] indices, float[] colours)
	{
		FloatBuffer verticesBuffer = null;
		FloatBuffer colourBuffer = null;
		IntBuffer indicesBuffer = null;
		
		vertexCount = indices.length;
		
		try
		{
			verticesBuffer = MemoryUtil.memAllocFloat(positions.length);
			
			
			
			vaoId = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vaoId);
			
			vboId = GL15.glGenBuffers();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
			verticesBuffer.put(positions).flip();
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
			idxVboId = GL15.glGenBuffers();
			indicesBuffer = MemoryUtil.memAllocInt(indices.length);
			indicesBuffer.put(indices).flip();
			GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, idxVboId);
			GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL15.GL_STATIC_DRAW);
			
			colourVboId = GL15.glGenBuffers();
			colourBuffer = MemoryUtil.memAllocFloat(colours.length);
			colourBuffer.put(colours).flip();
			GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, colourVboId);
			GL15.glBufferData(GL15.GL_ARRAY_BUFFER, colourBuffer, GL15.GL_STATIC_DRAW);
			GL20.glVertexAttribPointer(1, 3, GL11.GL_FLOAT, false, 0, 0);
			
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
		GL15.glDeleteBuffers(vboId);
		GL15.glDeleteBuffers(idxVboId);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
		
	}
	
}
