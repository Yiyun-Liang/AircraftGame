package com.tarena.shoot;

import java.util.Random;

public class Bee extends FlyingObject implements Award {
	private int xSpeed = 1;
	private int ySpeed = 2;
	private int awardType;
	public Bee(){
		this.image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
		y = -height;
		awardType = rand.nextInt(2);
		
	}
	
	
	
	public int getType(){
		return awardType;
	}
	
	public void step(){
		y += ySpeed;
		if(x>=ShootGame.WIDTH){
			x -= xSpeed;
		}
		if(x<= 0){
			x +=xSpeed;
		}
	}
	
	public boolean outOfBound(){
		return y >= ShootGame.HEIGHT;
	}
}
