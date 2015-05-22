package com.engine.Input;

import com.engine.Math.Vector2D;

public class MouseW {
	
	private boolean mousedownl,mousedownr;
	private int x,y;
	private static int deltax;
	private static int deltay;
	
	public MouseW(){
		x = y = deltay = deltax =0;
		mousedownl = mousedownr = false;
	}

		// x and y are the wrong way around in opengl
	public void update(boolean downleft,boolean downright, int y, int x){
		this.mousedownl = downleft;
		this.mousedownr = downright;
		if(downleft){
		MouseW.deltax = this.x -x;
		MouseW.deltay = this.y -y;
		} else {
		MouseW.deltax = MouseW.deltay = 0;	
		}
		this.x = x;
		this.y = y;
		}
	
	public boolean isMouseleftdown() {
		return mousedownl;
	}
	
	public boolean isMouserightdown() {
		return mousedownr;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "left: "+mousedownl+" right: "+mousedownr+" x: "+x+" y: "+ y;
	}
	
	public static Vector2D getDeltaMovement(){
		return new Vector2D(deltax,deltay) ;
	}
	

}
