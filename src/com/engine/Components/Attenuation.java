package com.engine.Components;

public class Attenuation {

	private float constant;
	private float linear;
	private float exponent;
	
	public Attenuation(float constant,float linear, float exponent){
		this.constant = constant;
		this.linear = linear;
		this.exponent = exponent;
		
	}
	
	public float getConstant() {
		return constant;
	}
	public void setContant(float contant) {
		this.constant = contant;
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
	
	@Override
	public String toString(){
		return "constant:"+constant+" linear:"+linear+" exponent:"+exponent;
	}

}

