package com.engine.Components;

import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardDirectionalLight;


public class DirectionalLight extends Light{
	private Vector3D direction;
	
	public DirectionalLight(Vector3D color,float intensity,Vector3D direction){
		super(color,intensity);
		this.direction = direction.normalize();
		this.setShader(ForwardDirectionalLight.getShader());
	}
	public DirectionalLight(Vector3D color,float intensity,float x, float y, float z){
		this(color,intensity, new Vector3D(x,y,z));
	}
	
	public Vector3D getDirection() {
		return direction;
	}
	public void setDirection(Vector3D direction) {
		this.direction = direction.normalize();
	}
}
