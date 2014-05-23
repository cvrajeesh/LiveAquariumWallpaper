
package com.plugai.android.livewallpapers;

public class AquariumThread extends Thread {

	private Aquarium _aquarium;
	private boolean _running;
	
	public AquariumThread(Aquarium aquarium) {
		this._aquarium = aquarium;
	}
	
	public void switchOn(){
		this._running = true;
		this.start();
	}
	
	public void pause(){
		this._running = false;
		synchronized(this){
			this.notify();
		}
	}
	
	public void switchOff(){
		this._running = false;
		synchronized(this){
			this.notify();
		}
	}
	
	@Override
	public void run() {
		while(this._running){
			this._aquarium.render();
		}
	}
}
