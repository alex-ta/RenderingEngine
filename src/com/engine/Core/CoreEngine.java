package com.engine.Core;

public class CoreEngine {
	
	private static boolean running;
	private int renderdelay = 1;
	private Game game;
	private RenderingEngine Rengine;
	
	public CoreEngine (Game game){
		running = false;
		this.game = game;
		this.Rengine = new RenderingEngine();
		game.setCamera(Rengine.getMainCamera());	
	}
	
	
	
	
	public void start(float timesRender){
		if(running)
			return;
		run(timesRender);
	}
	
	public void stop(){
		if(!running)
			return;
		running = false;
	}	
	
	private void run(float timesRender){
		running = true;
		Timer time = new Timer(timesRender);
		while(running)
		{

			render();
			
			time.updateAll();
			game.input();
			game.update();
			
			
			while((time.next())){
			
			time.renderAll();	
			if(Window.isCloseRequest()){
			stop();	
			}
			time.reset();
			// sparedtime
			try {
				Thread.sleep(renderdelay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			}
		}
		clean();
	}
	private void render(){
		Rengine.render(game);
		Window.render();
	}
	
	private  void clean(){
		Window.closeWindow();
	}
	
}
