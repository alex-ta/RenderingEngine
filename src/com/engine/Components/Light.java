package com.engine.Components;
import com.engine.Scenegraph.*;
import com.engine.Shaders.ForwardNormalLight;
import com.engine.Shaders.Shader;
import com.engine.Core.RenderingEngine;
import com.engine.Math.Transform;
import com.engine.Math.Vector3D;

public abstract class Light implements GameComponent {
private Vector3D color;
private float intensity;
private Shader shader;

public Light(Vector3D color,float intensity){
	this.color = color;
	this.intensity = intensity;
	this.setShader(ForwardNormalLight.getShader());
}

public Vector3D getColor() {
	return color;
}

public void setColor(Vector3D color) {
	this.color = color;
}

public float getIntensity() {
	return intensity;
}

public void setIntensity(float intensity) {
	this.intensity = intensity;
}

protected void setShader  (Shader shader){
	this.shader = shader;
}

public Shader getShader(){
	return shader;
}

@Override
public void update(Transform transform) {
	// TODO Auto-generated method stub
	
}

@Override
public void render(Transform transform, Shader shader) {
	// TODO Auto-generated method stub
	
}

@Override
public void addToREngine(RenderingEngine engine) {
	getShader().setRenderingEngine(engine);
	engine.addLight(this);
}
}
