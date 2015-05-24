package com.engine.Scenegraph;

import java.util.ArrayList;

import com.engine.Core.RenderingEngine;
import com.engine.Input.InputHandler;
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
	
	public GameObject addChild(GameObject child){
		children.add(child);
		return this;
	}
	
	public GameObject addCompontent(GameComponent component){
		components.add(component);
		component.setParent(this);
		return this;
	}
	
	public void update(){
		
		for(GameComponent component: components){
			component.update();
		}
		
		for(GameObject child:children){
			child.update();
		}
	}
	public void render(Shader shader){
		for(GameComponent component: components){
			component.render(shader);
		}
		for(GameObject child:children){
			child.render(shader);
		}
	}
	
	public void input(InputHandler input){
		for(GameComponent component: components){
			component.input(input);
		}
		for(GameObject child:children){
			child.input(input);
		}
	}

	public void addToREngine(RenderingEngine engine) {
		for(GameComponent component: components){
			component.addToREngine(engine);
		}
		for(GameObject child:children)
			child.addToREngine(engine);
	}
}
