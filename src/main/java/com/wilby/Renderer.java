package com.wilby;

import java.io.File;
import java.nio.FloatBuffer;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import com.wilby.display.Display;
import com.wilby.display.Window;
import com.wilby.model.ShaderProgram;
import com.wilby.util.ResourceLocation;

public class Renderer 
{
	
	private static ShaderProgram shaderProgram;
	private static String vertexShaderLocation = new ResourceLocation().shader("vertex.vs").getPath();
	private static String fragmentShaderLocation = new ResourceLocation().shader("fragment.fs").getPath();
	
	private static int vaoId;
	private static int vboId;
	
	public void render(Window window)
	{
		
		if(Display.isResized())
		{
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
			Display.setResized(false);
		}
		
		shaderProgram.bind();
		
		GL30.glBindVertexArray(vaoId);
		GL20.glEnableVertexAttribArray(0);
		
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);
		
		GL20.glDisableVertexAttribArray(0);
		GL30.glBindVertexArray(0);
		
		shaderProgram.unbind();
		
		GLFW.glfwSwapBuffers(window.getWindow());
		GLFW.glfwPollEvents();
	}

	public void clear(Window window)
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void initialise() throws Exception
	{
		
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(vertexShaderLocation);
		shaderProgram.createFragmentShader(fragmentShaderLocation);
		shaderProgram.link();
		

		float[] vertices = new float[]{
			     0.0f,  0.5f, 0.0f,
			    -0.5f, -0.5f, 0.0f,
			     0.5f, -0.5f, 0.0f
			};
		
		FloatBuffer verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
		verticesBuffer.put(vertices).flip();
		
		vaoId = GL30.glGenVertexArrays();
		
		GL30.glBindVertexArray(vaoId);
		
		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, verticesBuffer, GL15.GL_STATIC_DRAW);
		MemoryUtil.memFree(verticesBuffer);
		
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 0, 0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		
		GL30.glBindVertexArray(0);
		
		if(verticesBuffer != null)
		{
			MemoryUtil.memFree(verticesBuffer);
		}
		
	}
	
	public void cleanup()
	{
		if(shaderProgram != null)
		{
			shaderProgram.cleanup();
		}
		
		GL20.glDisableVertexAttribArray(0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		GL15.glDeleteBuffers(vboId);
		
		GL30.glBindVertexArray(0);
		GL30.glDeleteVertexArrays(vaoId);
	}
}
