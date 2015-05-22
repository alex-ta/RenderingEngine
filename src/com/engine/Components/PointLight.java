package com.engine.Components;
import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardPointLight;

public class PointLight extends Light {
	private Attenuation atten;
	private Vector3D position;
	private float range;
	
	public PointLight(Vector3D color,float intensity,Attenuation atten,Vector3D position,float range){
	super(color,intensity);
	this.atten = atten;
	this.position = position;
	this.range = range;
	this.setShader(ForwardPointLight.getShader());
	}

	public Attenuation getAttenuation() {
		return atten;
	}
	public void setAttenuation(Attenuation atten) {
		this.atten = atten;
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
}
