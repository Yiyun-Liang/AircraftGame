package com.tarena.shoot;
import java.awt.image.BufferedImage;
public abstract class FlyingObject {
	protected BufferedImage image;
	protected int width;
	protected int height;
	protected int x;
	protected int y;
	
	public abstract void step();
	
	public boolean shootBy(Bullet b){
		int x1 = this.x;
		int x2 = this.x+ width;
		int y1 = this.y;
		int y2 = this.y +height;
		int bx = b.x;
		int by = b.y;
		return bx < x2 && bx>x1 && by<y2 && by>y1;
	}
	
	public abstract boolean outOfBound();
}
