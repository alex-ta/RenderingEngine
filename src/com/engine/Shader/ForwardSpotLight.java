package com.engine.Shader;

import com.engine.components.SpotLight;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.RenderingEngine;
import com.math.Matrix;
import com.math.Transform;

public class ForwardSpotLight extends Shader
{
	private static final ForwardSpotLight instance = new ForwardSpotLight();

	public static ForwardSpotLight getShader()
	{
		return instance;
	}

	private ForwardSpotLight()
	{
		super("forward-spot");
	}

	public void updateUniforms(Transform transform, Material material,RenderingEngine engine)
	{
		Matrix worldMatrix = transform.getTransformation();
		Matrix projectedMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);
		material.getTexture("diffuse").bind();

		setUniform("model", worldMatrix);
		setUniform("MVP", projectedMatrix);

		setUniformf("specularIntensity", material.getFloat("specularIntensity"));
		setUniformf("specularPower", material.getFloat("specularPower"));

		setUniform("camera", engine.getMainCamera().getTransform().getTransformedPos());
		setUniformSpotLight("spotLight", (SpotLight)engine.getActiveLight());
	}

}
