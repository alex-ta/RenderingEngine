package com.engine.Components;
import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardPointLight;

public class PointLight extends Light {
	
	private static final int COLORDEPTH = 256;
	private Vector3D attenuation;
	private float range;
	
	public PointLight(Vector3D color,float intensity,Vector3D attenuation){
	super(color,intensity);
	this.attenuation = attenuation;
	
	float a = attenuation.z;
	float b = attenuation.y;
	float c = attenuation.x - COLORDEPTH * intensity * color.max();
	
	this.range = (float)(-b + Math.sqrt(b*b-4*a*c))/(2*a);
	this.setShader(ForwardPointLight.getShader()); // TODO easy range
	}
	public float getRange() {
		return range;
	}
	public void setRange(float range) {
		this.range = range;
	}

	public float getConstant() {
		return attenuation.x;
	}

	public void setConstant(float constant) {
		this.attenuation.x = constant;
	}

	public float getLinear() {
		return attenuation.y;
	}

	public void setLinear(float linear) {
		this.attenuation.y = linear;
	}

	public float getExponent() {
		return attenuation.z;
	}

	public void setExponent(float exponent) {
		this.attenuation.z = exponent;
	}
}
