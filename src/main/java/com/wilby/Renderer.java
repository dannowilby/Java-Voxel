package com.wilby;

import java.util.ArrayList;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.wilby.display.Camera;
import com.wilby.display.Display;
import com.wilby.display.Transformation;
import com.wilby.display.Window;
import com.wilby.model.Block;
import com.wilby.model.GameItem;
import com.wilby.model.ShaderProgram;
import com.wilby.util.ResourceLocation;

public class Renderer 
{
	
	private static float FOV = (float) Math.toRadians(60.0f);
	
	private static float zNear = .01f;
	private static float zFar = 1000.0f;
	
	private Matrix4f projectionMatrix;
	
	private static ShaderProgram shaderProgram;
	private static String vertexShaderLocation;
	private static String fragmentShaderLocation;
	
	private Transformation tranformation;
	private Camera camera;
	private static Vector3f cameraInc = new Vector3f();
	
	ArrayList<Block> renderList = new ArrayList<Block>();
	
	public void render(Window window)
	{
		
		if(Display.isResized())
		{
			GL11.glViewport(0, 0, Display.getWidth(), Display.getHeight());
			Display.setResized(false);
		}
		
		shaderProgram.bind();
		
		Matrix4f projectionMatrix = tranformation.getProjectionMatrix(FOV, Display.getWidth(), Display.getHeight(), zNear, zFar);
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);
		
		Matrix4f viewMatrix = tranformation.getViewMatrix(camera);
		
		shaderProgram.setUniform("texture_sampler", 0);
		
		for(int i = 0; i < renderList.size(); i++)
		{
			Block b = renderList.get(i);
			Matrix4f worldMatrix = tranformation.getModelViewMatrix(b.getGameItem(), viewMatrix);
			shaderProgram.setUniform("worldMatrix", worldMatrix);
			b.getGameItem().getMesh().render();
		}
		
		shaderProgram.unbind();
		
		GLFW.glfwSwapBuffers(window.getWindow());
		GLFW.glfwPollEvents();
	}

	public void clear(Window window)
	{
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}

	public void initialise(Window window) throws Exception
	{
		
		float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
		projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, zNear, zFar);
		
		vertexShaderLocation = new ResourceLocation().loadShader("/vertex.vs");
		fragmentShaderLocation = new ResourceLocation().loadShader("/fragment.fs");
		
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(vertexShaderLocation);
		shaderProgram.createFragmentShader(fragmentShaderLocation);
		shaderProgram.link();
		
		tranformation = new Transformation();
		
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("worldMatrix");
		shaderProgram.createUniform("texture_sampler");
		
		camera = new Camera();
		
		renderList.add(new Block("grassblock"));
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public static Vector3f getCamera()
	{
		return cameraInc;
	}
	
	public Camera cam()
	{
		return camera;
	}
	
	public void cleanup()
	{
		if(shaderProgram != null)
		{
			shaderProgram.cleanup();
		}
		
	}
}
