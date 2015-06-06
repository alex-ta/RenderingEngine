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
	private static HashMap<String, ShaderResource> s_loadedShaders = new HashMap<String, ShaderResource>();

	private ShaderResource m_resource;
	private String         m_fileName;

	public Shader(String fileName)
	{
		this.m_fileName = fileName;

		ShaderResource oldResource = s_loadedShaders.get(fileName);

		if(oldResource != null)
		{
			m_resource = oldResource;
			m_resource.addReference();
		}
		else
		{
			m_resource = new ShaderResource();

			String vertexShaderText = LoadShader(fileName + ".vs");
			String fragmentShaderText = LoadShader(fileName + ".fs");

			AddVertexShader(vertexShaderText);
			AddFragmentShader(fragmentShaderText);

			AddAllAttributes(vertexShaderText);

			CompileShader();

			AddAllUniforms(vertexShaderText);
			AddAllUniforms(fragmentShaderText);
			
			s_loadedShaders.put(fileName, m_resource);
		}
	}

	@Override
	protected void finalize()
	{
		if(m_resource.removeReference() && !m_fileName.isEmpty())
		{
			s_loadedShaders.remove(m_fileName);
		}
	}

	public void Bind()
	{
		glUseProgram(m_resource.getProgram());
	}

	public void UpdateUniforms(Transform transform, Material material, RenderingEngine renderingEngine)
	{
		Matrix worldMatrix = transform.getTransformation();
		Matrix MVPMatrix = renderingEngine.getMainCamera().getViewProjection().mul(worldMatrix);

		for(int i = 0; i < m_resource.getUniformNames().size(); i++)
		{
			String uniformName = m_resource.getUniformNames().get(i);
			String uniformType = m_resource.getUniformTypes().get(i);

			if(uniformType.equals("sampler2D"))
			{
				int samplerSlot = renderingEngine.getSamplerSlot(uniformName);
				material.getTexture(uniformName).bind(samplerSlot);
				SetUniformi(uniformName, samplerSlot);
			}
			else if(uniformName.startsWith("T_"))
			{
				if(uniformName.equals("T_MVP"))
					SetUniform(uniformName, MVPMatrix);
				else if(uniformName.equals("T_model"))
					SetUniform(uniformName, worldMatrix);
				else
					throw new IllegalArgumentException(uniformName + " is not a valid component of Transform");
			}
			else if(uniformName.startsWith("R_"))
			{
				String unprefixedUniformName = uniformName.substring(2);
				if(uniformType.equals("vec3"))
					SetUniform(uniformName, renderingEngine.getVector3D(unprefixedUniformName));
				else if(uniformType.equals("float"))
					SetUniformf(uniformName, renderingEngine.getFloat(unprefixedUniformName));
				else if(uniformType.equals("DirectionalLight"))
					SetUniformDirectionalLight(uniformName, (DirectionalLight) renderingEngine.getActiveLight());
				else if(uniformType.equals("PointLight"))
					SetUniformPointLight(uniformName, (PointLight) renderingEngine.getActiveLight());
				else if(uniformType.equals("SpotLight"))
					SetUniformSpotLight(uniformName, (SpotLight) renderingEngine.getActiveLight());
				else
					renderingEngine.UpdateUniformStruct(transform, material, this, uniformName, uniformType);
			}
			else if(uniformName.startsWith("C_"))
			{
				if(uniformName.equals("C_eyePos"))
					SetUniform(uniformName, renderingEngine.getMainCamera().getTransform().getTransformedPos());
				else
					throw new IllegalArgumentException(uniformName + " is not a valid component of Camera");
			}
			else
			{
				if(uniformType.equals("vec3"))
					SetUniform(uniformName, material.getVector3D(uniformName));
				else if(uniformType.equals("float"))
					SetUniformf(uniformName, material.getFloat(uniformName));
				else
					throw new IllegalArgumentException(uniformType + " is not a supported type in Material");
			}
		}
	}

	private void AddAllAttributes(String shaderText)
	{
		final String ATTRIBUTE_KEYWORD = "attribute";
		int attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD);
		int attribNumber = 0;
		while(attributeStartLocation != -1)
		{
			if(!(attributeStartLocation != 0
				&& (Character.isWhitespace(shaderText.charAt(attributeStartLocation - 1)) || shaderText.charAt(attributeStartLocation - 1) == ';')
				&& Character.isWhitespace(shaderText.charAt(attributeStartLocation + ATTRIBUTE_KEYWORD.length())))) {
					attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
					continue;
					
			}

			int begin = attributeStartLocation + ATTRIBUTE_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String attributeLine = shaderText.substring(begin, end).trim();
			String attributeName = attributeLine.substring(attributeLine.indexOf(' ') + 1, attributeLine.length()).trim();

			SetAttribLocation(attributeName, attribNumber);
			attribNumber++;

			attributeStartLocation = shaderText.indexOf(ATTRIBUTE_KEYWORD, attributeStartLocation + ATTRIBUTE_KEYWORD.length());
		}
	}

	private class GLSLStruct
	{
		public String name;
		public String type;
	}

	private HashMap<String, ArrayList<GLSLStruct>> FindUniformStructs(String shaderText)
	{
		HashMap<String, ArrayList<GLSLStruct>> result = new HashMap<String, ArrayList<GLSLStruct>>();

		final String STRUCT_KEYWORD = "struct";
		int structStartLocation = shaderText.indexOf(STRUCT_KEYWORD);
		while(structStartLocation != -1)
		{
			if(!(structStartLocation != 0
					&& (Character.isWhitespace(shaderText.charAt(structStartLocation - 1)) || shaderText.charAt(structStartLocation - 1) == ';')
					&& Character.isWhitespace(shaderText.charAt(structStartLocation + STRUCT_KEYWORD.length())))) {
				structStartLocation = shaderText.indexOf(STRUCT_KEYWORD, structStartLocation + STRUCT_KEYWORD.length());
				continue;
			}

			int nameBegin = structStartLocation + STRUCT_KEYWORD.length() + 1;
			int braceBegin = shaderText.indexOf("{", nameBegin);
			int braceEnd = shaderText.indexOf("}", braceBegin);

			String structName = shaderText.substring(nameBegin, braceBegin).trim();
			ArrayList<GLSLStruct> glslStructs = new ArrayList<GLSLStruct>();

			int componentSemicolonPos = shaderText.indexOf(";", braceBegin);
			while(componentSemicolonPos != -1 && componentSemicolonPos < braceEnd)
			{
				int componentNameEnd = componentSemicolonPos + 1;

				while(Character.isWhitespace(shaderText.charAt(componentNameEnd - 1)) || shaderText.charAt(componentNameEnd - 1) == ';')
					componentNameEnd--;

				int componentNameStart = componentSemicolonPos;

				while(!Character.isWhitespace(shaderText.charAt(componentNameStart - 1)))
					componentNameStart--;

				int componentTypeEnd = componentNameStart;

				while(Character.isWhitespace(shaderText.charAt(componentTypeEnd - 1)))
					componentTypeEnd--;

				int componentTypeStart = componentTypeEnd;

				while(!Character.isWhitespace(shaderText.charAt(componentTypeStart - 1)))
					componentTypeStart--;

				String componentName = shaderText.substring(componentNameStart, componentNameEnd);
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

	private void AddAllUniforms(String shaderText)
	{
		HashMap<String, ArrayList<GLSLStruct>> structs = FindUniformStructs(shaderText);

		final String UNIFORM_KEYWORD = "uniform";
		int uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD);
		while(uniformStartLocation != -1)
		{
			if(!(uniformStartLocation != 0
					&& (Character.isWhitespace(shaderText.charAt(uniformStartLocation - 1)) || shaderText.charAt(uniformStartLocation - 1) == ';')
					&& Character.isWhitespace(shaderText.charAt(uniformStartLocation + UNIFORM_KEYWORD.length())))) {
				uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
				continue;
			}

			int begin = uniformStartLocation + UNIFORM_KEYWORD.length() + 1;
			int end = shaderText.indexOf(";", begin);

			String uniformLine = shaderText.substring(begin, end).trim();

			int whiteSpacePos = uniformLine.indexOf(' ');
			String uniformName = uniformLine.substring(whiteSpacePos + 1, uniformLine.length()).trim();
			String uniformType = uniformLine.substring(0, whiteSpacePos).trim();

			m_resource.getUniformNames().add(uniformName);
			m_resource.getUniformTypes().add(uniformType);
			AddUniform(uniformName, uniformType, structs);

			uniformStartLocation = shaderText.indexOf(UNIFORM_KEYWORD, uniformStartLocation + UNIFORM_KEYWORD.length());
		}
	}

	private void AddUniform(String uniformName, String uniformType, HashMap<String, ArrayList<GLSLStruct>> structs)
	{
		boolean addThis = true;
		ArrayList<GLSLStruct> structComponents = structs.get(uniformType);

		if(structComponents != null)
		{
			addThis = false;
			for(GLSLStruct struct : structComponents)
			{
				AddUniform(uniformName + "." + struct.name, struct.type, structs);
			}
		}

		if(!addThis)
			return;

		int uniformLocation = glGetUniformLocation(m_resource.getProgram(), uniformName);

		if(uniformLocation == 0xFFFFFFFF)
		{
			System.err.println("Error: Could not find uniform: " + uniformName);
			new Exception().printStackTrace();
			System.exit(1);
		}

		m_resource.getUniforms().put(uniformName, uniformLocation);
	}

	private void AddVertexShader(String text)
	{
		AddProgram(text, GL_VERTEX_SHADER);
	}

	private void AddGeometryShader(String text)
	{
		AddProgram(text, GL_GEOMETRY_SHADER);
	}

	private void AddFragmentShader(String text)
	{
		AddProgram(text, GL_FRAGMENT_SHADER);
	}

	private void SetAttribLocation(String attributeName, int location)
	{
		glBindAttribLocation(m_resource.getProgram(), location, attributeName);
	}

	private void CompileShader()
	{
		glLinkProgram(m_resource.getProgram());
		
		if(glGetProgrami(m_resource.getProgram(), GL_LINK_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(m_resource.getProgram(), 1024));
			System.exit(1);
		}
		
		glValidateProgram(m_resource.getProgram());
		
		if(glGetProgrami(m_resource.getProgram(), GL_VALIDATE_STATUS) == 0)
		{
			System.err.println(glGetProgramInfoLog(m_resource.getProgram(), 1024));
			System.exit(1);
		}
	}

	private void AddProgram(String text, int type)
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
		
		glAttachShader(m_resource.getProgram(), shader);
	}

	private static String LoadShader(String fileName)
	{
		StringBuilder shaderSource = new StringBuilder();
		BufferedReader shaderReader = null;
		final String INCLUDE_DIRECTIVE = "#include";

		try
		{
			shaderReader = new BufferedReader(new FileReader("./res/shaders/" + fileName));
			String line;

			while((line = shaderReader.readLine()) != null)
			{
				if(line.startsWith(INCLUDE_DIRECTIVE))
				{
					shaderSource.append(LoadShader(line.substring(INCLUDE_DIRECTIVE.length() + 2, line.length() - 1)));
				}
				else
					shaderSource.append(line).append("\n");
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
	
	public void SetUniformi(String uniformName, int value)
	{
		glUniform1i(m_resource.getUniforms().get(uniformName), value);
	}
	
	public void SetUniformf(String uniformName, float value)
	{
		glUniform1f(m_resource.getUniforms().get(uniformName), value);
	}
	
	public void SetUniform(String uniformName, Vector3D value)
	{
		glUniform3f(m_resource.getUniforms().get(uniformName), value.getX(), value.getY(), value.getZ());
	}
	
	public void SetUniform(String uniformName, Matrix value)
	{
		glUniformMatrix4(m_resource.getUniforms().get(uniformName), true, Util.createFlippedBuffer(value));
	}

	public void SetUniformBaseLight(String uniformName, Light baseLight)
	{
		SetUniform(uniformName + ".color", baseLight.getColor());
		SetUniformf(uniformName + ".intensity", baseLight.getIntensity());
	}

	public void SetUniformDirectionalLight(String uniformName, DirectionalLight directionalLight)
	{
		SetUniformBaseLight(uniformName + ".base", directionalLight);
		SetUniform(uniformName + ".direction", directionalLight.getDirection());
	}

	public void SetUniformPointLight(String uniformName, PointLight pointLight)
	{
		SetUniformBaseLight(uniformName + ".base", pointLight);
		SetUniformf(uniformName + ".atten.constant", pointLight.getAttenuation().getConstant());
		SetUniformf(uniformName + ".atten.linear", pointLight.getAttenuation().getLinear());
		SetUniformf(uniformName + ".atten.exponent", pointLight.getAttenuation().getExponent());
		SetUniform(uniformName + ".position", pointLight.getTransform().getTransformedPos());
		SetUniformf(uniformName + ".range", pointLight.getRange());
	}

	public void SetUniformSpotLight(String uniformName, SpotLight spotLight)
	{
		SetUniformPointLight(uniformName + ".pointLight", spotLight);
		SetUniform(uniformName + ".direction", spotLight.getDirection());
		SetUniformf(uniformName + ".cutoff", spotLight.getCutoff());
	}
}
