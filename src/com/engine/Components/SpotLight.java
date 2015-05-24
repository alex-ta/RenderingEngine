package com.engine.Components;
import com.engine.Math.Vector3D;
import com.engine.Shaders.ForwardSpotLight;
public class SpotLight extends PointLight{

	private float cutoff;
	
	public SpotLight(Vector3D color,float intensity,Vector3D attenuation, float cutoff) {
		super(color,intensity,attenuation);
		this.cutoff = cutoff;
		this.setShader(ForwardSpotLight.getShader());
	}
	
	public Vector3D getDircetion() {
		return new Vector3D(1,1,1);
		//return parent.getTransform().getRotation().getForward();
	}
	
	public float getCutoff() {
		return cutoff;
	}
	public void setCutoff(float cutoff) {
		this.cutoff = cutoff;
	}

}
