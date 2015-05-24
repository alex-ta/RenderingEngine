package com.engine.Core;

import static org.lwjgl.opengl.GL11.*;

import java.util.LinkedList;

import com.engine.Components.Camera;
import com.engine.Components.Light;
import com.engine.Components.NormalLight;
import com.engine.Desing.Material;
import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardNormalLight;
import com.engine.Shaders.Shader;

public class RenderingEngine {
	
	private Camera camera;
	private Light activeLight;
	private Light normlight;
	
	// permanent Structures
	private LinkedList<Light> lights;
	
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
				lights = new LinkedList<Light>();
				normlight = NormalLight.getInstance(this);
	}
				
	
	public void render(Game game){
		clearLights();
		game.addToREngine(this);
		
		game.render(normlight.getShader());
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_ONE,GL_ONE);
		glDepthMask(false);
		glDepthFunc(GL_EQUAL);
		
		for(Light l: lights){
			this.activeLight = l;
			game.render(l.getShader());
		}
		
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
		return camera;
	}

	public void setMainCamera(Camera camera) {
		this.camera = camera;
	}

	public Light getLight() {
		return activeLight;
	}
	
	public void addLight(Light light) {
		this.lights.add(light);
	}
	
	public void addCamera(Camera camera) {
	this.camera = camera;
	}
	
	private void clearLights(){
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		lights.clear();
	}
	
	public Light getNormalLight() {
		return normlight;
	}



	
}
