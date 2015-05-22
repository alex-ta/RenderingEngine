package com.engine.RenderObjects;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import com.engine.Math.Matrix;

public class Util {

	public static FloatBuffer createFlippedBuffer(Vertex... vertecies){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(vertecies.length*Vertex.SIZE);
		for(int i =0; i< vertecies.length;i++){
			buffer.put(vertecies[i].getPos().x);
			buffer.put(vertecies[i].getPos().y);
			buffer.put(vertecies[i].getPos().z);
			buffer.put(vertecies[i].getTexturepos().x);
			buffer.put(vertecies[i].getTexturepos().y);
			buffer.put(vertecies[i].getNormal().x);
			buffer.put(vertecies[i].getNormal().y);
			buffer.put(vertecies[i].getNormal().z);
		}
		
		buffer.flip();
		return buffer;
	}
	
	public static IntBuffer createFlippedBuffer(int... indices) {
		IntBuffer buff = BufferUtils.createIntBuffer(indices.length);
		buff.put(indices);
		buff.flip();
		return buff;
	}
	
	public static FloatBuffer createFlippedBuffer(Matrix value){
		FloatBuffer buffer = BufferUtils.createFloatBuffer((value.m.length*value.m[0].length));
		
		for(int i=0; i<value.m.length;i++){
			for(int j=0; j<value.m[0].length; j++){
				buffer.put(value.get(i, j));
			}
		}
		
		buffer.flip();
		return buffer;
	}

	public static int[] toIntArray(Integer... indecies) {
		int [] res = new int[indecies.length];
		for(int i =0; i< indecies.length; i++)
			res[i] = indecies[i].intValue();
		return res;
	}
	
}
