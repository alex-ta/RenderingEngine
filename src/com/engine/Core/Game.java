package com.engine.Core;

import com.engine.Components.Camera;
import com.engine.Input.InputHandler;
import com.engine.Input.MouseW;
import com.engine.Scenegraph.GameObject;
import com.engine.Shaders.Shader;

public abstract class Game{
	private InputHandler input;
	private Camera camera; 
	private final float rotspeed = 0.9f;
	private final float movespeed = 0.006f;
	private GameObject root;	
	
	public Game(GameObject obj){
		input = new InputHandler();
		this.root = obj;
	}
		
	public Game(){
		this(new GameObject());
	}
	
	public void input(){
		input.update();
		
		if(input.isMoveleft()){
			camera.move(camera.getLeft(),movespeed);
		}
		if(input.isMoveright()){
			camera.move(camera.getRight(),movespeed);
		}
		if(input.isMoveforward()){
			camera.move(camera.getForward(),movespeed);
		}
		if(input.isMovebackwards()){
			camera.move(camera.getForward(),-movespeed);
		}
		
		camera.rotateY(MouseW.getDeltaMovement().y*rotspeed);
		camera.rotateX(MouseW.getDeltaMovement().x*rotspeed);
	}
	protected Camera getCamera() {
		return camera;
	}
	protected void setCamera(Camera camera) {
		this.camera = camera;
	}
	public void update(){
		root.update();
	}
	public void render(Shader shader){
		root.render(shader);
	}
	public void addChild(GameObject child){
		root.addChild(child);
	}
	public void addToREngine(RenderingEngine engine){
		root.addToREngine(engine);
	};
	
}
