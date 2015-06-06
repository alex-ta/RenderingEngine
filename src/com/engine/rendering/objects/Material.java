package com.engine.rendering.objects;

import java.util.HashMap;

import com.engine.rendering.ressource.MappedValues;
import com.math.Vector3D;

public class Material extends MappedValues
{
	private HashMap<String,Texture> textureMap;
	
	public Material(Texture diffuse,Texture normal,Texture dispMap, float dispMapScale, float dispMapOffset){
		super();
		addFloat("specularIntensity", 1f);
		addFloat("specularPower", 8f);
		textureMap = new HashMap<String,Texture>();
		addTexture("diffuse", diffuse);
		addTexture("normalMap", normal);
		addTexture("dispMap", dispMap);
		float baseBias = dispMapScale/2.0f;
		addFloat("dispMapScale", dispMapScale);
		addFloat("dispMapBias", -baseBias + baseBias * dispMapOffset);
		
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
}
