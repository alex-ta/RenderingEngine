package com.engine.Shaders;

import com.engine.Desing.Material;
import com.engine.Math.Matrix;
import com.engine.Math.Transform;

public class ForwardNormalLight extends Shader{
private static final Shader instance = new ForwardNormalLight();
public static Shader getShader(){return instance;}

private ForwardNormalLight(){
	super();
	addVertexShader(loadShaderFile("forward-normlight.vs"));
	addFragmentShader(loadShaderFile("forward-normlight.fs"));
	this.addAttr("position",0);
	this.addAttr("texCoord",1);
	this.addAttr("normal",2);
	compileShader();
	
	addUniform("MVP");
	addUniform("normlight");
}

@Override
public void updateUniforms(Transform transform, Material material) {
	
	Matrix worldMatrix = transform.getTransformation();
	Matrix projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
	material.getTexture().bind();
	
	setUniform("MVP",projectedMatrix);
	setUniform("normlight",getRenderingEngine().lit);
}

}
