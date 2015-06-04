package com.engine.scenegraph;

import com.engine.Shader.Shader;
import com.engine.core.CoreEngine;
import com.engine.rendering.objects.RenderingEngine;
import com.math.Transform;

import java.util.ArrayList;

public class GameObject
{
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	private CoreEngine engine;

	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		engine = null;
	}

	public void addChild(GameObject child)
	{		
		children.add(child);
		child.getTransform().setParent(transform);
		child.setEngine(engine);
	}

	public GameObject addComponent(GameComponent component)
	{
		components.add(component);
		component.setParent(this);

		return this;
	}
	
	public void input(float delta){
		transform.update();

		for(GameComponent component : components)
			component.input(delta);

	}
	public void update(float delta){

		for(GameComponent component : components)
			component.update(delta);

	}
	public void render(Shader shader, RenderingEngine engine){
		for(GameComponent component : components)
			component.render(shader,engine);
	}

	public void inputAll(float delta)
	{
		this.input(delta);
		for(GameObject child : children)
			child.inputAll(delta);
	}

	public void updateAll(float delta)
	{
		this.update(delta);
		for(GameObject child : children)
			child.updateAll(delta);
	}

	public void renderAll(Shader shader, RenderingEngine engine)
	{
		this.render(shader, engine);
		for(GameObject child : children)
			child.renderAll(shader,engine);
	}

	public Transform getTransform()
	{
		return transform;
	}

	public CoreEngine getEngine() {
		return engine;
	}

	public ArrayList<GameObject> getAllAttached(){
		ArrayList<GameObject> result = new ArrayList<GameObject>();
		for(GameObject child: children){
			result.addAll(child.getAllAttached());
		}
		result.add(this);
		return result;
	}
	
	public void setEngine(CoreEngine engine) {
		if(engine == null){
			return;}
		this.engine = engine;
		for(GameComponent component : components)
			component.addToEngine(engine);

		for(GameObject child : children)
			child.setEngine(engine);	
	}
}
