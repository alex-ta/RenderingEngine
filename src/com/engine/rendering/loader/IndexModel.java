package com.engine.rendering.loader;

import java.util.ArrayList;

import com.engine.rendering.objects.Vertex;
import com.math.Vector2D;
import com.math.Vector3D;

public class IndexModel {
	private ArrayList<Vector3D>positions,normals;
	private ArrayList<Vector2D> texCoords;
	private ArrayList<Integer> indices;

	public IndexModel(){
		positions = new ArrayList<Vector3D>();
		normals = new ArrayList<Vector3D>();
		texCoords = new ArrayList<Vector2D>();
		indices =new ArrayList<Integer>();
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
			
			normals.get(i0).set(normal);
			normals.get(i1).set(normal);
			normals.get(i2).set(normal);
		}
		
		for(int i = 0; i < normals.size(); i++)
			normals.get(i).set(normals.get(i).normalized());
	}
	
	
	public Vector3D getPosition(int index) {
		return positions.get(index);
	}

	public void setPosition(Vector3D position) {
		this.positions.add(position);
	}
	public Vector3D getNormal(int index) {
		return normals.get(index);
	}

	public void setNormal(Vector3D normal) {
		this.normals.add(normal);
	}
	public Vector2D getTexCoord(int index) {
		return texCoords.get(index);
	}

	public void setTexCoord(Vector2D texCoord) {
		this.texCoords.add(texCoord);
	}

	public Integer get(int index) {
		return indices.get(index);
	}

	public void set(Integer indice) {
		indices.add(indice);
	}
	
	public int length(){
		return positions.size();
	}
	
	public ArrayList<Integer> getIndices(){
		return indices;
	}
	

}

