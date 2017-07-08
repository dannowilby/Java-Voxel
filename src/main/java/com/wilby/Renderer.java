package com.wilby;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import com.wilby.display.Display;
import com.wilby.display.Window;
import com.wilby.model.Mesh;
import com.wilby.model.ShaderProgram;
import com.wilby.util.ResourceLocation;

public class Renderer 
{
	
	private static ShaderProgram shaderProgram;
	private static String vertexShaderLocation;
	private static String fragmentShaderLocation;
	
	Mesh mesh;
	
	float[] positions = new float[]{
	        -0.5f,  0.5f, 0.0f,
	        -0.5f, -0.5f, 0.0f,
	         0.5f,  0.5f, 0.0f,
	         0.5f,  0.5f, 0.0f,
	        -0.5f, -0.5f, 0.0f,
	         0.5f, -0.5f, 0.0f,
	    };
	
	int[] indices = new int[]{
	        0, 1, 3, 3, 1, 2,
	    };
	
	float[] colours = new float[]{
		    0.5f, 0.0f, 0.0f,
		    0.0f, 0.5f, 0.0f,
		    0.0f, 0.0f, 0.5f,
		    0.0f, 0.5f, 0.5f,
		};
	
	public void render(Window window)
	{
		
		if(Display.isResized())
		{
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
			Display.setResized(false);
		}
		
		shaderProgram.bind();
		
		GL30.glBindVertexArray(mesh.getVaoId());
		GL20.glEnableVertexAttribArray(0);
		GL20.glEnableVertexAttribArray(1);
		
		GL11.glDrawElements(GL11.GL_TRIANGLES, mesh.getVertexCount(), GL11.GL_UNSIGNED_INT, 0);
		
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
		mesh = new Mesh(positions, indices, colours);
		vertexShaderLocation = new ResourceLocation().loadShader("/vertex.vs");
		fragmentShaderLocation = new ResourceLocation().loadShader("/fragment.fs");
		
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(vertexShaderLocation);
		shaderProgram.createFragmentShader(fragmentShaderLocation);
		shaderProgram.link();
		
	}
	
	public void cleanup()
	{
		if(shaderProgram != null)
		{
			shaderProgram.cleanup();
		}
		
	}
}
