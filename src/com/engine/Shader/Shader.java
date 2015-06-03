package com.engine.Shader;

import com.engine.components.DirectionalLight;
import com.engine.components.Light;
import com.engine.components.PointLight;
import com.engine.components.SpotLight;
import com.engine.core.*;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.RenderingEngine;
import com.engine.rendering.ressource.ShaderResource;
import com.math.Matrix;
import com.math.Transform;
import com.math.Vector3D;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

public class Shader
{
	private static HashMap<String,ShaderResource> loadedModels = new HashMap<String,ShaderResource>();
	
	private ShaderResource resource;
	private String fileName;
	
	public Shader(String fileName)
	{
		this.fileName = fileName;
		ShaderResource oldRes = loadedModels.get(fileName);
		if(oldRes != null){
			resource = oldRes;
			resource.addReference();
		}else{
			resource = new ShaderResource();
			String vshadertxt = loadShader(fileName+".vs");
			String fshadertxt = loadShader(fileName+".fs");
			
			addVertexShader(vshadertxt);
			addFragmentShader(fshadertxt);
			
			addAllAttributes(vshadertxt);
			compileShader();
			
			addAllUniforms(vshadertxt);
			addAllUniforms(fshadertxt);
		}
	}
	
	public void bind()
	{
		glUseProgram(resource.getProgram());
	}

	public void updateUniforms(Transform transform, Material material,RenderingEngine engine){
		
		Matrix worldMatrix = transform.getTransformation();
		Matrix MVPMatrix = engine.getMainCamera().getViewProjection().mul(worldMatrix);
		
		for(int i=0; i<resource.getUniformNames().size();i++){
			String name = resource.getUniformNames().get(i);
			String type = resource.getUniformTypes().get(i);
			
			if(name.startsWith("T_")){
				switch(name){
				case "T_MVP": setUniform(name,MVPMatrix);
							  break;
				case "T_model": setUniform(name,worldMatrix);
							  break;
				default: throw new IllegalArgumentException(name +" not supported");	
				}
			} else if(name.startsWith("R_")){
				String unprefixed = name.substring(2);
				switch(type){
				case "sampler2D": int samplerSlot = engine.getSamplerSlot(unprefixed);
							      material.getTexture(unprefixed).bind(samplerSlot);
							      setUniform(name,samplerSlot);
								  break;
				case "vec3":	  setUniform(name,engine.getVector3D(unprefixed));
								  break;
				case "float":     setUniform(name,engine.getFloat(unprefixed));
								  break;
				case "DirectionalLight":     setUniform(name,(DirectionalLight)engine.getActiveLight());
				  				  			 break;
				case "PointLight":  	     setUniform(name,(PointLight)engine.getActiveLight());
		  			 						 break;
				case "SpotLight":  		     setUniform(name,(SpotLight)engine.getActiveLight());
		  			 						 break;
				default: throw new IllegalArgumentException(type +" not supported by Rendering Engine");	
				}
				
			} else if(name.startsWith("C_")){
				switch(name){
				case "C_camera":	setUniform(name,engine.getMainCamera().getTransform().getTransformedPos());
									break;
				default:throw new IllegalArgumentException(type +" not supported by Camera");	
				}
			} else{
				switch(type){
				case "vec3":	  setUniform(name,material.getVector3D(name));
								  break;
				case "float":     setUniform(name,material.getFloat(name));
								  break;
				default: throw new IllegalArgumentException(type +" not supported by Material");	
				}
				
			}
				
		}
		
	}
	
	private void addAllAttributes(String shadertxt){
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = shadertxt.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while(attributeStartLocation != -1)
		{
			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int end = shadertxt.indexOf(";", begin);

			String attributeLine = shadertxt.substring(begin, end);
			String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length());

			setAttribLocation(attributeName, attribNumber);
			attribNumber++;

			attributeStartLocation = shadertxt.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
		}
	}
	
	private class GLSLStruct
	{
		public String name;
		public String type;
	}

	private HashMap<String, ArrayList<GLSLStruct>> findUniformStructs(String shaderText)
	{
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

		final String STRUCT_KEYWORD = "struct";
		int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
		while(structStartLocation != -1)
		{
			int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
			int braceBegin = shaderText.indexOf("{", nameBegin);
			int braceEnd = shaderText.indexOf("}", braceBegin);

			String structName = shaderText.substring(nameBegin, braceBegin).trim();
			ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

			int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
			while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd)
			{
				int componentNameStart = componentSemicolonPos;

				while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
					componentNameStart--;

				int componentTypeEnd = componentNameStart - 1;
				int componentTypeStart = componentTypeEnd;

				while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
					componentTypeStart--;

				String componentName = shaderText.substring(componentNameStart, componentSemicolonPos);
				String componentType = shaderText.substring(componentTypeStart, componentTypeEnd);

				GLSLStruct glslStruct = new GLSLStruct();
				glslStruct.name = componentName;
				glslStruct.type = componentType;

				glslStructs.add(glslStruct);

				componentSemicolonPos = shaderText.indexOf(";", componentSemicolonPos + 1);
			}

			result.put(structName, glslStructs);

			structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
		}

		return result;
	}
	
	
	private void addAllUniforms(String shadertxt){
		
		HashMap<String, ArrayList<GLSLStruct>> structs =findUniformStructs(shadertxt);
		
		final String UNIFORM = "uniform";
		int uniformLocation = shadertxt.indexOf(UNIFORM);
		while(uniformLocation != -1){
			int begin = uniformLocation + UNIFORM.length()+1;
			int end = shadertxt.indexOf(";",begin);
			String uniformLine = shadertxt.substring(begin,end);
			
			int whiteSpace = uniformLine.indexOf(' ');
			
			String uniformName = uniformLine.substring(whiteSpace+1,uniformLine.length());
			String uniformType = uniformLine.substring(0,whiteSpace);
			
			
			resource.getUniformNames().add(uniformName);
			resource.getUniformTypes().add(uniformType);
			addUniform(uniformName, uniformType, structs);
			
			uniformLocation = shadertxt.indexOf("uniform",uniformLocation + UNIFORM.length());
		}
	}
	
	
	private void addUniform(String name,String type, HashMap<String,ArrayList<GLSLStruct>> struct){
		boolean addThis = true;
		ArrayList<GLSLStruct> components = (ArrayList<GLSLStruct>) struct.get(type);
		
		if(components != null){
			addThis = false;
			for(GLSLStruct stru : components){
				addUniform(name+"."+stru.name,stru.type,struct);
			}
		
		}
		
		if(!addThis){
			return;
		}
		
		int uniformLocation = glGetUniformLocation(resource.getProgram(), name);
		
		if(uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Error: Could not find uniform: " + name);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		resource.getUniforms().put(name, uniformLocation);	
	}
	
	private void addVertexShader(String text)
	{
		addProgram(text, GL_VERTEX_SHADER);
	}
	
	private void addGeometryShader(String text)
	{
		addProgram(text, GL_GEOMETRY_SHADER);
	}
	
	private void addFragmentShader(String text)
	{
		addProgram(text, GL_FRAGMENT_SHADER);
	}

	private void setAttribLocation(String attributeName, int location)
	{
		glBindAttribLocation(resource.getProgram(), location, attributeName);
	}

	private void compileShader()
	{
		glLinkProgram(resource.getProgram());
		
		if(glGetProgrami(resource.getProgram(), GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
		
		glValidateProgram(resource.getProgram());
		
		if(glGetProgrami(resource.getProgram(), GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(resource.getProgram(), 1024));
			System.exit(1);
		}
	}

	private void addProgram(String text, int type)
	{
		int shader = glCreateShader(type);
		
		if(shader == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location when adding shader");
			System.exit(1);
		}
		
		glShaderSource(shader, text);
		glCompileShader(shader);
		
		if(glGetShaderi(shader, GL_COMPILE_STATUS) == 0)
		{
			System.err.println(glGetShaderInfoLog(shader, 1024));
			System.exit(1);
		}
		
		glAttachShader(resource.getProgram(), shader);
	}
	
	private static String loadShader(String fileName)
	{
		final String INCLUDE ="#include";
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		
		try
		{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;
			
			while((line = shaderReader.readLine()) != null)
			{
				if(line.startsWith(INCLUDE)){
					shaderSource.append(loadShader(line.substring(INCLUDE.length()+2,line.length()-1)));
				}else{				
					shaderSource.append(line).append("\n");
				}
			}
			
			shaderReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		
		return shaderSource.toString();
	}
	
	public void setUniform(String uniformName, int value)
	{
		glUniform1i(resource.getUniforms().get(uniformName), value);
	}
	
	public void setUniform(String uniformName, float value)
	{
		glUniform1f(resource.getUniforms().get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3D value)
	{
		glUniform3f(resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix value)
	{
		glUniformMatrix4(resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
	}
	
	public void setUniform(String uniformName, Light baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniform(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniform(String uniformName, PointLight pointLight)
	{
		setUniform(uniformName + ".base", (Light) pointLight);
		setUniform(uniformName + ".atten.constant", pointLight.getConstant());
		setUniform(uniformName + ".atten.linear", pointLight.getLinear());
		setUniform(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniform(uniformName + ".range", pointLight.getRange());
	}

	public void setUniform(String uniformName, SpotLight spotLight)
	{
		setUniform(uniformName + ".pointLight", (PointLight)spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniform(uniformName + ".cutoff", spotLight.getCutoff());
	}
	

	public void setUniform(String uniformName, DirectionalLight directionalLight)
	{
		setUniform(uniformName + ".base", (Light)directionalLight);
		setUniform(uniformName + ".direction", directionalLight.getDirection());
	}
	
}
