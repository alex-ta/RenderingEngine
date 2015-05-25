package com.engine.Shader;

import com.engine.components.PointLight;
import com.engine.rendering.objects.Material;
import com.math.Matrix;
import com.math.Transform;

public class ForwardPointLight extends Shader
{
	private static final ForwardPointLight instance = new ForwardPointLight();

	public static ForwardPointLight getShader()
	{
		return instance;
	}

	private ForwardPointLight()
	{
		super();

		addVertexShaderFromFile("forward-point.vs");
		addFragmentShaderFromFile("forward-point.fs");

		setAttribLocation("position", 0);
		setAttribLocation("texCoord", 1);
		setAttribLocation("normal", 2);

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

		setUniformf("specularIntensity", material.getSpecularIntensity());
		setUniformf("specularPower", material.getSpecularPower());

		setUniform("camera", getRenderingEngine().getMainCamera().getTransform().getTransformedPos());
		setUniformPointLight("pointLight", (PointLight)getRenderingEngine().getActiveLight());
	}

}
