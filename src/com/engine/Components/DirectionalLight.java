package com.engine.components;

import com.engine.Shader.ForwardDirectionalLight;
import com.math.Vector3D;

public class DirectionalLight extends Light
{
	public DirectionalLight(Vector3D color, float intensity)
	{
		super(color, intensity);

		setShader(ForwardDirectionalLight.getShader());
	}

	public Vector3D getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}
}
