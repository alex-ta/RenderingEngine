package com.engine.Desing;
import static org.lwjgl.opengl.GL11.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.newdawn.slick.opengl.TextureLoader;

public class Texture {
	
	private int id;
	
	public Texture (String file){
		this(loadTexture(file));
	}
	
	public Texture (int id){
		this.id = id;
	}
	
	public int getID(){
		return id;
	}
	
	public void bind(){
		glBindTexture(GL_TEXTURE_2D,id);
		
	}
	
	protected static int loadTexture(String fileName){
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length-1];
		FileInputStream stream = null;
		int texture = 0;
		try{
			stream = new FileInputStream(new File ("./res/textures/"+fileName));
			texture = TextureLoader.getTexture(ext,stream).getTextureID();
		}catch(Exception e){
			e.printStackTrace();
			System.exit(1);
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return texture;
	}
}
