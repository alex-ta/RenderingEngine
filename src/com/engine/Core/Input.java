package com.engine.core;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import com.math.Vector2D;
public class Input {
	
	
	private static boolean moveup,movedown,moveleft,moveright,moveforward,movebackwards;
	private MouseW mouse;

	public void setMouse(boolean left, boolean right,int posx, int posy) {
		mouse.update(left, right, posx, posy);
	}

	public Input(){
		mouse = new MouseW();
	}
	
	public void update(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W)){
			moveforward = true;
		} else {
			moveforward = false;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S)){
			movebackwards = true;
		} else {
			movebackwards = false;
		}
		
		if(Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A)){
			moveleft = false;
		} else {
			moveleft = true;
		}
			
		if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D)){
			moveright = false;
		} else {
			moveright = true;
		}
		
		mouse.update(Mouse.isButtonDown(0),Mouse.isButtonDown(1),Mouse.getX(),Mouse.getY());
		
	}

	public static boolean isMoveup() {
		return moveup;
	}

	public static boolean isMovedown() {
		return movedown;
	}

	public static boolean isMoveleft() {
		return moveleft;
	}

	public static boolean isMoveright() {
		return moveright;
	}

	public static boolean isMoveforward() {
		return moveforward;
	}

	public static boolean isMovebackwards() {
		return movebackwards;
	}

	public MouseW getMouse() {
		return mouse;
	}
	

}