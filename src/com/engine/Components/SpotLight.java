package com.engine.Components;
import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardSpotLight;
public class SpotLight extends PointLight{

	private Vector3D dircetion;
	private float cutoff;
	
	public SpotLight(Vector3D color,float intensity,float constant,float linear, float exponent,Vector3D position,float range, Vector3D dircetion, float cutoff) {
		super(color,intensity,constant,linear,exponent,position,range);
		this.dircetion = dircetion.normalize();
		this.cutoff = cutoff;
		this.setShader(ForwardSpotLight.getShader());
	}
	
	public Vector3D getDircetion() {
		return dircetion;
	}
	public void setDircetion(Vector3D dircetion) {
		this.dircetion = dircetion.normalize();
	}
	public float getCutoff() {
		return cutoff;
	}
	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

}
