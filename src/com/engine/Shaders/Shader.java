package com.engine.Shaders;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import com.engine.Components.DirectionalLight;
import com.engine.Components.Light;
import com.engine.Components.PointLight;
import com.engine.Components.SpotLight;
import com.engine.Core.RenderingEngine;
import com.engine.Desing.Material;
import com.engine.Math.Matrix;
import com.engine.Math.Transform;
import com.engine.Math.Vector3D;
import com.engine.RenderObjects.Util;

public abstract class Shader {
	
	private RenderingEngine Rengine;
	private int program;
	/**GLSL Pointer array*/
	private HashMap<String, Integer> uniforms;
	
	
	public Shader(){
		program = glCreateProgram();
		uniforms = new HashMap<String,Integer>();
		
		if(program == 0){
			System.out.println("Shader could not find a valid Memory location");
			System.exit(1);
		}
	}
	
	public void addVertexShader(String text){
		System.out.println("Add Vertex Shader");
		addProgram(text,GL_VERTEX_SHADER);
	}
	public void addGeometryShader(String text){
		System.out.println("Add Geometry Shader");
		addProgram(text,GL_GEOMETRY_SHADER);
	}
	public void addFragmentShader(String text){
		System.out.println("Add Fragment Shader");
		addProgram(text,GL_FRAGMENT_SHADER);
	}
	
	public void bind(){		
		glUseProgram(program);
	}
	
	public void addAttr(String name, int value){
		glBindAttribLocation(program,value,name);
	}

	public void compileShader(){
		glLinkProgram(program);
		if(glGetShader(program,GL_LINK_STATUS) == 0){
			System.out.println("Shader could not find a valid Memory location");
			System.exit(1);
		}
		glValidateProgram(program);
		if(glGetShader(program,GL_VALIDATE_STATUS) == 0){
			System.out.println("Shader could not find a valid Memory location");
			System.exit(1);
		}
	}
	
	
	private void addProgram(String text,int type){
		int shader = glCreateShader(type);
		if(shader == 0){
			System.out.println("Shader could not been added");
			System.exit(1);
		}
		glShaderSource(shader,text);
		glCompileShader(shader);
		
		if(glGetShader(shader,GL_COMPILE_STATUS) == 0){
			System.out.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(program,shader);
	}
	
	/**Adding new String var to GLSL*/
	public void addUniform(String uniform){
		
		int uniformLocation = glGetUniformLocation(program, uniform);
		
		if(uniformLocation == 0xFFFFFFFF){
			System.out.println("Could not find Uniform "+uniform);
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
	}
	
	public abstract void updateUniforms(Transform transform,Material material);
	
	/**Setting the Values to the Uniform Pointer address*/
	public void setUniform(String uniName, int value){
		glUniform1i(uniforms.get(uniName),value);
	}
	public void setUniform(String uniName, float value){
		glUniform1f(uniforms.get(uniName),value);
	}
	public void setUniform(String uniName, Vector3D value){
		glUniform3f(uniforms.get(uniName),value.x,value.y,value.z);
	}
	public void setUniform(String uniName, Matrix value){
		glUniformMatrix4(uniforms.get(uniName),true ,Util.createFlippedBuffer(value));
	}
	public void setUniform(String uniformName, Light normalLight)
	{
		setUniform(uniformName + ".color", normalLight.getColor());
		setUniform(uniformName + ".intensity", normalLight.getIntensity());
	}
	public void setUniform(String uniformName, PointLight pointlight)
	{
		setUniform(uniformName + ".base", (Light) pointlight);
		setUniform(uniformName + ".position",pointlight.getParent().getTransform().getTranslation());
		setUniform(uniformName+".atten.constant",pointlight.getConstant());
		setUniform(uniformName+".atten.linear",pointlight.getLinear());
		setUniform(uniformName+".atten.exponent",pointlight.getExponent());
		setUniform(uniformName+".range",pointlight.getRange());
	}
	public void setUniform(String uniformName, SpotLight spotLight){
		setUniform(uniformName+".pointLight",(PointLight)spotLight);
		setUniform(uniformName+".cutoff",spotLight.getCutoff());
		setUniform(uniformName+".direction",spotLight.getDircetion());
	}
	public void setUniform(String uniformName, DirectionalLight directionalLight)
	{
		setUniform(uniformName + ".base", (Light)directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}

	protected static String loadShaderFile(String fileName){
		StringBuilder src = new StringBuilder();
		BufferedReader buff = null;
		try{
		buff = new BufferedReader(new FileReader("./res/shaders/"+fileName));
		String line;
		while((line = buff.readLine()) != null){
			src.append(line).append("\n");
			}
		} catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		} finally{
			try {
				buff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return src.toString();
	}
	
	public void setRenderingEngine(RenderingEngine Rengine){
		this.Rengine = Rengine;
	}
	public RenderingEngine getRenderingEngine(){
		return this.Rengine;
	}
}