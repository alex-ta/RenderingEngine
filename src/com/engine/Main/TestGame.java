package com.engine.main;
import com.engine.Shader.*;
import com.engine.components.Camera;
import com.engine.components.DirectionalLight;
import com.engine.components.FreeLook;
import com.engine.components.LookAtComponent;
import com.engine.components.MeshRenderer;
import com.engine.components.FreeMove;
import com.engine.components.PointLight;
import com.engine.components.SpotLight;
import com.engine.core.*;
import com.engine.rendering.objects.Attenuation;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.Mesh;
import com.engine.rendering.objects.Texture;
import com.engine.rendering.objects.Vertex;
import com.engine.scenegraph.Game;
import com.engine.scenegraph.GameObject;
import com.math.Matrix;
import com.math.Quaternion;
import com.math.Vector2D;
import com.math.Vector3D;


public class TestGame extends Game
	{
		public void Init()
		{
			Mesh mesh = new Mesh("plane3.obj");
			Material material2 = new Material(new Texture("bricks.jpg"),
				new Texture("bricks_normal.jpg"), new Texture("bricks_disp.png"), 0.03f, -0.5f);

			Material material = new Material(new Texture("bricks2.jpg"),
					new Texture("bricks2_normal.png"), new Texture("bricks2_disp.jpg"), 0.04f, -1.0f);

			Mesh tempMesh = new Mesh("monkey3.obj");

			MeshRenderer meshRenderer = new MeshRenderer(mesh, material);

			GameObject planeObject = new GameObject();
			planeObject.addComponent(meshRenderer);
			planeObject.getTransform().getPos().set(0, -1, 5);

			GameObject directionalLightObject = new GameObject();
			DirectionalLight directionalLight = new DirectionalLight(new Vector3D(0,0,1), 0.4f);

			directionalLightObject.addComponent(directionalLight);

			GameObject pointLightObject = new GameObject();
			pointLightObject.addComponent(new PointLight(new Vector3D(0, 1, 0), 0.4f, new Attenuation(0, 0, 1)));

			SpotLight spotLight = new SpotLight(new Vector3D(0,1,1), 0.4f,
					new Attenuation(0,0,0.1f), 0.7f);

			GameObject spotLightObject = new GameObject();
			spotLightObject.addComponent(spotLight);

			spotLightObject.getTransform().getPos().set(5, 0, 5);
			spotLightObject.getTransform().setRot(new Quaternion(new Vector3D(0, 1, 0), (float) Math.toRadians(90.0f)));

			addChild(planeObject);
			addChild(directionalLightObject);
			addChild(pointLightObject);
			addChild(spotLightObject);

			GameObject testMesh3 = new GameObject().addComponent(new LookAtComponent()).addComponent(new MeshRenderer(tempMesh, material));

			addChild(
					//AddObject(
					new GameObject().addComponent(new FreeLook(0.5f)).addComponent(new FreeMove(10.0f))
							.addComponent(new Camera((float)Math.toRadians(70.0f),
									(float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f)));

			addChild(testMesh3);

			testMesh3.getTransform().getPos().set(5, 5, 5);
			testMesh3.getTransform().setRot(new Quaternion(new Vector3D(0, 1, 0), (float) Math.toRadians(-70.0f)));

			addChild(new GameObject().addComponent(new MeshRenderer(new Mesh("monkey3.obj"), material2)));

			directionalLight.getTransform().setRot(new Quaternion(new Vector3D(1, 0, 0), (float) Math.toRadians(-45)));
		}
}
