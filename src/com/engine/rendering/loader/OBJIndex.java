package com.engine.rendering.loader;


public class OBJIndex
{
	public int vertexIndex;
	public int texCoordIndex;
	public int normalIndex;
	
	@Override
	public boolean equals(Object obj){
		OBJIndex index = (OBJIndex) obj;
		return vertexIndex == index.vertexIndex
				&& texCoordIndex == index.texCoordIndex
				&& normalIndex == index.normalIndex;
	}
	
	@Override 
	public int hashCode(){
	final int BASE = 17;
	final int MULTI = 31;
	
		int result = BASE;
		result *= MULTI* result+vertexIndex;
		result *= MULTI* result+texCoordIndex;
		result *= MULTI* result+normalIndex;
		return result;
	}
	
}
