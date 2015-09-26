package com.tarena.shoot;
import java.util.Random;
public class Airplane extends FlyingObject implements Enemy {
	private int speed = 2;
	public Airplane(){
		image = ShootGame.airplane;
		width = image.getWidth();
		height = image.getHeight();
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);
		y = -height;
		
	}
	
	public int getScore(){
		return 5;
	}
	
	public void step(){
		y += 2;
	}
	
	public boolean outOfBound(){
		return y>=ShootGame.HEIGHT;
	}
}
