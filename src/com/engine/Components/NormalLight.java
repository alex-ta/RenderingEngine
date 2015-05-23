package com.engine.Components;

import com.engine.Core.RenderingEngine;
import com.engine.Math.Vector3D;
public class NormalLight extends Light{

	private NormalLight(float r, float g, float b, float intensity) {
		super(new Vector3D(r, g, b), intensity);
	}
	private static final Light light = new NormalLight(0.05f,0.05f,0.05f,0f);
	
	public static Light getInstance(RenderingEngine engine){
		light.addToREngine(engine);
		return light;
	}
	public void setNormalLightColor(float red,float green,float blue){
		light.setColor(new Vector3D(red,green,blue));
	}
	
	
}
