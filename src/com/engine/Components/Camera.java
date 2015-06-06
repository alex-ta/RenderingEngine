package com.engine.components;

import com.engine.Shader.Shader;
import com.engine.core.*;
import com.engine.rendering.objects.RenderingEngine;
import com.engine.scenegraph.GameComponent;
import com.math.Matrix;
import com.math.Vector3D;

public class Camera extends GameComponent
{
	private Matrix projection;

	public Camera(float fov, float aspect, float zNear, float zFar)
	{
		this.projection = new Matrix().initPerspective(fov, aspect, zNear, zFar);
	}

	public Matrix getViewProjection()
	{
		Matrix cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3D cameraPos = getTransform().getTransformedPos().mul(-1);

		Matrix cameraTranslation = new Matrix().initTranslation(cameraPos.getX(), cameraPos.getY(), cameraPos.getZ());

		return projection.mul(cameraRotation.mul(cameraTranslation));
	}

	@Override
	public void addToEngine(CoreEngine engine)
	{
		engine.getRenderingEngine().addCamera(this);
	}

	@Override
	public void input(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Shader shader, RenderingEngine engine) {
		// TODO Auto-generated method stub
		
	}

}
