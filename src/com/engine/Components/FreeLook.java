package com.engine.components;

import com.engine.Shader.Shader;
import com.engine.core.MouseW;
import com.engine.rendering.objects.RenderingEngine;
import com.engine.scenegraph.GameComponent;
import com.math.Vector3D;

public class FreeLook extends GameComponent{
	public static final Vector3D yAxis = new Vector3D(0,1,0);
	private final float sensitivity;
	
	public FreeLook(float sensitivity){
		this.sensitivity = sensitivity;
	}
	
	
	@Override
	public void input(float delta)
	{
		getTransform().rotate(yAxis, (float) Math.toRadians(-MouseW.getDeltaMovement().getY() * sensitivity));
		getTransform().rotate(getTransform().getRot().getRight(), (float) Math.toRadians(MouseW.getDeltaMovement().getX() * sensitivity));
	
	}
	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Shader shader, RenderingEngine engine) {
	}
}
