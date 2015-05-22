package com.engine.RenderObjects;

import com.engine.Math.Vector2D;
import com.engine.Math.Vector3D;

public class Vertex {
	
	public static final int SIZE = 8;
	private Vector3D pos;
	private Vector2D texturepos;
	private Vector3D normal;
	
	
	public Vertex(Vector3D pos, Vector2D texturepos,Vector3D normal){
		this.setPos(pos);
		this.setTexturepos(texturepos);
		this.setNormal(normal);
	}
	public Vertex(Vector3D pos){
		this(pos,new Vector2D(0,0),new Vector3D(0,0,0));
	}
	public Vertex(float x, float y, float z){
		this(new Vector3D(x,y,z),new Vector2D(0,0),new Vector3D(0,0,0));
	}
	public Vertex(float x, float y, float z, float tx, float ty){
		this(new Vector3D(x,y,z),new Vector2D(tx,ty),new Vector3D (0,0,0));
	}
	public Vertex(float x, float y, float z, float tx, float ty,float nx, float ny, float nz){
		this(new Vector3D(x,y,z),new Vector2D(tx,ty),new Vector3D (nx,ny,nz));
	}

	

	public Vector3D getPos() {
		return pos;
	}

	public void setPos(Vector3D pos) {
		this.pos = pos;
	}

	public Vector2D getTexturepos() {
		return texturepos;
	}

	public void setTexturepos(Vector2D texturepos) {
		this.texturepos = texturepos;
	}
	public Vector3D getNormal() {
		return normal;
	}
	public void setNormal(Vector3D normal) {
		this.normal = normal;
	}
}
