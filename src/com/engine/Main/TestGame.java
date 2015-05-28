package com.engine.main;
import com.engine.Shader.*;
import com.engine.components.Camera;
import com.engine.components.DirectionalLight;
import com.engine.components.MeshRenderer;
import com.engine.components.PointLight;
import com.engine.components.SpotLight;
import com.engine.core.*;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.Mesh;
import com.engine.rendering.objects.Texture;
import com.engine.rendering.objects.Vertex;
import com.engine.scenegraph.GameObject;
import com.math.Quaternion;
import com.math.Vector2D;
import com.math.Vector3D;

public class TestGame extends Game
{
	public TestGame(){
		super();
	}
	public void init()
	{
		material.addTexture("diffuse",new Texture("test.png"));
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;

		Vertex[] vertices = new Vertex[] { 	new Vertex( new Vector3D(-fieldWidth, 0.0f, -fieldDepth), new Vector2D(0.0f, 0.0f)),
				new Vertex( new Vector3D(-fieldWidth, 0.0f, fieldDepth * 3), new Vector2D(0.0f, 1.0f)),
				new Vertex( new Vector3D(fieldWidth * 3, 0.0f, -fieldDepth), new Vector2D(1.0f, 0.0f)),
				new Vertex( new Vector3D(fieldWidth * 3, 0.0f, fieldDepth * 3), new Vector2D(1.0f, 1.0f))};

		int indices[] = { 0, 1, 2,
				2, 1, 3};

		Vertex[] vertices2 = new Vertex[] { 	new Vertex( new Vector3D(-fieldWidth/ 10, 0.0f, -fieldDepth/ 10), new Vector2D(0.0f, 0.0f)),
				new Vertex( new Vector3D(-fieldWidth/ 10, 0.0f, fieldDepth/ 10 * 3), new Vector2D(0.0f, 1.0f)),
				new Vertex( new Vector3D(fieldWidth/ 10 * 3, 0.0f, -fieldDepth/ 10), new Vector2D(1.0f, 0.0f)),
				new Vertex( new Vector3D(fieldWidth/ 10 * 3, 0.0f, fieldDepth/ 10 * 3), new Vector2D(1.0f, 1.0f))};

		int indices2[] = { 0, 1, 2,
				2, 1, 3};

		Mesh mesh2 = new Mesh(vertices2, indices2, true);

		Mesh mesh = new Mesh(vertices, indices, true);

		Mesh tempMesh = new Mesh("monkey3.obj");
		
		
		MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

		GameObject planeObject = new GameObject();
		planeObject.addComponent(meshRenderer);
		planeObject.getTransform().getPos().set(0, -1, 5);

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight(new Vector3D(0,0,1), 0.4f);

		directionalLightObject.addComponent(directionalLight);

		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent(new PointLight(new Vector3D(0,1,0), 0.4f, new Vector3D(0,0,1)));

		SpotLight spotLight = new SpotLight(new Vector3D(0,1,1), 0.4f,
				new Vector3D(0,0,0.1f), 0.7f);

		GameObject spotLightObject = new GameObject();
		spotLightObject.addComponent(spotLight);

		spotLightObject.getTransform().getPos().set(5, 0, 5);
		spotLightObject.getTransform().setRot(new Quaternion(new Vector3D(0,1,0), (float)Math.toRadians(90.0f)));

		addChild(planeObject);
		addChild(directionalLightObject);
		addChild(pointLightObject);
		addChild(spotLightObject);

		//getRootObject().addChild(new GameObject().addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f)));

		GameObject testMesh1 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh2 = new GameObject().addComponent(new MeshRenderer(mesh2, material));
		GameObject testMesh3 = new GameObject().addComponent(new MeshRenderer(tempMesh, material));
		 

		testMesh1.getTransform().getPos().set(0, 2, 0);
		testMesh1.getTransform().setRot(new Quaternion(new Vector3D(0,1,0), 0.4f));

		testMesh2.getTransform().getPos().set(0, 0, 5);

		testMesh1.addChild(testMesh2);
		testMesh2
		//getRootObject()
						.addChild(new GameObject().addComponent(new Camera((float)Math.toRadians(70.0f), (float)Window.getWidth()/(float)Window.getHeight(), 0.01f, 1000.0f)));

		addChild(testMesh1);
		addChild(testMesh3);
		
		testMesh3.getTransform().getPos().set(5,5,5);
		testMesh3.getTransform().setRot(new Quaternion(new Vector3D(1,0,0),(float)Math.toRadians(-45)));
		
		directionalLight.getTransform().setRot(new Quaternion(new Vector3D(1,0,0), (float)Math.toRadians(-45)));
	}
}
