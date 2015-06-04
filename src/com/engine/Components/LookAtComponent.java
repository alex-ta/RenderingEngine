package com.engine.components;

import com.engine.Shader.Shader;
import com.engine.rendering.objects.RenderingEngine;
import com.engine.scenegraph.GameComponent;
import com.math.Quaternion;
import com.math.Vector3D;

public class LookAtComponent extends GameComponent
{
	RenderingEngine renderingEngine;

	@Override
	public void update(float delta)
	{
		if(renderingEngine != null)
		{
			Quaternion newRot = getTransform().getLookAtDirection(renderingEngine.getMainCamera().getTransform().getTransformedPos(),
					new Vector3D(0,1,0));
					//getTransform().getRot().getUp());

			//getTransform().setRot(getTransform().getRot().nlerp(newRot, delta * 5.0f, true));
			getTransform().setRot(getTransform().getRot().slerp(newRot, delta * 5.0f, true));
		}
	}

	@Override
	public void render(Shader shader, RenderingEngine renderingEngine)
	{
		this.renderingEngine = renderingEngine;
	}

	@Override
	public void input(float delta) {
		// TODO Auto-generated method stub
	}
}
