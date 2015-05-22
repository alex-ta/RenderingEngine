package com.engine.Scenegraph;

import com.engine.Core.RenderingEngine;
import com.engine.Desing.Material;
import com.engine.Math.Transform;
import com.engine.RenderObjects.Mesh;
import com.engine.Shaders.Shader;

public class MeshRenderer implements GameComponent{
	
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh,Material material){
		this.mesh = mesh;
		this.material = material;
	}
	
	@Override
	public void render(Transform transform,Shader shader){
		shader.bind();
		shader.updateUniforms(transform,material);
		mesh.draw();
		
	}

	@Override
	public void update(Transform transform) {}
	@Override
	public void addToREngine(RenderingEngine engine) {}

}
