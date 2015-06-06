package com.engine.scenegraph;

import com.engine.core.CoreEngine;
import com.engine.rendering.objects.*;

public abstract class Game
{
	private GameObject root;
	protected Material material;

	public Game(){
		material = new Material(new Texture("bricks.jpg"), new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f);
		root = new GameObject();
	}
	
	public void init() {}

	public void render(RenderingEngine engine){
		engine.render(root);
	}
	
	public void input(float delta){
		root.inputAll(delta);
	}

	public void update(float delta)
	{
		root.updateAll(delta);
	}
	
	public void addChild(GameObject child){
		root.addChild(child);
	}
	public void addComponent(GameComponent component){
		root.addComponent(component);
	}
	public void setEngine(CoreEngine engine){
		root.setEngine(engine);
	}
}