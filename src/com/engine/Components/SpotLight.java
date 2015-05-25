package com.engine.components;

import com.engine.Shader.ForwardSpotLight;
import com.engine.components.PointLight;
import com.math.Vector3D;

public class SpotLight extends PointLight
{
	private float cutoff;
	
	public SpotLight(Vector3D color, float intensity, Vector3D attenuation, float cutoff)
	{
		super(color, intensity, attenuation);
		this.cutoff = cutoff;

		setShader(ForwardSpotLight.getShader());
	}
	
	public Vector3D getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}

	public float getCutoff()
	{
		return cutoff;
	}
	public void setCutoff(float cutoff)
	{
		this.cutoff = cutoff;
	}
}
