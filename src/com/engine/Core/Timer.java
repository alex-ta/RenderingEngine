package com.engine.Core;

public class Timer {
private long startedAt;
private long endedAt;
private long lastTime;
private long currentTime;
private double delta;
private double times;
private long timeToSecond;

private int fpscounter;
private int upscounter;
private int fps;
private int ups;

public static long SECOND = 1000;

public Timer(float timesPerSec){
	lastTime = currentTime = endedAt = startedAt = System.currentTimeMillis();
	fpscounter = upscounter = fps = ups = 0;
	delta = 0;
	times = (SECOND)/(timesPerSec); 
	times -= (timesPerSec)*0.009;
	// to fix some buggs *0.009 -> near the selected framerate
}

public void update(){
	endedAt = currentTime = System.currentTimeMillis();
	delta += currentTime - lastTime;
	lastTime = currentTime;	
}

public void render(){
	currentTime = System.currentTimeMillis();
	delta += currentTime-(lastTime+delta);
}

public boolean next(){
	if(times > delta)
		return false;
	timeToSecond += currentTime - lastTime;
	lastTime = currentTime;
	delta -= times;
	return true;
}

public double getDelta(){
	return delta;
}

public long getRunningTime(){
	return startedAt - endedAt;
}

public void updateAll(){
	update();
	upscounter++;
}

public void renderAll(){
	render();
	fpscounter++;
}

public void reset(){
	if(!(timeToSecond > SECOND))
	return;
	ups = upscounter;
	fps = fpscounter;
	fpscounter = upscounter = 0;
	timeToSecond = 0;
}

public int getFPS(){
	return fps;
}

public int getUPS(){
	return ups;
}


}
