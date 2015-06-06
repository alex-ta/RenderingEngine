package com.engine.scenegraph;

import com.engine.core.CoreEngine;
import com.engine.rendering.objects.*;

public abstract class Game
{
	private GameObject m_root;

	public void init() {}

	public void input(float delta)
	{
		getRootObject().inputAll(delta);
	}

	public void update(float delta)
	{
		getRootObject().updateAll(delta);
	}

	public void render(RenderingEngine renderingEngine)
	{
		renderingEngine.render(getRootObject());
	}

	public void addChild(GameObject object)
	{
		getRootObject().addChild(object);
	}

	private GameObject getRootObject()
	{
		if(m_root == null)
			m_root = new GameObject();

		return m_root;
	}

	public void setEngine(CoreEngine engine) { getRootObject().setEngine(engine); }
}
