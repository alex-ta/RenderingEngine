package com.engine.Shader;

import com.engine.components.PointLight;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.RenderingEngine;
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
		super("forward-point");
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
		setUniformPointLight("pointLight", (PointLight)engine.getActiveLight());
	}

}
