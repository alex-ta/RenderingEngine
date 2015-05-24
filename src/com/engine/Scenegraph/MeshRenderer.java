package com.engine.Scenegraph;

import com.engine.Core.RenderingEngine;
import com.engine.Desing.Material;
import com.engine.Input.InputHandler;
import com.engine.Math.Transform;
import com.engine.RenderObjects.Mesh;
import com.engine.Shaders.Shader;

public class MeshRenderer extends GameComponent{
	
	private Mesh mesh;
	private Material material;
	
	public MeshRenderer(Mesh mesh,Material material){
		this.mesh = mesh;
		this.material = material;
	}
	
	@Override
	public void render(Shader shader){
		shader.bind();
		shader.updateUniforms(parent.getTransform(),material);
		mesh.draw();
		
	}

	@Override
	public void input(InputHandler input) {}
	@Override
	public void update() {}
	@Override
	public void addToREngine(RenderingEngine engine) {}

}
