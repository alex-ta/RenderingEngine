package com.engine.Shaders;

import com.engine.Components.DirectionalLight;
import com.engine.Desing.Material;
import com.engine.Math.*;

public class ForwardDirectionalLight extends Shader
{
	private static final Shader instance = new ForwardDirectionalLight();

	public static Shader getShader()
	{
		return instance;
	}

	private ForwardDirectionalLight()
	{
		super();

		addVertexShader(loadShaderFile("forward-directional.vs"));
		addFragmentShader(loadShaderFile("forward-directional.fs"));

		addAttr("position", 0);
		addAttr("texCoord", 1);
		addAttr("normal", 2);
		
		compileShader();

		addUniform("model");
		addUniform("MVP");

		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("camera");

		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
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
		System.out.println(getRenderingEngine().getLight().getClass().getName());
		setUniform("directionalLight", (DirectionalLight)getRenderingEngine().getLight());
	}
}