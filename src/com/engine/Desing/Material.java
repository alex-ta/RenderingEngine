package com.engine.Desing;

import com.engine.Math.Vector3D;

public class Material {

	private Texture texture;
	private Vector3D color;
	private float specularIntensity;
	private float specularPower;
	
	public Material(Texture texture, Vector3D color){
		this(texture,color,2,32);
	}
	
	public Material(Texture texture){
		this(texture, new Vector3D(1,1,1));
	}
	
	public Material(Texture texture, Vector3D color, float specularIntensity,float specularPower){
		this.texture = texture;
		this.color = color;
		this.specularPower = specularPower;
		this.specularIntensity = specularIntensity;	
	}
	
	public Texture getTexture() {
		return texture;
	}

	public void setTexture(Texture texture) {
		this.texture = texture;
	}

	public Vector3D getColor() {
		return color;
	}

	public void setColor(Vector3D color) {
		this.color = color;
	}

	public float getSpecularIntensity() {
		return specularIntensity;
	}

	public void setSpecularIntensity(float specularIntensity) {
		this.specularIntensity = specularIntensity;
	}

	public float getSpecularPower() {
		return specularPower;
	}

	public void setSpecularPower(float specularPower) {
		this.specularPower = specularPower;
	}
}
