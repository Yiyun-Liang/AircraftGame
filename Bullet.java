package com.tarena.shoot;

public class Bullet extends FlyingObject{
	int speed = 3;
	public Bullet(int x, int y){
		image = ShootGame.bullet;
		width = image.getWidth();
		height = image.getHeight();
		this.x = x;
		this.y = y;
	}
	
	public void step(){
		y -= speed;
	}
	
	public boolean outOfBound(){
		return y<=-height;
	}
}
