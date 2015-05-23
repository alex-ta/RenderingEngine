package com.engine.Scenegraph;

import java.util.ArrayList;

import com.engine.Core.RenderingEngine;
import com.engine.Math.Transform;
import com.engine.Shaders.Shader;

public class GameObject {
	private ArrayList<GameObject> children;
	private ArrayList<GameComponent> components;
	private Transform transform;
	
	
	public GameObject(){
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
	}
	
	public Transform getTransform(){
		return transform;
	}
	
	public void addChild(GameObject child){
		children.add(child);
	}
	
	public void addComponent(GameComponent component){
		components.add(component);
	}
	
	public void update(){
		
		for(GameComponent component: components){
			component.update(transform);
		}
		
		for(GameObject child:children){
			child.update();
		}
	}
	public void render(Shader shader){
		for(GameComponent component: components){
			component.render(transform,shader);
		}
		for(GameObject child:children)
			child.render(shader);
	}

	public void addToREngine(RenderingEngine engine) {
		for(GameComponent component: components){
			component.addToREngine(engine);
		}
		for(GameObject child:children)
			child.addToREngine(engine);
	}
}
