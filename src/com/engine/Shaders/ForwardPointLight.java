package com.engine.Shaders;

import com.engine.Components.PointLight;
import com.engine.Desing.Material;
import com.engine.Math.Matrix;
import com.engine.Math.Transform;

public class ForwardPointLight extends Shader
{
	private static final Shader instance = new ForwardPointLight();

	public static Shader getShader()
	{
		return instance;
	}

	private ForwardPointLight()
	{
		super();

		addVertexShader(loadShaderFile("forward-point.vs"));
		addFragmentShader(loadShaderFile("forward-point.fs"));

		addAttr("position", 0);
		addAttr("texCoord", 1);
		addAttr("normal", 2);
		
		compileShader();

		addUniform("model");
		addUniform("MVP");

		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("camera");

		addUniform("pointLight.base.color");
		addUniform("pointLight.base.intensity");
		addUniform("pointLight.atten.constant");
		addUniform("pointLight.atten.linear");
		addUniform("pointLight.atten.exponent");
		addUniform("pointLight.position");
		addUniform("pointLight.range");
	}

	public void updateUniforms(Transform transform, Material material)
	{
		Matrix worldMatrix = transform.getTransformation();
		Matrix projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture().bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniform("specularIntensity", material.getSpecularIntensity());
		setUniform("specularPower", material.getSpecularPower());

		setUniform("camera", getRenderingEngine().getMainCamera().getPos());
		setUniform("pointLight", (PointLight) getRenderingEngine().getLight());
	}

}