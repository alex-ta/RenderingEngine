package com.engine.Main;

import com.engine.Core.CoreEngine;
import com.engine.Core.TestGame;
import com.engine.Core.Window;

public class Main {
	
	
	public static void main(String[] args){
		Window.createWindow(600,600,"Hallo");
		CoreEngine game = new CoreEngine(new TestGame());	
		game.start(45f);
		
	}
}
