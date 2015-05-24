package com.engine.Scenegraph;

import com.engine.Core.RenderingEngine;
import com.engine.Input.InputHandler;
import com.engine.Math.Transform;
import com.engine.Shaders.Shader;

public abstract class GameComponent {
	
	protected GameObject parent;
	public abstract void input(InputHandler input);
	public abstract void update();
	public abstract void render(Shader shader);
	public abstract void addToREngine(RenderingEngine engine);
	public void setParent(GameObject parent){
		this.parent = parent;
	}
	public GameObject getParent(){
		return parent;
	}
	
}
