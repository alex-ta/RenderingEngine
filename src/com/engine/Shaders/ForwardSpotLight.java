package com.engine.Shaders;

import com.engine.Components.SpotLight;
import com.engine.Desing.Material;
import com.engine.Math.Matrix;
import com.engine.Math.Transform;

public class ForwardSpotLight extends Shader
{
	private static final Shader instance = new ForwardSpotLight();

	public static Shader getShader()
	{
		return instance;
	}

	private ForwardSpotLight()
	{
		super();

		addVertexShader(loadShaderFile("forward-spot.vs"));
		addFragmentShader(loadShaderFile("forward-spot.fs"));

		addAttr("position", 0);
		addAttr("texCoord", 1);
		addAttr("normal", 2);
		
		compileShader();

		addUniform("model");
		addUniform("MVP");

		addUniform("specularIntensity");
		addUniform("specularPower");
		addUniform("camera");

		addUniform("spotLight.pointLight.base.color");
		addUniform("spotLight.pointLight.base.intensity");
		addUniform("spotLight.pointLight.atten.constant");
		addUniform("spotLight.pointLight.atten.linear");
		addUniform("spotLight.pointLight.atten.exponent");
		addUniform("spotLight.pointLight.position");
		addUniform("spotLight.pointLight.range");
		addUniform("spotLight.direction");
		addUniform("spotLight.cutoff");
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
		setUniform("spotLight", (SpotLight)getRenderingEngine().getLight());
	}
}