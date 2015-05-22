package com.engine.Core;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import com.engine.Math.Vector2D;

public class Window {
	
	private static int width;
	public static int getWidth() {
		return width;
	}

	public static void setWidth(int width) {
		Window.width = width;
	}

	public static int getHeight() {
		return height;
	}

	public static void setHeight(int height) {
		Window.height = height;
	}

	public static String getTitle() {
		return title;
	}

	public static void setTitle(String title) {
		Window.title = title;
	}
	
	private static int height;
	private static String title;
	private static boolean windowcreated;
	
	
	public static void createWindow(int width, int height, String title){
		if(windowcreated)
			return;
		Window.width = width;
		Window.height = height;
		Window.title = title;
		Display.setTitle(title);
		try {
			Display.setDisplayMode(new DisplayMode(width,height));
			Display.create();
			Keyboard.create();
			Mouse.create();
			windowcreated = true;
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void render(){
		Display.update();
	}
	
	public static boolean isCloseRequest(){
		return Display.isCloseRequested();
	}
	
	public static void closeWindow(){
		windowcreated = false;
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}
	
	public Vector2D getCenter(){
		return new Vector2D(getWidth()/2,getHeight()/2);
	}
	
}
