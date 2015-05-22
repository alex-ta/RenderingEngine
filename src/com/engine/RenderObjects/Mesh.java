package com.engine.RenderObjects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.engine.Math.Vector3D;

public class Mesh {
	
	private int vbo;
	private int size;
	private int ibo;
	
	public Mesh (String file){
		initMeshGL();
		loadMesh(file);
	}
	
	public Mesh(Vertex []vertices,int...indices){
		this(vertices,indices,false);
	}
	public Mesh(Vertex [] vertices,int[] indices, boolean normals){
		initMeshGL();
		addVertices(vertices,indices,normals);
	}
	private void initMeshGL(){
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		size = 0;
	}
	
	
	protected void addVertices (Vertex[] vertecies,int[] indices,boolean calcNormals){
		
		if(calcNormals)
			calcNormals(vertecies,indices);
		size = indices.length;
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		glBufferData(GL_ARRAY_BUFFER,Util.createFlippedBuffer(vertecies),GL_STATIC_DRAW);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER,Util.createFlippedBuffer(indices),GL_STATIC_DRAW);
	}	
	
	
	public void draw(){
		
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ARRAY_BUFFER,vbo);
		// 4 => float = 4 Byte
		glVertexAttribPointer(0,3,GL_FLOAT,false,Vertex.SIZE*4,0);
		glVertexAttribPointer(1,2,GL_FLOAT,false,Vertex.SIZE*4,12); // 12 bytes offset for vertex
		glVertexAttribPointer(2,3,GL_FLOAT,false,Vertex.SIZE*4,20);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER,ibo);
		glDrawElements(GL_TRIANGLES,size,GL_UNSIGNED_INT,0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
	}
	
	private void calcNormals(Vertex[] vertecies,int[] indices){
		for(int i = 0 ; i<indices.length ; i+=3){
			int i0 = indices[i];
			int i1 = indices[i+1];
			int i2 = indices[i+2];
			
			Vector3D v0 = vertecies[i1].getPos().sub(vertecies[i0].getPos());
			Vector3D v1 = vertecies[i2].getPos().sub(vertecies[i0].getPos());
			Vector3D normal = v0.cross(v1).normalize();
			
			vertecies[i0].setNormal(vertecies[i0].getNormal().add(normal));
			vertecies[i1].setNormal(vertecies[i1].getNormal().add(normal));
			vertecies[i2].setNormal(vertecies[i2].getNormal().add(normal));
		}

		for(Vertex v: vertecies){
			v.setNormal(v.getNormal().normalize()); // nur normalize ?
		}
		
	}
	
	protected Mesh loadMesh(String fileName){
		
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length-1];
		if(!ext.equals("obj")){
			System.err.println("Format not supported"+ext);
			System.exit(1);
		}
		
		ArrayList<Vertex> vertecies = new ArrayList<Vertex>();
		ArrayList<Integer> indecies = new ArrayList<Integer>();
		BufferedReader buff = null;
		
		try{
			buff = new BufferedReader(new FileReader("./res/models/"+fileName));
			String line;
			while((line = buff.readLine()) != null){
				String[] tokens = line.split(" ");
				tokens = removeEmpty(tokens);
				
				if(tokens.length == 0 || tokens[0].equals("#"))
					continue;
				else if(tokens[0].equals("v"))
					vertecies.add(new Vertex(Float.valueOf(tokens[1]),Float.valueOf(tokens[2]),Float.valueOf(tokens[3])));
				else if(tokens[0].equals("f")){
					indecies.add(Integer.parseInt(tokens[1].split("/")[0])-1);
					indecies.add(Integer.parseInt(tokens[2].split("/")[0])-1);
					indecies.add(Integer.parseInt(tokens[3].split("/")[0])-1);
					
					if(tokens.length> 4){
						indecies.add(Integer.parseInt(tokens[1].split("/")[0])-1);
						indecies.add(Integer.parseInt(tokens[3].split("/")[0])-1);
						indecies.add(Integer.parseInt(tokens[4].split("/")[0])-1);			
					}
					
				}
			}
			
			Vertex[] vertexData = new Vertex[vertecies.size()];
			vertecies.toArray(vertexData);
			Integer[] indexData = new Integer[indecies.size()];
			indecies.toArray(indexData);
			addVertices(vertexData,Util.toIntArray(indexData),true);
			
			} catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			} finally{
				try {
					buff.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		return null;
	}

	public static String[] removeEmpty (String... data)	{
		ArrayList<String> list = new ArrayList<String>();
		for(int i =0; i< data.length; i++)
			if(!data[i].equals(""))
				list.add(data[i]);
		String[] res = new String[list.size()];
		list.toArray(res);
		return res;
		}

}
