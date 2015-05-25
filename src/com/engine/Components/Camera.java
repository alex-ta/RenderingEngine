package com.engine.components;

import com.engine.core.*;
import com.engine.rendering.objects.RenderingEngine;
import com.math.Matrix;
import com.math.Vector2D;
import com.math.Vector3D;

public class Camera extends GameComponent
{
	public static final Vector3D yAxis = new Vector3D(0,1,0);

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
	public void addToRenderingEngine(RenderingEngine renderingEngine)
	{
		renderingEngine.addCamera(this);
	}

	@Override
	public void input(float delta)
	{
		float sensitivity = 0.5f;
		float movAmt = (float)(10 * delta);
		

		if(Input.isMoveforward())
			move(getTransform().getRot().getForward(), movAmt);
		if(Input.isMovebackwards())
			move(getTransform().getRot().getForward(), -movAmt);
		if(Input.isMoveleft())
			move(getTransform().getRot().getLeft(), -movAmt);
		if(Input.isMoveright())
			move(getTransform().getRot().getRight(), -movAmt);
	
		getTransform().rotate(yAxis, (float) Math.toRadians(-MouseW.getDeltaMovement().getY() * sensitivity));
		getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(MouseW.getDeltaMovement().getX() * sensitivity));
	
	}

	public void move(Vector3D dir, float amt)
	{
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}
}
