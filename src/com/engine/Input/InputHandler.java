package com.engine.Input;



import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class InputHandler {
	
	
	private boolean moveup,movedown,moveleft,moveright,moveforward,movebackwards;
	private MouseW mouse;

	public void setMouse(boolean left, boolean right,int posx, int posy) {
		mouse.update(left, right, posx, posy);
	}

	public InputHandler(){
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

	public boolean isMoveup() {
		return moveup;
	}

	public boolean isMovedown() {
		return movedown;
	}

	public boolean isMoveleft() {
		return moveleft;
	}

	public boolean isMoveright() {
		return moveright;
	}

	public boolean isMoveforward() {
		return moveforward;
	}

	public boolean isMovebackwards() {
		return movebackwards;
	}

	public MouseW getMouse() {
		return mouse;
	}
	
	

	

}
