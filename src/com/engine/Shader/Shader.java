package com.engine.Shader;

import com.engine.components.Light;
import com.engine.components.PointLight;
import com.engine.components.SpotLight;
import com.engine.core.*;
import com.engine.rendering.objects.Material;
import com.engine.rendering.objects.RenderingEngine;
import com.math.Matrix;
import com.math.Transform;
import com.math.Vector3D;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Shader
{
	private int program;
	private HashMap<String, Integer> uniforms;
	
	public Shader(String fileName)
	{
		program = glCreateProgram();
		uniforms = new HashMap<String, Integer>();
		
		if(program == 0)
		{
			System.err.println("Shader creation failed: Could not find valid memory location in constructor");
			System.exit(1);
		}
		
		String vshadertxt = loadShader(fileName+".vs");
		String fshadertxt = loadShader(fileName+".fs");
		
		addVertexShader(vshadertxt);
		addFragmentShader(fshadertxt);
		
		addAllAttributes(vshadertxt);
		compileShader();
		
		addAllUniforms(vshadertxt);
		addAllUniforms(fshadertxt);
	}
	
	public void bind()
	{
		glUseProgram(program);
	}

	public abstract void updateUniforms(Transform transform, Material material,RenderingEngine engine);
	
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
			
			
			addUniformWithCheck(uniformName, uniformType, structs);
			
			uniformLocation = shadertxt.indexOf("uniform",uniformLocation + UNIFORM.length());
		}
	}
	
	
	private void addUniformWithCheck(String name,String type, HashMap<String,ArrayList<GLSLStruct>> struct){
		boolean addThis = true;
		ArrayList<GLSLStruct> components = (ArrayList<GLSLStruct>) struct.get(type);
		
		if(components != null){
			addThis = false;
			for(GLSLStruct stru : components){
				addUniformWithCheck(name+"."+stru.name,stru.type,struct);
			}
		
		}
		
		if(addThis){
			addUniform(name);
		}
		
	}
	
	private void addUniform(String uniform)
	{
		int uniformLocation = glGetUniformLocation(program, uniform);
		
		if(uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Error: Could not find uniform: " + uniform);
			new Exception().printStackTrace();
			System.exit(1);
		}
		
		uniforms.put(uniform, uniformLocation);
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
		glBindAttribLocation(program, location, attributeName);
	}

	private void compileShader()
	{
		glLinkProgram(program);
		
		if(glGetProgrami(program, GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
			System.exit(1);
		}
		
		glValidateProgram(program);
		
		if(glGetProgrami(program, GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(program, 1024));
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
		
		glAttachShader(program, shader);
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
	
	public void setUniformi(String uniformName, int value)
	{
		glUniform1i(uniforms.get(uniformName), value);
	}
	
	public void setUniformf(String uniformName, float value)
	{
		glUniform1f(uniforms.get(uniformName), value);
	}
	
	public void setUniform(String uniformName, Vector3D value)
	{
		glUniform3f(uniforms.get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void setUniform(String uniformName, Matrix value)
	{
		glUniformMatrix4(uniforms.get(uniformName), true, Util.createFlippedBuffer(value));
	}
	
	public void setUniformBaseLight(String uniformName, Light baseLight)
	{
		setUniform(uniformName + ".color", baseLight.getColor());
		setUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void setUniformPointLight(String uniformName, PointLight pointLight)
	{
		setUniformBaseLight(uniformName + ".base", pointLight);
		setUniformf(uniformName + ".atten.constant", pointLight.getConstant());
		setUniformf(uniformName + ".atten.linear", pointLight.getLinear());
		setUniformf(uniformName + ".atten.exponent", pointLight.getExponent());
		setUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		setUniformf(uniformName + ".range", pointLight.getRange());
	}

	public void setUniformSpotLight(String uniformName, SpotLight spotLight)
	{
		setUniformPointLight(uniformName + ".pointLight", spotLight);
		setUniform(uniformName + ".direction", spotLight.getDirection());
		setUniformf(uniformName + ".cutoff", spotLight.getCutoff());
	}
	
	
}
