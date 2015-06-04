package com.engine.rendering.objects;

import com.math.Vector3D;

public class Attenuation extends Vector3D{

	public Attenuation(float contant, float linear, float exponent) {
		super(contant, linear, exponent);
	}

	public float getConstant(){
		return getX();
	}
	public float getLinear(){
		return getY();
	}
	public float getExponent(){
		return getZ();
	}
}
