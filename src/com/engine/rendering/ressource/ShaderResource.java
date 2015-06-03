package com.engine.rendering.ressource;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.*;

import java.util.ArrayList;
import java.util.HashMap;

public class ShaderResource {
	private int program;
	private int refCount;
	private HashMap<String, Integer> uniforms;
	private ArrayList<String> uniformNames;
	private ArrayList<String> uniformTypes;

	@Override
	protected void finalize(){
		glDeleteBuffers(program);
	}
	
	public void addReference(){
		refCount++;
	}
	
	public boolean removeReference(){
		refCount--;
		return refCount ==0;
	}
	
	
	public ShaderResource(){
		uniforms = new HashMap<String, Integer>();
		uniformNames = new ArrayList<String>();
		uniformTypes = new ArrayList<String>();
		this.program = glCreateProgram();
		if(program == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
		this.refCount = 1;
	}
	
	public int getProgram() {
		return program;
	}

	public HashMap<String, Integer> getUniforms() {
		return uniforms;
	}

	public void setUniforms(HashMap<String, Integer> uniforms) {
		this.uniforms = uniforms;
	}

	public ArrayList<String> getUniformNames() {
		return uniformNames;
	}

	public void setUniformNames(ArrayList<String> uniformNames) {
		this.uniformNames = uniformNames;
	}

	public ArrayList<String> getUniformTypes() {
		return uniformTypes;
	}

	public void setUniformTypes(ArrayList<String> uniformTypes) {
		this.uniformTypes = uniformTypes;
	}
}
