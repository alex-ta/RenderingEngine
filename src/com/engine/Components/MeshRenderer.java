package com.engine.components;

import com.engine.Shader.Shader;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.Mesh;
import com.engine.rendering.objects.RenderingEngine;

public class MeshRenderer extends GameComponent
{
	private Mesh mesh;
	private Material material;

	public MeshRenderer(Mesh mesh, Material material)
	{
		this.mesh = mesh;
		this.material = material;
	}

	@Override
	public void render(Shader shader,RenderingEngine engine)
	{
		shader.bind();
		shader.updateUniforms(getTransform(), material, engine);
		mesh.draw();
	}

	@Override
	public void input(float delta) {
	}

	@Override
	public void update(float delta) {
	}
}
