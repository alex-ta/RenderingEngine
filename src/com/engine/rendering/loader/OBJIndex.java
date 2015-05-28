package com.engine.rendering.loader;

public class OBJIndex {

	private int vertexIndex,texCoordIndex,normalIndex;

	public int getVertexIndex() {
		return vertexIndex;
	}

	public void setVertexIndex(int vertexIndex) {
		this.vertexIndex = vertexIndex;
	}

	public int getTexCoordIndex() {
		return texCoordIndex;
	}

	public void setTexCoordIndex(int tecxCoordIndex) {
		this.texCoordIndex = tecxCoordIndex;
	}

	public int getNormalIndex() {
		return normalIndex;
	}

	public void setNormalIndex(int normalIndex) {
		this.normalIndex = normalIndex;
	}
}
