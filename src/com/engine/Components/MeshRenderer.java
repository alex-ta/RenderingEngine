package com.engine.components;

import com.engine.Shader.Shader;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.Mesh;

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
	public void render(Shader shader)
	{
		shader.bind();
		shader.updateUniforms(getTransform(), material);
		mesh.draw();
	}
}
