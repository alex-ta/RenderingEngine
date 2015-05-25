package com.engine.Shader;

import com.engine.rendering.objects.Material;
import com.math.Matrix;
import com.math.Transform;

public class ForwardNormalLight extends Shader{
		private static final Shader instance = new ForwardNormalLight();
		public static Shader getShader(){return instance;}

		private ForwardNormalLight(){
			super();
			addVertexShaderFromFile("forward-normlight.vs");
			addFragmentShaderFromFile("forward-normlight.fs");
			this.setAttribLocation("position",0);
			this.setAttribLocation("texCoord",1);
			this.setAttribLocation("normal",2);
			compileShader();
			
			addUniform("MVP");
			addUniform("normlight");
		}

		@Override
		public void updateUniforms(Transform transform, Material material) {
			
			Matrix worldMatrix = transform.getTransformation();
			Matrix projectedMatrix = getRenderingEngine().getMainCamera().getViewProjection().mul(worldMatrix);
			material.getTexture().bind();
			
			setUniform("MVP",projectedMatrix);
			setUniform("normlight",getRenderingEngine().getNormalLight());
		}

		}
