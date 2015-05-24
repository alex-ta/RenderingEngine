package com.engine.Components;
import com.engine.Core.RenderingEngine;
import com.engine.Input.InputHandler;
import com.engine.Input.MouseW;
import com.engine.Math.Matrix;
import com.engine.Math.Quaternion;
import com.engine.Math.Vector3D;
import com.engine.Scenegraph.GameComponent;
import com.engine.Shaders.Shader;

public class Camera extends GameComponent {
	public static final Vector3D yAx = new Vector3D(0,1,0);

	private Matrix projection;
	private final float rotspeed = 0.9f;
	private final float movespeed = 0.006f;
		
	public Camera(float fov, float aspectRatio, float zNear, float zFar){
		this.projection = new Matrix().initPerspective(fov, aspectRatio, zNear, zFar);
	}
		
	public Matrix getViewProjection(){
		Matrix cameraRotation = parent.getTransform().getRotation().toRoationMatrix();
		Matrix cameraTranslation = new Matrix().initTranslation(parent.getTransform().getTranslation());
		return projection.mul(cameraRotation.mul(cameraTranslation));
	}
	
	public void move(Vector3D dir, float a){
		parent.getTransform().setTranslation(parent.getTransform().getTranslation().add(dir.mul(a)));
	}
	
	
	
	public Vector3D getPos() {
		return parent.getTransform().getTranslation();
	}


	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Shader shader) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void addToREngine(RenderingEngine engine) {
		engine.addCamera(this);
	}

	@Override
	public void input(InputHandler input) {
		if(input.isMoveleft()){
			this.move(parent.getTransform().getRotation().getLeft(),movespeed);
		}
		if(input.isMoveright()){
			this.move(parent.getTransform().getRotation().getRight(),movespeed);
		}
		if(input.isMoveforward()){
			this.move(parent.getTransform().getRotation().getForward(),-movespeed);
		}
		if(input.isMovebackwards()){
			this.move(parent.getTransform().getRotation().getForward(),movespeed);
		}
		parent.getTransform().setRotation(parent.getTransform().getRotation().mul(new Quaternion().initRotation(MouseW.getDeltaMovement().y*rotspeed,yAx)).normalize());
		parent.getTransform().setRotation(parent.getTransform().getRotation().mul(new Quaternion().initRotation(MouseW.getDeltaMovement().x*rotspeed,parent.getTransform().getRotation().getRight())).normalize());
	}

}
