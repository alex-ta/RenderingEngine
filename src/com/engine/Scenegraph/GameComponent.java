package com.engine.Scenegraph;

import com.engine.Core.RenderingEngine;
import com.engine.Math.Transform;
import com.engine.Shaders.Shader;

public interface GameComponent {
	
	public void update(Transform transform);
	public void render(Transform transform,Shader shader);
	public void addToREngine(RenderingEngine engine);
}
