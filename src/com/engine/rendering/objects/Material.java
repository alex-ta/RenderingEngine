package com.engine.rendering.objects;

import java.util.HashMap;

import com.math.Vector3D;

public class Material
{
	private HashMap<String,Texture> textureMap;
	private HashMap<String,Vector3D> colorMap;
	private HashMap<String,Float> intensityMap;
	
	public Material(){
		textureMap = new HashMap<String,Texture>();
		colorMap = new HashMap<String,Vector3D>();
		intensityMap = new HashMap<String,Float>();
	}
	
	public void addTexture(String name,Texture texture){
		textureMap.put(name, texture);
	}
	public Texture getTexture(String name){
		if(textureMap.containsKey(name))
		return textureMap.get(name);
		System.out.println("No Value found for "+name+" returned test.png");
		return new Texture("test.png");
	}
	public void addColor(String name,Vector3D color){
		colorMap.put(name, color);
	}
	public Vector3D getColor(String name){
		if(colorMap.containsKey(name))
		return colorMap.get(name);
		System.out.println("No Value found for "+name+" returned 0 Vector");
		return new Vector3D(0,0,0);
	}
	public void addFloat(String name,float intensity){
		intensityMap.put(name, intensity);
	}
	public float getFloat(String name){
		if(intensityMap.containsKey(name))
		return intensityMap.get(name);
		System.out.println("No value found for "+name+" returned 0 Value");
		return 0f;
	}
}
