package com.wilby;

import org.joml.Matrix4f;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

import com.wilby.display.Camera;
import com.wilby.display.Display;
import com.wilby.display.Transformation;
import com.wilby.display.Window;
import com.wilby.model.GameItem;
import com.wilby.model.Mesh;
import com.wilby.model.ShaderProgram;
import com.wilby.model.Texture;
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
	
	Mesh mesh;
	
	float[] positions = new float[] {
            // V0
            -0.5f, 0.5f, 0.5f,
            // V1
            -0.5f, -0.5f, 0.5f,
            // V2
            0.5f, -0.5f, 0.5f,
            // V3
            0.5f, 0.5f, 0.5f,
            // V4
            -0.5f, 0.5f, -0.5f,
            // V5
            0.5f, 0.5f, -0.5f,
            // V6
            -0.5f, -0.5f, -0.5f,
            // V7
            0.5f, -0.5f, -0.5f,
            
            // For text coords in top face
            // V8: V4 repeated
            -0.5f, 0.5f, -0.5f,
            // V9: V5 repeated
            0.5f, 0.5f, -0.5f,
            // V10: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V11: V3 repeated
            0.5f, 0.5f, 0.5f,

            // For text coords in right face
            // V12: V3 repeated
            0.5f, 0.5f, 0.5f,
            // V13: V2 repeated
            0.5f, -0.5f, 0.5f,

            // For text coords in left face
            // V14: V0 repeated
            -0.5f, 0.5f, 0.5f,
            // V15: V1 repeated
            -0.5f, -0.5f, 0.5f,

            // For text coords in bottom face
            // V16: V6 repeated
            -0.5f, -0.5f, -0.5f,
            // V17: V7 repeated
            0.5f, -0.5f, -0.5f,
            // V18: V1 repeated
            -0.5f, -0.5f, 0.5f,
            // V19: V2 repeated
            0.5f, -0.5f, 0.5f,
        };
        float[] textureCoords = new float[]{
            0.0f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.5f, 0.0f,
            
            0.0f, 0.0f,
            0.5f, 0.0f,
            0.0f, 0.5f,
            0.5f, 0.5f,
            
            // For text coords in top face
            0.0f, 0.5f,
            0.5f, 0.5f,
            0.0f, 1.0f,
            0.5f, 1.0f,

            // For text coords in right face
            0.0f, 0.0f,
            0.0f, 0.5f,

            // For text coords in left face
            0.5f, 0.0f,
            0.5f, 0.5f,

            // For text coords in bottom face
            0.5f, 0.0f,
            1.0f, 0.0f,
            0.5f, 0.5f,
            1.0f, 0.5f,
        };
        int[] indices = new int[]{
            // Front face
            0, 1, 3, 3, 1, 2,
            // Top Face
            8, 10, 11, 9, 8, 11,
            // Right face
            12, 13, 7, 5, 12, 7,
            // Left face
            14, 15, 6, 4, 14, 6,
            // Bottom face
            16, 18, 19, 17, 16, 19,
            // Back face
            4, 6, 7, 5, 4, 7,};
	    
	    private Texture texture;
	
	GameItem[] gameItems = new GameItem[1];
	
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
		shaderProgram.setUniform("texture_sampler", 0);
		
		float rotation = gameItems[0].getRotation().x + 1.5f;
		
		if(rotation > 360)
		{
			rotation = 0;
		}
		
		gameItems[0].setRotation(rotation, rotation, rotation);
		
		Matrix4f viewMatrix = tranformation.getViewMatrix(camera);
		
		for(GameItem gameItem : gameItems)
		{
			Matrix4f worldMatrix = tranformation.getModelViewMatrix(gameItem, viewMatrix);
			shaderProgram.setUniform("worldMatrix", worldMatrix);
			gameItem.getMesh().render();
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
		
		texture = new Texture("grassblock.png");
		
		camera = new Camera();
		
		mesh = new Mesh(positions, indices, textureCoords, texture);
		GameItem gameItem = new GameItem(mesh);
		
		gameItem.setPosition(0, 0, -5f);
		
		gameItems[0] = gameItem;
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public void cleanup()
	{
		if(shaderProgram != null)
		{
			shaderProgram.cleanup();
		}
		
	}
}
