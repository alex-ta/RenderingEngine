package com.engine.rendering.loader;


import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.engine.core.Util;
import com.math.*;

public class OBJModel
{
	private ArrayList<Vector3D> positions;
	private ArrayList<Vector2D> texCoords;
	private ArrayList<Vector3D> normals;
	private ArrayList<OBJIndex> indices;
	private boolean hasTexCoords;
	private boolean hasNormals;

	public OBJModel(String fileName)
	{
		positions = new ArrayList<Vector3D>();
		texCoords = new ArrayList<Vector2D>();
		normals = new ArrayList<Vector3D>();
		indices = new ArrayList<OBJIndex>();
		hasTexCoords = false;
		hasNormals = false;

		BufferedReader meshReader = null;

		try
		{
			meshReader = new BufferedReader(new FileReader(fileName));
			String line;

			while((line = meshReader.readLine()) != null)
			{
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);

				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
				{
					positions.add(new Vector3D(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("vt"))
				{
					texCoords.add(new Vector2D(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2])));
				}
				else if(tokens[0].equals("vn"))
				{
					normals.add(new Vector3D(Float.valueOf(tokens[1]),
							Float.valueOf(tokens[2]),
							Float.valueOf(tokens[3])));
				}
				else if(tokens[0].equals("f"))
				{
					for(int i = 0; i < tokens.length - 3; i++)
					{
						indices.add(parseOBJIndex(tokens[1]));
						indices.add(parseOBJIndex(tokens[2 + i]));
						indices.add(parseOBJIndex(tokens[3 + i]));
					}
				}
			}

			meshReader.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
	}

	public IndexedModel toIndexedModel()
	{
		IndexedModel result = new IndexedModel();
		HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();

		int currentVertexIndex = 0;
		for(int i = 0; i < indices.size(); i++)
		{
			OBJIndex currentIndex = indices.get(i);

			Vector3D currentPosition = positions.get(currentIndex.vertexIndex);
			Vector2D currentTexCoord;
			Vector3D currentNormal;

			if(hasTexCoords)
				currentTexCoord = texCoords.get(currentIndex.texCoordIndex);
			else
				currentTexCoord = new Vector2D(0,0);

			if(hasNormals)
				currentNormal = normals.get(currentIndex.normalIndex);
			else
				currentNormal = new Vector3D(0,0,0);

			int previousVertexIndex = -1;

			for(int j = 0; j < i; j++)
			{
				OBJIndex oldIndex = indices.get(j);

				if(currentIndex.vertexIndex == oldIndex.vertexIndex
					&& currentIndex.texCoordIndex == oldIndex.texCoordIndex
					&& currentIndex.normalIndex == oldIndex.normalIndex)
				{
					previousVertexIndex = j;
					break;
				}
			}

			if(previousVertexIndex == -1)
			{
				indexMap.put(i, currentVertexIndex);

				result.getPositions().add(currentPosition);
				result.getTexCoords().add(currentTexCoord);
				result.getNormals().add(currentNormal);
				result.getIndices().add(currentVertexIndex);
				currentVertexIndex++;
			}
			else
				result.getIndices().add(indexMap.get(previousVertexIndex));
		}

		return result;
	}

	private OBJIndex parseOBJIndex(String token)
	{
		String[] values = token.split("/");

		OBJIndex result = new OBJIndex();
		result.vertexIndex = Integer.parseInt(values[0]) - 1;

		if(values.length > 1)
		{
			hasTexCoords = true;
			result.texCoordIndex = Integer.parseInt(values[1]) - 1;

			if(values.length > 2)
			{
				hasNormals = true;
				result.normalIndex = Integer.parseInt(values[2]) - 1;
			}
		}

		return result;
	}

//	public ArrayList<Vector3f> getPositions() { return positions; }
//	public ArrayList<Vector2f> getTexCoords() { return texCoords; }
//	public ArrayList<Vector3f> getNormals() { return normals; }
//	public ArrayList<OBJIndex> getIndices() { return indices; }
}
