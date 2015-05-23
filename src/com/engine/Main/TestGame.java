package com.engine.Main;

import com.engine.Components.DirectionalLight;
import com.engine.Components.NormalLight;
import com.engine.Components.PointLight;
import com.engine.Components.SpotLight;
import com.engine.Core.Game;
import com.engine.Desing.Material;
import com.engine.Desing.Texture;
import com.engine.Math.Vector3D;
import com.engine.RenderObjects.Mesh;
import com.engine.RenderObjects.Vertex;
import com.engine.Scenegraph.GameObject;
import com.engine.Scenegraph.MeshRenderer;

public class TestGame extends Game {
	
	public TestGame(){
		super();
		
		float fd = 10.0f;
		float fw = 10.0f;
		Vertex[] vertecies = new Vertex[] {
			new Vertex(-fw,0f,-fd,0f,0f),
			new Vertex(-fw,0f,fd*3,0f,1f),
			new Vertex(fw*3,0f,-fd,1f,0f),
			new Vertex(fw*3,0f,fd*3,1f,1f)	
		};
		
		int indices[] = {0,1,2,2,1,3};
		
		Material material = new Material(new Texture("test.png"), new Vector3D(1,1,1),1,8);
		Mesh mesh = new Mesh(vertecies, indices,true);
		MeshRenderer renderer = new MeshRenderer(mesh,material);
		
		GameObject plane = new GameObject();
		plane.addCompontent(renderer);
		plane.getTransform().setTranslation(0,-1,5);
		
		DirectionalLight l = new DirectionalLight(new Vector3D(0,0,1),0.4f,new Vector3D(1,1,1));
		PointLight pl = new PointLight(new Vector3D(0,1,0),0.4f,0,0,1,new Vector3D(5,0,5),100);
		SpotLight sl = new SpotLight(new Vector3D(1,1,1),0.4f,0,0,0.1f,new Vector3D(5,0,5),100,new Vector3D(1,0,0),0.7f);
		
		plane.addCompontent(l);
		plane.addCompontent(pl);
		plane.addCompontent(sl);
		
		addChild(plane);
		
		
	}
	
}
