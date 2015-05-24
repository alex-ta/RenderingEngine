package com.engine.Math;

public class Transform {
	
	private Vector3D translation;
	private Quaternion rotation;
	private Vector3D scale;
	
	
	public Transform(){
		translation = new Vector3D(0,0,0);
		rotation = new Quaternion(0,0,0,1);
		setScale(new Vector3D(1,1,1));
	}
	
	public Matrix getTransformation(){
		Matrix mtrans = new Matrix().initTranslation(translation.x,translation.y,translation.z);
		Matrix mrot = rotation.toRoationMatrix();		
		Matrix mscale = new Matrix().initScale(scale.x, scale.y, scale.z);
		return mtrans.mul(mrot.mul(mscale));
	}
	
	public Vector3D getTranslation() {
		return translation;
	}

	public void setTranslation(Vector3D translation) {
		this.translation = translation;
	}
	
	public void setTranslation(float x,float y,float z) {
		this.translation.set(x,y,z);
	}

	public Quaternion getRotation() {
		return rotation;
	}

	public void setRotation(Quaternion rotation) {
		this.rotation = rotation;
	}

	public Vector3D getScale() {
		return scale;
	}

	public void setScale(Vector3D scale) {
		this.scale = scale;
	}
	
	public void setScale(float x, float y, float z) {
		this.scale .set(x, y, z);
	}
	
}
