package com.engine.main;

import com.engine.core.CoreEngine;

public class Main
{
	public static void main(String[] args)
	{
		CoreEngine.crateWindow(800,600, "Game");
		CoreEngine engine = new CoreEngine(60, new TestGame());
		engine.start();
	}
}
