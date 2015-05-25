package com.engine.Shader;

import com.engine.components.Light;
import com.engine.components.DirectionalLight;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.RenderingEngine;
import com.math.Matrix;
import com.math.Transform;

public class ForwardDirectionalLight extends Shader
{
	private static final ForwardDirectionalLight instance = new ForwardDirectionalLight();

	public static ForwardDirectionalLight getShader()
	{
		return instance;
	}

	private ForwardDirectionalLight()
	{
		super();

		addVertexShaderFromFile("forward-directional.vs");
		addFragmentShaderFromFile("forward-directional.fs");

		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

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

	public void updateUniforms(Transform transform, Material material, RenderingEngine engine)
	{
		Matrix worldMatrix = transform.getTransformation();
		Matrix projectedMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniformf("specularIntensity", material.getFloat("specularIntensity"));
		setUniformf("specularPower", material.getFloat("specularPower"));

		setUniform("camera", engine.getMainCamera().getTransform().getTransformedPos());
		setUniformDirectionalLight("directionalLight", (DirectionalLight)engine.getActiveLight());
	}

	public void setUniformBaseLight(String uniformName, Light baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniformDirectionalLight(String uniformName, DirectionalLight directionalLight)
	{
		setUniformBaseLight(uniformName + ".base", directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
}
