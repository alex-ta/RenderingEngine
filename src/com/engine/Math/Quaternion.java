package com.engine.Math;

public class Quaternion {
	public float x,y,z,w;
	private float length;
	private boolean normalized;
	
	public Quaternion(){
		this(0,0,0,1);
	}
	
	public Quaternion(float x, float y, float z, float w){
		this.y = y;
		this.x = x;
		this.z = z;
		this.w = w;
		length =(float) Math.sqrt(x*x+y*y+z*z+w*w);
	}
	
	public Quaternion initRotation(float angle,Vector3D axe){
		
		float hrad = (float)Math.toRadians(angle/2);
		float hcos = (float)Math.cos(hrad);
		float hsin = (float)Math.sin(hrad);
		
		this.x= axe.x * hsin;
		this.y= axe.y * hsin;
		this.z= axe.z * hsin;
		this.w= hcos;
		
		return this;
	}
	
	@Override
	public String toString(){
		return "x: "+x+" y: "+y+" z: "+z+" w: "+w+"normalized:"+normalized;
	}
	
	public Matrix toRoationMatrix(){
		Vector3D forward =new Vector3D(2f*(x*z-w*y),2f*(y*z+w*x),1f-2f*(x*x+y*y));
		Vector3D up = new Vector3D(2f*(x*y+w*z),1f-2f*(x*x+z*z),2f*(y*z-w*x));
		Vector3D right = new Vector3D(1f-2f*(y*y+z*z),2f*(x*y-w*z),2f*(x*z+w*y));

		return new Matrix().initRotation(forward, up, right);
	}
	
	public Vector3D getForward(){
		return new Vector3D(0,0,1).rotate(this);
	}
	public Vector3D getBack(){
		return new Vector3D(0,0,-1).rotate(this);
	}
	public Vector3D getUp(){
		return new Vector3D(0,1,0).rotate(this);
	}
	public Vector3D getDown(){
		return new Vector3D(0,-1,0).rotate(this);	
	}
	public Vector3D getRight(){
		return new Vector3D(1,0,0).rotate(this);
	}
	public Vector3D getLeft(){
		return new Vector3D(-1,0,0).rotate(this);
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
