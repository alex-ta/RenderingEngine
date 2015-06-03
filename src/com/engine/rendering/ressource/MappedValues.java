package com.engine.rendering.ressource;

import java.util.HashMap;

import com.math.Vector3D;

public abstract class MappedValues {
	private HashMap<String,Vector3D> vec3Map;
	private HashMap<String,Float> floatMap;
	
	public MappedValues(){
		vec3Map = new HashMap<String,Vector3D>();
		floatMap = new HashMap<String,Float>();
	}
	
	public void addVector3D(String name, Vector3D value){
		vec3Map.put(name, value);
	}
	public void addFloat(String name,Float fl){
		floatMap.put(name, fl);
	}
	public Vector3D getVector3D(String name){
		if(vec3Map.containsKey(name))
		return vec3Map.get(name);
		System.out.println("No Value found for "+name+" returned 0 Vector");
		return new Vector3D(0,0,0);
	} 
	public float getFloat(String name){
		if(floatMap.containsKey(name))
		return floatMap.get(name);
		System.out.println("No value found for "+name+" returned 0 Value");
		return 0f;
	}
}
