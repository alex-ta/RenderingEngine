package com.engine.scenegraph;

import com.engine.Shader.Shader;
import com.engine.core.CoreEngine;
import com.engine.rendering.objects.RenderingEngine;
import com.math.Transform;

public abstract class GameComponent
{
	private GameObject parent;

	public abstract void input(float delta);
	public abstract void update(float delta);
	public abstract void render(Shader shader, RenderingEngine engine);

	public void setParent(GameObject parent)
	{
		this.parent = parent;
	}

	public Transform getTransform()
	{
		return parent.getTransform();
	}

	public void addToEngine(CoreEngine engine) {}
}

