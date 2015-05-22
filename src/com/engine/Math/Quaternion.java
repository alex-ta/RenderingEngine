package com.engine.Math;

public class Quaternion {
	public float x,y,z,w;
	private float length;
	private boolean normalized;
	
	public Quaternion(float x, float y, float z, float w){
		this.y = y;
		this.x = x;
		this.z = z;
		this.w = w;
		length =(float) Math.sqrt(x*x+y*y+z*z+w*w);
	}
	
	@Override
	public String toString(){
		return "x: "+x+" y: "+y+" z: "+z+" w: "+w+"normalized:"+normalized;
	}
	
	public float length(){
		if(normalized)
		return 1;
		return length;
	}
	
	public Quaternion normalize(){
	
		x/=length();
		y/=length();
		z/=length();
		w/=length();
		normalized = true;
		
		return this;
	}
	
	public Quaternion denormalize(){
		
		x*=length;
		y*=length;
		z*=length;
		w*=length;
		normalized = false;
		
		return this;
	}
	
	public Quaternion conjugate(){
		return new Quaternion(-x,-y,-z,w);
	}
	
	public Quaternion mul(Quaternion v){
		
		float w_ = w*v.w - x*v.x - y*v.y - z*v.z;
		float x_ = x*v.w + w*v.x + y*v.z - z*v.y;
		float y_ = y*v.w + w*v.y + z*v.x - x*v.z;
		float z_ = z*v.w + w*v.z + x*v.y - y*v.x;
		
		return new Quaternion(x_,y_,z_,w_);
	}
	public Quaternion mul (Vector3D v){
		float w_ = -x*v.x - y*v.y - z*v.z;
		float x_ =  w*v.x + y*v.z - z*v.y;
		float y_ =  w*v.y + z*v.x - x*v.z;
		float z_ =  w*v.z + x*v.y - y*v.x;
		
		return new Quaternion (x_,y_,z_,w_);
		
	}
}
