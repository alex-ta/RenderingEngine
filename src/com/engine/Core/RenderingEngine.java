package com.engine.Core;

import static org.lwjgl.opengl.GL11.*;

import java.util.LinkedList;

import com.engine.Components.Camera;
import com.engine.Components.DirectionalLight;
import com.engine.Components.Light;
import com.engine.Components.NormalLight;
import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardDirectionalLight;
import com.engine.Shaders.ForwardNormalLight;
import com.engine.Shaders.Shader;

public class RenderingEngine {
	
	private Camera mainCamera;
	private Light activeLight;
	
	// permanent Structures
	private LinkedList<Light> lights;
	// Added !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	private Light lit = new NormalLight(0.0f,0.0f,0.0f,0.0f);
	private DirectionalLight directional = new DirectionalLight(new Vector3D(0,0,1), 0.4f, new Vector3D(1,1,1));
	//	public DirectionalLight directional2 = new DirectionalLight8new Vector3D(1,0,0, 0.4f, new Vector3D(-1,1,-1));

	
	public RenderingEngine(){
				// clearcolor setzten (schwarz)
				glClearColor(0.0f,0.0f,0.0f,0.0f);
				// -> welches das frontface is
				glFrontFace(GL_CW);
				// sachen die wegzeigen nicht zeichnen
				glCullFace(GL_BACK);
				glEnable(GL_CULL_FACE);
				glEnable(GL_DEPTH_TEST);
				//glEnable(GL_DEPTH_CLAMP);
				glEnable(GL_TEXTURE_2D);
				System.out.println(getOGLVersion());
				mainCamera = new Camera((float)Math.toRadians(70f), (float)Window.getWidth()/(float)Window.getHeight(), 0.1f, 1000);
				lights = new LinkedList<Light>();
	}
				
	
	public void render(Game game){
		clearLights();
		game.addToREngine(this);
		// Added !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//Shader s =ForwardNormalLight.getShader();
		Shader s = ForwardDirectionalLight.getShader();
		// Added !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		s.setRenderingEngine(this);
		// Added !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		this.activeLight = lit;
		game.render(lit.getShader());
		
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE,GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		// Hier funktioniert das rendern noch nicht !!!
	
		this.activeLight = directional;
		game.render(directional.getShader());
		//game.render(lit.getShader());
		
		glDepthFunc(GL_LESS);
		glDepthMask(true);
		glDisable(GL_BLEND);
	}
	
	private static String getOGLVersion(){
		return glGetString(GL_VERSION);
	}
	
	private static void setTextures(boolean value){
		if(value)
			glEnable(GL_TEXTURE_2D);
		else
			glDisable(GL_TEXTURE_2D);
		
	}
	
	private static void setClearColor(Vector3D color){
		glClearColor(color.x,color.y,color.z,1.0f);
	}
	
	private static void unbindTextures(){
		glBindTexture(GL_TEXTURE_2D,0);
	}

	public Camera getMainCamera() {
		return mainCamera;
	}
	

	public void setMainCamera(Camera mainCamera) {
		this.mainCamera = mainCamera;
	}

	public Light getLight() {
		return activeLight;
	}
	
	public void addLight(Light light) {
		this.lights.add(light);
	}
	
	private void clearLights(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		lights.clear();
	}
}
