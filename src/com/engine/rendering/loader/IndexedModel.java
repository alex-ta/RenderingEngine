package com.engine.rendering.loader;

import com.math.*;
import java.util.ArrayList;

public class IndexedModel
{
	private ArrayList<Vector3D> positions;
	private ArrayList<Vector2D> texCoords;
	private ArrayList<Vector3D> normals;
	private ArrayList<Integer> indices;

	public IndexedModel()
	{
		positions = new ArrayList<Vector3D>();
		texCoords = new ArrayList<Vector2D>();
		normals = new ArrayList<Vector3D>();
		indices = new ArrayList<Integer>();
	}

	public void calcNormals()
	{
		for(int i = 0; i < indices.size(); i += 3)
		{
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);

			Vector3D v1 = positions.get(i1).sub(positions.get(i0));
			Vector3D v2 = positions.get(i2).sub(positions.get(i0));

			Vector3D normal = v1.cross(v2).normalized();

			normals.get(i0).set(normals.get(i0).add(normal));
			normals.get(i1).set(normals.get(i1).add(normal));
			normals.get(i2).set(normals.get(i2).add(normal));
		}

		for(int i = 0; i < normals.size(); i++)
			normals.get(i).set(normals.get(i).normalized());
	}

	public ArrayList<Vector3D> getPositions() { return positions; }
	public ArrayList<Vector2D> getTexCoords() { return texCoords; }
	public ArrayList<Vector3D> getNormals() { return normals; }
	public ArrayList<Integer> getIndices() { return indices; }
}
