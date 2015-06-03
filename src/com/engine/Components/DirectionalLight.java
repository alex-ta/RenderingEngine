package com.engine.components;
import com.engine.Shader.Shader;
import com.math.Vector3D;

public class DirectionalLight extends Light
{
	public DirectionalLight(Vector3D color, float intensity)
	{
		super(color, intensity);

		setShader(new Shader("forward-directional"));
	}

	public Vector3D getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}
}
