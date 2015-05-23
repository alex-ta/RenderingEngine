package com.engine.Components;
import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardPointLight;

public class PointLight extends Light {
	private float constant;
	private float linear;
	private float exponent;
	private Vector3D position;
	private float range;
	
	public PointLight(Vector3D color,float intensity,float constant,float linear, float exponent,Vector3D position,float range){
	super(color,intensity);
	this.constant = constant;
	this.linear = linear;
	this.exponent = exponent;
	this.position = position;
	this.range = range;
	this.setShader(ForwardPointLight.getShader());
	}

	public Vector3D getPosition() {
		return position;
	}
	public void setPosition(Vector3D position) {
		this.position = position;
	}
	public float getRange() {
		return range;
	}
	public void setRange(float range) {
		this.range = range;
	}

	public float getConstant() {
		return constant;
	}

	public void setConstant(float constant) {
		this.constant = constant;
	}

	public float getLinear() {
		return linear;
	}

	public void setLinear(float linear) {
		this.linear = linear;
	}

	public float getExponent() {
		return exponent;
	}

	public void setExponent(float exponent) {
		this.exponent = exponent;
	}
}
