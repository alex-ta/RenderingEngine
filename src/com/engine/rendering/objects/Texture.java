package com.engine.rendering.objects;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.HashMap;

import javax.imageio.ImageIO;

import com.engine.core.Util;
import com.engine.rendering.ressource.TextureResource;


public class Texture
{
	private static HashMap<String,TextureResource> loadedModels = new HashMap<String,TextureResource>();
	private TextureResource resource;
	private String fileName;
	
	public Texture(String fileName)
	{
		this.fileName = fileName;
		TextureResource oldRes = loadedModels.get(fileName);
		if(oldRes != null){
			resource = oldRes;
			resource.addReference();
		}else{
			resource = loadTexture(fileName);
			loadedModels.put(fileName,resource);
		}
	}
	
	public void bind(int samplerSlot)
	{
		assert(samplerSlot >= 0 && samplerSlot <= 31);
		glActiveTexture(GL_TEXTURE0+samplerSlot);
		glBindTexture(GL_TEXTURE_2D, resource.getId());
	}
	
	public void bind(){
		bind(0);
	}
	
	public int getID()
	{
		return resource.getId();
	}
	
	private static TextureResource loadTexture(String fileName)
	{
		String[] splitArray = fileName.split("\\.");
		String ext = splitArray[splitArray.length - 1];
		
		try
		{		
			BufferedImage img = ImageIO.read(new File("./res/textures/"+fileName));
			int[] pixels = img.getRGB(0,0, img.getWidth(), img.getHeight(), null,0,img.getWidth());
			ByteBuffer buff = Util.createByteBuffer(img.getHeight()*img.getWidth()*4);
			boolean hasAlpha = img.getColorModel().hasAlpha();
			
			for(int y=0; y<img.getHeight(); y++){
				for(int x=0; x<img.getWidth(); x++){
					int pixel = pixels[y*img.getWidth()+x];
					buff.put((byte)((pixel>>16)&0xFF));
					buff.put((byte)((pixel>>8)&0xFF));
					buff.put((byte)((pixel)&0xFF));
					if(hasAlpha){
						buff.put((byte)((pixel>>24)&0xFF));
					}else{
						buff.put((byte)(0xFF));
					}
				}
			}
			buff.flip();
			TextureResource resource = new TextureResource();
			glBindTexture(GL_TEXTURE_2D,resource.getId());
			
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_WRAP_S,GL_REPEAT);
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_WRAP_T,GL_REPEAT);
			
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MIN_FILTER,GL_LINEAR);
			glTexParameterf(GL_TEXTURE_2D,GL_TEXTURE_MAG_FILTER,GL_LINEAR);
			
			glTexImage2D(GL_TEXTURE_2D,0,GL_RGBA8,img.getWidth(),img.getHeight(),0,GL_RGBA,GL_UNSIGNED_BYTE,buff);
			
			return resource;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return null;
	}
	
	@Override
	protected void finalize(){
		if(resource.removeReference()&&fileName.isEmpty()){
			loadedModels.remove(fileName);
		}
	}
}

