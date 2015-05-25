package com.engine.core;

import com.engine.scenegraph.GameObject;
import com.engine.components.GameComponent;
import com.engine.rendering.objects.*;

public abstract class Game
{
	private GameObject root;
	protected Material material;

	public Game(){
		material = new Material();
		root = new GameObject();
		
		material.addFloat("specularIntensity", 1f);
		material.addFloat("specularPower", 8f);
	}
	
	public void init() {}

	public void render(RenderingEngine engine){
		engine.render(root);
	}
	
	public void input(float delta){
		root.input(delta);
	}

	public void update(float delta)
	{
		root.update(delta);
	}
	
	public void addChild(GameObject child){
		root.addChild(child);
	}
	public void addComponent(GameComponent component){
		root.addComponent(component);
	}
	
	
}
