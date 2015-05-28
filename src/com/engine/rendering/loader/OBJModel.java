package com.engine.rendering.loader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.engine.core.Util;
import com.math.Vector2D;
import com.math.Vector3D;

public class OBJModel {
	private ArrayList<Vector3D>positions,normals;
	private ArrayList<Vector2D> texCoords;
	private ArrayList<OBJIndex> indices;
	private boolean hasCoords,hasNormals;

	public OBJModel(String file){
		
		positions = new ArrayList<Vector3D>();
		normals = new ArrayList<Vector3D>();
		texCoords = new ArrayList<Vector2D>();
		indices = new ArrayList<OBJIndex>();
		hasCoords = hasNormals = false;
		BufferedReader reader= null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String line;
			while((line = reader.readLine()) != null){
				String[] tokens = line.split(" ");
				tokens = Util.removeEmptyStrings(tokens);
				
				if(tokens.length ==0 || tokens[0].equals("#")){
					continue;
				}else if(tokens[0].equals("v")){
					positions.add(new Vector3D(Float.valueOf(tokens[1]),Float.valueOf(tokens[2]),Float.valueOf(tokens[3])));
				}else if(tokens[0].equals("vt")){
					texCoords.add(new Vector2D(Float.valueOf(tokens[1]),Float.valueOf(tokens[2])));
				}else if(tokens[0].equals("vn")){
					normals.add(new Vector3D(Float.valueOf(tokens[1]),Float.valueOf(tokens[2]),Float.valueOf(tokens[3])));
				}else if(tokens[0].equals("f")){
					for(int i =0; i<tokens.length-3; i++){
					indices.add(parseOBJ(tokens[1]));
					indices.add(parseOBJ(tokens[2]+i));
					indices.add(parseOBJ(tokens[3]+i));
					}
				}
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	
		public IndexModel toIndexedModel()
		{
			IndexModel result = new IndexModel();
			HashMap<Integer, Integer> indexMap = new HashMap<Integer, Integer>();
	
			int currentVertexIndex = 0;
			for(int i = 0; i < indices.size(); i++)
			{
				OBJIndex currentIndex = indices.get(i);
	
				Vector3D currentPosition = positions.get(currentIndex.getVertexIndex());
				Vector2D currentTexCoord;
				Vector3D currentNormal;
	
				if(hasCoords)
					currentTexCoord = texCoords.get(currentIndex.getTexCoordIndex());
				else
					currentTexCoord = new Vector2D(0,0);
	
				if(hasNormals)
					currentNormal = normals.get(currentIndex.getNormalIndex());
				else
					currentNormal = new Vector3D(0,0,0);
	
				int previousVertexIndex = -1;
	
				for(int j = 0; j < i; j++)
				{
					OBJIndex oldIndex = indices.get(j);
	
					if(currentIndex.getVertexIndex() == oldIndex.getVertexIndex()
						&& currentIndex.getTexCoordIndex() == oldIndex.getTexCoordIndex()
						&& currentIndex.getNormalIndex() == oldIndex.getNormalIndex())
					{
						previousVertexIndex = j;
						break;
					}
				}
	
				if(previousVertexIndex == -1)
				{
					indexMap.put(i, currentVertexIndex);
	
					result.setPosition(currentPosition);
					result.setTexCoord(currentTexCoord);
					result.setNormal(currentNormal);
					result.getIndices().add(currentVertexIndex);
					currentVertexIndex++;
				}
				else
					result.getIndices().add(indexMap.get(previousVertexIndex));
			}
	
			return result;
		}
	
	
	private OBJIndex parseOBJ(String token){
		String[] values = token.split("/");
		OBJIndex result = new OBJIndex();
		result.setVertexIndex(Integer.parseInt(values[0])-1);
		if(values.length >1){
			hasCoords = true;
			result.setTexCoordIndex(Integer.parseInt(values[1])-1);
				if(values.length >2){
					hasNormals = true;
					result.setNormalIndex(Integer.parseInt(values[2])-1);
				}
			}
		return result;
	}
	
}
