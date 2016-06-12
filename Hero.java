package com.tarena.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
public class Hero extends FlyingObject{
	private int life;  
	private int doubleFire; 
	private BufferedImage[] images; //array of images
	private int index; //control the frequency
	
	/** constructor */
	public Hero(){
		image = ShootGame.hero0; //images
		width = image.getWidth(); //width
		height = image.getHeight(); //height
		x = 150; //x:fix to 150
		y = 400; //y:fix to 400
		life = 3; //initialize life to 3
		doubleFire = 0; //when fire is 0, it means 1x fire
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1}; //load two of the images
		index = 0; //changing betweent the two images
	}
	
	public void step(){
		image = images[index++/10%images.length];
	}
	
	public Bullet [] shoot(){
		int po = width/4;
		if(doubleFire>0){
			Bullet [] bullets = new Bullet[2];
			bullets[0] = new Bullet(this.x+po, this.y);
			bullets[1] = new Bullet(this.x+3*po, this.y);	
			doubleFire -=2;
			return bullets;
		}else{
			Bullet [] bullets = new Bullet[1];
			bullets[0] = new Bullet(this.x+2*po, this.y);
			return bullets;
		}
	}
	
	public void moveTo(int x, int y){
		this.x = x - width/2;
		this.y = y - height/2;
	}
	
	public void addDoubleFire(){
		doubleFire+=40;
	}
	public void addlife(){
		life++;
	}
	
	public int getLife(){
		return life;
	}
	
	public boolean outOfBound(){
		return false;
	}
	
	public boolean hit(FlyingObject o){
		int x = this.x + width/2;
		int y = this.y + height/2;
		int x1 = o.x - width/2;
		int x2 = o.x + o.width + width/2;
		int y1 = o.y - height/2;
		int y2 = o.y + o.height + height/2;
		return x>x1 && x<x2 && y< y2 && y> y1;
	}
	public void subtractLife(){
		life--;
	}
	public void clearDoubleFire(){
		doubleFire =0;
	}
}
