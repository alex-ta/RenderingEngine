package com.engine.Math;

public class Vector3D {
	
	public float x,y,z;
	private float length;
	private boolean normalized;
	
	public Vector3D(float x, float y, float z){
		set(x,y,z);
	}
	public Vector3D(){
		set(0,0,0);
	}
	
	@Override
	public String toString(){
		return "x: "+x+" y: "+y+" z: "+z+"normalized:"+normalized;
	}
	
	public void set(float x, float y, float z){
		this.x = x;
		this.y = y;
		this.z = z;
		this.length = (float)Math.sqrt(x*x+y*y+z*z);
		this.normalized = false;
	}
	
	public float length(){
		if(normalized)
		return 1;
		return length;
	}
	
	public float dot(Vector3D v){
		return x*v.x+y*v.y+z*v.z;
	}
	
	public Vector3D normalize(){
	
		x/=length();
		y/=length();
		z/=length();
		normalized = true;
		
		return this;
	}
	
	public Vector3D denormalize(){
		
		x*=length;
		y*=length;
		z*=length;
		normalized = false;
		
		return this;
	}
	
	public Vector3D cross(Vector3D v){
		float x_ = y*v.z - z*v.y;
		float y_ = z*v.x - x*v.z;
		float z_ = x*v.y - y*v.x;
		
		return new Vector3D(x_,y_,z_);
	}

	public boolean equals(Vector3D v){
		return x==v.x && y == v.y && z == v.z;
	}
	
	public Vector3D rotate(Quaternion rotation){
		
		Quaternion conjugate = rotation.conjugate();
		Quaternion w = rotation.mul(this).mul(conjugate);
		x=w.x;
		y=w.y;
		z=w.z;
		
		return this;
	}
	
	public Vector3D rotate(Vector3D axe, float angle){	
		Quaternion rotate = new Quaternion().initRotation(angle, axe);
		return this.rotate(rotate);
	}
	
	public Vector2D getXY(){return new Vector2D(x,y);}
	public Vector2D getYX(){return new Vector2D(y,x);}
	public Vector2D getYZ(){return new Vector2D(y,z);}
	public Vector2D getZY(){return new Vector2D(z,y);}
	public Vector2D getZX(){return new Vector2D(z,x);}
	public Vector2D getXZ(){return new Vector2D(x,z);}
	
	public Vector3D lerp(Vector3D dest,float lerp){
		return dest.sub(this).mul(lerp).add(this);
	}
	
	public Vector3D add(Vector3D v){
		return new Vector3D(x+v.x,y+v.y,z+v.z);
	}
	public Vector3D add(float v){
		return new Vector3D(x+v,y+v,z+v);
	}
	public Vector3D sub(Vector3D v){
		return new Vector3D(x-v.x,y-v.y,z-v.z);
	}
	public Vector3D sub(float v){
		return new Vector3D(x-v,y-v,z-v);
	}
	public Vector3D div(Vector3D v){
		return new Vector3D(x/v.x,y/v.y,z/v.z);
	}
	public Vector3D div(float v){
		return new Vector3D(x/v,y/v,z/v);
	}
	public Vector3D mul(Vector3D v){
		return new Vector3D(x*v.x,y*v.y,z*v.z);
	}
	public Vector3D mul(float v){
		return new Vector3D(x*v,y*v,z*v);
	}
	public Vector3D abs()
	{
		return new Vector3D(Math.abs(x), Math.abs(y), Math.abs(z));
	}
	public float max(){
		return Math.max(x, Math.max(y, z));
	}
	
}
