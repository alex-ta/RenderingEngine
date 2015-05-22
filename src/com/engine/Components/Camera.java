package com.engine.Components;
import com.engine.Math.Matrix;
import com.engine.Math.Vector3D;

public class Camera {
	public static final Vector3D yAx = new Vector3D(0,1,0);

	private Vector3D pos;
	private Vector3D forward;
	private Vector3D up;
	private Matrix projection;
		
	public Camera(float fov, float aspectRatio, float zNear, float zFar){
		this.pos = new Vector3D(0,0,0);
		this.forward = new Vector3D(0,0,1).normalize();
		this.up = new Vector3D(0,1,0).normalize();
		this.projection = new Matrix().initPerspective(fov, aspectRatio, zNear, zFar);
	}
		
	public Matrix getViewProjection(){
		Matrix mcamera = new Matrix().initCameraRotation(this.getForward(),this.getUp());
		Matrix cameraTranslation = new Matrix().initTranslation(-this.getPos().x, -this.getPos().y, -this.getPos().z);
		return projection.mul(mcamera.mul(cameraTranslation));
	}
	
	public void move(Vector3D dir, float a){
		
		pos = pos.add(dir.mul(a));
	}
	
	public Vector3D getLeft(){
		Vector3D left = up.cross(forward);
		left.normalize();
		return left;
	}
	
	public Vector3D getRight(){
		Vector3D right = forward.cross(up);
		right.normalize();
		return right;
	}
	
	public void rotateY(float angle){
		Vector3D horizontal = yAx.cross(forward);
		horizontal.normalize();
		
		forward.rotate(angle,yAx);
		forward.normalize();
		
		up = forward.cross(horizontal);
		up.normalize();
		
	}
	
	public void rotateX(float angle){
		Vector3D horizontal = yAx.cross(forward);
		horizontal.normalize();
		forward.rotate(angle,horizontal);
		forward.normalize();
		
		up = forward.cross(horizontal);
		up.normalize();
	}
	
	public Vector3D getPos() {
		return pos;
	}

	public void setPos(Vector3D pos) {
		this.pos = pos;
	}

	public Vector3D getForward() {
		return forward;
	}

	public void setForward(Vector3D forward) {
		this.forward = forward;
	}

	public Vector3D getUp() {
		return up;
	}

	public void setUp(Vector3D up) {
		this.up = up;
	}

}
