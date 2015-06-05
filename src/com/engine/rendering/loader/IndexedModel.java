package com.engine.rendering.loader;

import com.math.*;
import java.util.ArrayList;

public class IndexedModel
{
	private ArrayList<Vector3D> positions;
	private ArrayList<Vector2D> texCoords;
	private ArrayList<Vector3D> normals;
	private ArrayList<Vector3D> tangents;
	private ArrayList<Integer> indices;

	public IndexedModel()
	{
		positions = new ArrayList<Vector3D>();
		texCoords = new ArrayList<Vector2D>();
		normals = new ArrayList<Vector3D>();
		tangents = new ArrayList<Vector3D>();
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
	
	public void calcTangents()
		{
			for(int i = 0; i < indices.size(); i += 3)
			{
				int i0 = indices.get(i);
				int i1 = indices.get(i + 1);
				int i2 = indices.get(i + 2);
	
				Vector3D edge1 = positions.get(i1).sub(positions.get(i0));
				Vector3D edge2 = positions.get(i2).sub(positions.get(i0));
	
				float deltaU1 = texCoords.get(i1).getX() - texCoords.get(i0).getX();
				float deltaV1 = texCoords.get(i1).getY() - texCoords.get(i0).getY();
				float deltaU2 = texCoords.get(i2).getX() - texCoords.get(i0).getX();
				float deltaV2 = texCoords.get(i2).getY() - texCoords.get(i0).getY();
	
				float f = 1.0f/(deltaU1*deltaV2 - deltaU2*deltaV1);
	
				Vector3D tangent = new Vector3D(0,0,0);
				tangent.setX(f * (deltaV2 * edge1.getX() - deltaV1 * edge2.getX()));
				tangent.setY(f * (deltaV2 * edge1.getY() - deltaV1 * edge2.getY()));
				tangent.setZ(f * (deltaV2 * edge1.getZ() - deltaV1 * edge2.getZ()));
	
				tangents.get(i0).set(tangents.get(i0).add(tangent));
				tangents.get(i1).set(tangents.get(i1).add(tangent));
				tangents.get(i2).set(tangents.get(i2).add(tangent));
			}
	
			for(int i = 0; i < tangents.size(); i++)
				tangents.get(i).set(tangents.get(i).normalized());
	}

	public ArrayList<Vector3D> getPositions() { return positions; }
	public ArrayList<Vector2D> getTexCoords() { return texCoords; }
	public ArrayList<Vector3D> getNormals() { return normals; }
	public ArrayList<Integer> getIndices() { return indices; }
	public ArrayList<Vector3D> getTangents() { return tangents; }
}
