package com.engine.rendering.objects;

import com.engine.Shader.ForwardNormalLight;
import com.engine.Shader.Shader;
import com.engine.components.Light;
import com.engine.components.Camera;
import com.engine.scenegraph.GameObject;
import com.math.Vector3D;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderingEngine
{
	private Camera mainCamera;
	private Vector3D ambientLight;

	//"More Permanent" Structure
	private ArrayList<Light> lights;
	private Light activeLight;

	public RenderingEngine()
	{
		lights = new ArrayList<Light>();
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		
		ambientLight = new Vector3D(0.1f, 0.1f, 0.1f);
}

	public Vector3D getNormalLight()
	{
		return ambientLight;
	}

	public void render(GameObject object)
	{
		clearScreen();

		lights.clear();
		object.addToRenderingEngine(this);

		Shader forwardAmbient = ForwardNormalLight.getShader();
		forwardAmbient.setRenderingEngine(this);

		object.render(forwardAmbient);

		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		for(Light light : lights)
		{
			light.getShader().setRenderingEngine(this);
			activeLight = light;
			object.render(light.getShader());
		}

		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}

	private static void clearScreen()
	{
		//TODO: Stencil Buffer
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
	}

	private static void setTextures(boolean enabled)
	{
		if(enabled)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
	}

	private static void unbindTextures()
	{
		glBindTexture(GL_TEXTURE_2D, 0);
	}

	private static void setClearColor(Vector3D color)
	{
		glClearColor(color.getX(), color.getY(), color.getZ(), 1.0f);
	}

	public static String getOpenGLVersion()
	{
		return glGetString(GL_VERSION);
	}

	public void addLight(Light light)
	{
		lights.add(light);
	}

	public void addCamera(Camera camera)
	{
		mainCamera = camera;
	}

	public Light getActiveLight()
	{
		return activeLight;
	}

	public Camera getMainCamera()
	{
		return mainCamera;
	}

	public void setMainCamera(Camera mainCamera)
	{
		this.mainCamera = mainCamera;
	}
}
