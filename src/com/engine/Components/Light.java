package com.engine.components;

import com.engine.Shader.Shader;
import com.engine.core.CoreEngine;
import com.engine.rendering.objects.RenderingEngine;
import com.engine.scenegraph.GameComponent;
import com.math.Vector3D;

public class Light extends GameComponent
{
	private Vector3D color;
	private float intensity;
	private Shader shader;
	
	public Light(Vector3D color, float intensity)
	{
		this.color = color;
		this.intensity = intensity;
	}

	@Override
	public void addToEngine(CoreEngine engine)
	{
		engine.getRenderingEngine().addLight(this);
	}

	public void setShader(Shader shader)
	{
		this.shader = shader;
	}

	public Shader getShader()
	{
		return shader;
	}

	public Vector3D getColor()
	{
		return color;
	}

	public void setColor(Vector3D color)
	{
		this.color = color;
	}

	public float getIntensity()
	{
		return intensity;
	}

	public void setIntensity(float intensity)
	{
		this.intensity = intensity;
	}

	@Override
	public void input(float delta) {
	}
	@Override
	public void update(float delta) {
	}
	@Override
	public void render(Shader shader, RenderingEngine engine) {
	}
}
