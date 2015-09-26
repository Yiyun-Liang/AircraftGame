package com.tarena.shoot;
import java.awt.image.BufferedImage;
import java.util.Random;
public class Hero extends FlyingObject{
	private int life;  //命
	private int doubleFire; //火力值
	private BufferedImage[] images; //图片数组
	private int index; //控制切换的频率
	
	/** 构造方法 */
	public Hero(){
		image = ShootGame.hero0; //图片
		width = image.getWidth(); //宽
		height = image.getHeight(); //高
		x = 150; //x:固定的150
		y = 400; //y:固定的400
		life = 3; //3条命
		doubleFire = 0; //火力值0,代表单倍火力
		images = new BufferedImage[]{ShootGame.hero0,ShootGame.hero1}; //两张图片
		index = 0; //协助切换图片
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
