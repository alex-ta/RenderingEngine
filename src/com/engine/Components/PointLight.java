package com.engine.components;
import com.engine.Shader.Shader;
import com.engine.rendering.objects.Attenuation;
import com.math.Vector3D;

public class PointLight extends Light
{
	private static final int COLOR_DEPTH = 256;

	private Attenuation attenuation;
	private float range;
	
	public PointLight(Vector3D color, float intensity, Attenuation attenuation)
	{
		super(color, intensity);
		this.attenuation = attenuation;

		float exponent = attenuation.getExponent();
		float linear = attenuation.getLinear();
		float constant = attenuation.getConstant() - COLOR_DEPTH * getIntensity() * getColor().max();

		this.range = (float)((-linear + Math.sqrt(linear * linear - 4 * exponent * constant))/(2 * exponent));

		setShader(new Shader("forward-point"));
	}

	public float getRange()
	{
		return range;
	}

	public void setRange(float range)
	{
		this.range = range;
	}

	public Attenuation getAttenuation(){
		return attenuation;
	}
}
