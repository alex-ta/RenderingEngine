package com.engine.rendering.ressource;

import static org.lwjgl.opengl.GL15.*;

public class MeshResource {
	private int vbo;
	private int ibo;
	private int size;
	private int refCount;

	@Override
	protected void finalize(){
		glDeleteBuffers(vbo);
		glDeleteBuffers(ibo);
	}
	
	public void addReference(){
		refCount++;
	}
	
	public boolean removeReference(){
		refCount--;
		return refCount ==0;
	}
	
	
	public MeshResource(int size){
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
		this.refCount = 1;
	}
	
	public int getVbo() {
		return vbo;
	}
	
	public int getIbo() {
		return ibo;
	}

	public int getSize() {
		return size;
	}

}
