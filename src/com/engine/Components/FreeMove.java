package com.engine.components;

import com.engine.Shader.Shader;
import com.engine.core.Input;
import com.engine.rendering.objects.RenderingEngine;
import com.engine.scenegraph.GameComponent;
import com.math.Vector3D;

public class FreeMove extends GameComponent{

	private final float speed;
	
	public FreeMove(float speed){
		this.speed = speed;
	}
	
	@Override
	public void input(float delta)
	{
		if(Input.isMoveforward())
			move(getTransform().getRot().getForward(), speed);
		if(Input.isMovebackwards())
			move(getTransform().getRot().getForward(), -speed);
		if(Input.isMoveleft())
			move(getTransform().getRot().getLeft(), -speed);
		if(Input.isMoveright())
			move(getTransform().getRot().getRight(), -speed);
	}

	public void move(Vector3D dir, float amt)
	{
		getTransform().setPos(getTransform().getPos().add(dir.mul(amt)));
	}

	@Override
	public void update(float delta) {
	}

	@Override
	public void render(Shader shader, RenderingEngine engine) {
	}
}
