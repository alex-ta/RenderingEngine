package com.engine.Components;

import com.engine.Math.Vector3D;
public class NormalLight extends Light{

	public NormalLight(float r, float g, float b, float intensity) {
		super(new Vector3D(r, g, b), intensity);
	}

}
