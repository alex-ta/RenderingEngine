package com.engine.Math;

public class Vector2D {
	
	public float x,y;
	private float length;
	private boolean normalized;
	
	public Vector2D(float x, float y){
		this.x = x;
		this.y = y;
		this.length = (float)Math.sqrt(x*x+y*y);
		this.normalized = false;
	}
	
	@Override
	public String toString(){
		return "x: "+x+" y: "+y+"normalized:"+normalized;
	}
	
	public float length(){
		if(normalized)
		return 1;
		return length;
	}
	
	public float dot(Vector2D v){
		return x*v.x+y*v.y;
	}
	
	public Vector2D normalize(){
	
		x/=length();
		y/= length();
		normalized = true;
		
		return this;
	}
	
	public Vector2D denormalize(){
		
		x*=length;
		y*=length;
		normalized = false;
		
		return this;
	}
	
	
	public Vector2D rotate(float angle){
		double rad = Math.toRadians(angle);
		double cos = Math.cos(rad);
		double sin = Math.sin(rad);
		
		return new Vector2D((float)(x*cos - y*sin),(float)(x*sin + y*cos));
	}
	
	public Vector2D lerp(Vector2D v,float lerp){
		return v.sub(this).mul(lerp).add(this);
	}
	
	public boolean equals(Vector2D v){
		return x==v.x && y == v.y;
	}
	
	public float cross(Vector2D v){
		return x*v.y - y*v.x;
	}
	
	public Vector2D add(Vector2D v){
		return new Vector2D(x+v.x,y+v.y);
	}
	public Vector2D add(float v){
		return new Vector2D(x+v,y+v);
	}
	public Vector2D sub(Vector2D v){
		return new Vector2D(x-v.x,y-v.y);
	}
	public Vector2D sub(float v){
		return new Vector2D(x-v,y-v);
	}
	public Vector2D div(Vector2D v){
		return new Vector2D(x/v.x,y/v.y);
	}
	public Vector2D div(float v){
		return new Vector2D(x/v,y/v);
	}
	public Vector2D mul(Vector2D v){
		return new Vector2D(x*v.x,y*v.y);
	}
	public Vector2D mul(float v){
		return new Vector2D(x*v,y*v);
	}
	
	
}
