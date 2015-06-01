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
		super("forward-directional");
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
