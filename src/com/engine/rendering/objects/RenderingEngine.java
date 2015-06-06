package com.engine.rendering.objects;

import com.engine.Shader.Shader;
import com.engine.components.Light;
import com.engine.components.Camera;
import com.engine.rendering.ressource.MappedValues;
import com.engine.scenegraph.GameObject;
import com.math.Transform;
import com.math.Vector3D;

import java.util.ArrayList;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;

public class RenderingEngine extends MappedValues
{	
	private HashMap<String,Integer> samplerMap;
	private ArrayList<Light> lights;
	private Light activeLight;
	
	private Shader normalLight;
	private Camera mainCamera;
	
	public void UpdateUniformStruct(Transform transform, Material material, Shader shader, String uniformName, String uniformType)
	{
		throw new IllegalArgumentException(uniformType + " is not a supported type in RenderingEngine");
	}
	
	public RenderingEngine()
	{
		super();
		lights = new ArrayList<Light>();
		samplerMap = new HashMap<String,Integer>();
		samplerMap.put("diffuse", 0);
		samplerMap.put("normalMap", 1);
		samplerMap.put("dispMap", 2);
		addVector3D("normlight",new Vector3D(0.1f,0.1f,0.1f));
		normalLight = new Shader("forward-normlight");
		
		
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		glFrontFace(GL_CW);
		glCullFace(GL_BACK);
		glEnable(GL_CULL_FACE);
		glEnable(GL_DEPTH_TEST);
		glEnable(GL_TEXTURE_2D);
		
}

	public void render(GameObject object)
	{
		clearScreen();

		object.renderAll(normalLight,this);

		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE, GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);

		for(Light light : lights)
		{
			activeLight = light;
			object.renderAll(light.getShader(),this);
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

	public int getSamplerSlot(String name) {
		return samplerMap.get(name);
	}
}
