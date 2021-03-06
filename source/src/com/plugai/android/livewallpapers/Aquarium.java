package com.plugai.android.livewallpapers;

import java.util.ArrayList;

import com.plugai.android.livewallpapers.fishes.*;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.view.SurfaceHolder;

public class Aquarium {
	
	private AquariumThread _aquariumThread;	
	private SurfaceHolder _surfaceHolder;	
	private ArrayList<Renderable> _fishes;	
	private Bitmap _backgroundImage;
	private Context _context;
	
	
	public void render(){
		Canvas canvas = null;
		try{
			
			canvas = this._surfaceHolder.lockCanvas(null);
			synchronized (this._surfaceHolder) {
				this.onDraw(canvas);
			}
			
		}finally{
			if(canvas != null){
				this._surfaceHolder.unlockCanvasAndPost(canvas);
			}
		}	
	}
	
	protected void onDraw(Canvas canvas) {
		this.renderBackGround(canvas);
		for (Renderable renderable : this._fishes) {
			renderable.render(canvas);
		}
	};

	public void start(){
		this._aquariumThread.switchOn();
	}
	
	public void stop(){
		boolean retry = true;
        this._aquariumThread.switchOff();
        while (retry) {
            try {
           	 this._aquariumThread.join();
                retry = false;
            } catch (InterruptedException e) {
                // we will try it again and again...
            }
        }
	}

	public int getLeft() {		
		return 0;
	}


	public int getRight() {
		return this._backgroundImage.getWidth();
	}
	
	public void initialize(Context context, SurfaceHolder surfaceHolder) {
		this._aquariumThread = new AquariumThread(this);	
		this._surfaceHolder = surfaceHolder;		
		this._fishes = new ArrayList<Renderable>();
		this._context = context;
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inPurgeable = true;
		this._backgroundImage = BitmapFactory.decodeResource(context.getResources(), com.plugai.android.livewallpapers.R.drawable.aquarium, options);		
		this.addFishes();
	}

	private void addFishes() {		
		Point startPoint = new Point(100, 100);
		this._fishes.add(new ClownFish(this._context, this, startPoint, 90));
		Point startPoint1 = new Point(100, 300);
		this._fishes.add(new ClownFish(this._context, this, startPoint1, 50));
		
		Point startPoint2 = new Point(200, 200);
		this._fishes.add(new ClownFish(this._context, this, startPoint2, 15));
	}
	
	private void renderBackGround(Canvas canvas)
	{
		canvas.drawBitmap(this._backgroundImage, 0, 0, null);
	}
}
