package com.engine.core;

import com.engine.rendering.objects.RenderingEngine;
import com.engine.scenegraph.Game;

public class CoreEngine
{
	private boolean isRunning;
	private Game game;
	private RenderingEngine renderingEngine;
	private double frameTime;
	private Input input;
	
	public CoreEngine(double framerate, Game game)
	{
		this.isRunning = false;
		this.game = game;
		this.frameTime = 1.0/framerate;
		input = new Input();
		game.setEngine(this);
		this.renderingEngine = new RenderingEngine();
	}

	public static void crateWindow(int width, int height,String title){
		Window.createWindow(width, height, title);
	}
	
	public void start()
	{
		if(isRunning)
			return;
		
		run();
	}
	
	public void stop()
	{
		if(!isRunning)
			return;
		
		isRunning = false;
	}
	
	private void run()
	{
		isRunning = true;
		
		int frames = 0;
		long frameCounter = 0;

		game.init();

		double lastTime = Time.getTime();
		double unprocessedTime = 0;
		
		while(isRunning)
		{
			boolean render = false;

			double startTime = Time.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;
			
			unprocessedTime += passedTime;
			frameCounter += passedTime;
			
			while(unprocessedTime > frameTime)
			{
				render = true;
				
				unprocessedTime -= frameTime;
				
				if(Window.isCloseRequested())
					stop();

				game.input((float)frameTime);
				input.update();
				
				game.update((float)frameTime);
				
				if(frameCounter >= 1.0)
				{
					System.out.println(frames);
					frames = 0;
					frameCounter = 0;
				}
			}
			if(render)
			{
				game.render(renderingEngine);
				Window.render();
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		cleanUp();
	}
	
	private void cleanUp()
	{
		Window.dispose();
	}

	public RenderingEngine getRenderngEngine() {
		return renderingEngine;
	}
}
