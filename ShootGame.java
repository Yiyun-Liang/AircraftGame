package com.tarena.shoot;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;
import java.util.Arrays;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;

import java.awt.Graphics;
public class ShootGame extends JPanel{
	
	public static final int WIDTH = 400;  //���ڿ�
	public static final int HEIGHT = 654; //���ڸ�
	
	public static BufferedImage background; //����ͼ
	public static BufferedImage start; //����ͼ
	public static BufferedImage pause; //��ͣͼ
	public static BufferedImage gameover; //��Ϸ����ͼ
	public static BufferedImage airplane; //�л�ͼ
	public static BufferedImage bee; //С�۷�ͼ
	public static BufferedImage bullet; //�ӵ�ͼ
	public static BufferedImage hero0; //Ӣ�ۻ�ͼ1
	public static BufferedImage hero1; //Ӣ�ۻ�ͼ2
	
	public static final int START = 0;
	public static final int RUNNING = 1;
	public static final int PAUSE = 2;
	public static final int GAME_OVER = 3;
	private int state = 0;
	
	private Hero hero = new Hero();  //Ӣ�ۻ�����
	private FlyingObject[] flyings = {}; //����(�л�+С�۷�)����
	private Bullet[] bullets = {}; //�ӵ�����
	
	static{  //��ʼ����̬��Դ
		try{
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	int flyEnterIndex = 0;
	public void enterAction(){
		flyEnterIndex++;
		if(flyEnterIndex % 40 ==0){
			FlyingObject f = nextOne();
			flyings = Arrays.copyOf(flyings, flyings.length+1);
			flyings[flyings.length-1] = f;
		}
	}
	
	public FlyingObject nextOne(){
		Random rand = new Random();
		int i = rand.nextInt(20);
		if(i<1){
			return new Bee();
		}else{
			return new Airplane();
		}
	}
	
	public void stepAction(){
		hero.step();
		for(int i =0; i<flyings.length; i++){
			flyings[i].step();
		}
		for(int i = 0; i<bullets.length; i++){
			bullets[i].step();
		}
	}
	int shootIndex = 0;
	public void shootAction(){
		shootIndex++;
		if(shootIndex % 30 ==0){
			Bullet [] bs = hero.shoot();
			bullets = Arrays.copyOf(bullets, bullets.length+bs.length);
			System.arraycopy(bs, 0, bullets, bullets.length-bs.length, bs.length);
		}
	}
	public void bangAction(){
		for(int i =0; i<bullets.length ; i++){
			bang(bullets[i]);
		}
	}
	int score =0;
	public void bang(Bullet bullet){
		int index = -1;
		for(int i = 0; i<flyings.length ; i++){
			FlyingObject o = flyings[i];
			if(o.shootBy(bullet)){
				index = i;
				break;
			}
		}
		if(index != -1){
			FlyingObject one = flyings[index];
			if(one instanceof Airplane){
				Airplane a = (Airplane)one;
				score += a.getScore();
			}
			if(one instanceof Bee){
				Bee b = (Bee)one;
				int type =b.getType();
				switch(type){
				case Award.DOUBLE_FIRE:
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					hero.addlife();
					break;
				}
			}
			FlyingObject f = flyings[index];
			flyings[index] = flyings[flyings.length-1];
			flyings[flyings.length-1] = f;
			flyings = Arrays.copyOf(flyings, flyings.length-1);
		}
	}
	public void outOfBoundAction(){
		//flyingobjects
		FlyingObject [] fAlive = new FlyingObject[flyings.length];
		int index=0;
		for(int i =0 ; i< flyings.length ; i++){
			FlyingObject f = flyings[i];
			if(!f.outOfBound()){
				fAlive[index] = f;
				index++;
			}
		}
		flyings = Arrays.copyOf(fAlive, index);
		//bullets
		Bullet [] bAlive = new Bullet[bullets.length];
		index=0;
		for(int i =0 ; i< bullets.length ; i++){
			Bullet b = bullets[i];
			if(!b.outOfBound()){
				bAlive[index] = b;
				index++;
			}
		}
		bullets = Arrays.copyOf(bAlive, index);
	}
	public void checkGameOverAction(){
		if(checkGameOver()){
			state = GAME_OVER;
		}
	}
	public boolean checkGameOver(){
		for(int i = 0 ; i< flyings.length ; i++){
			FlyingObject f = flyings[i];
			if(hero.hit(f)){
				hero.subtractLife();
				hero.clearDoubleFire();
				
				FlyingObject temp = f;
				f=flyings[flyings.length-1];
				flyings[flyings.length-1] = temp;
				flyings = Arrays.copyOf(flyings, flyings.length-1);
			}
		}
		
		return hero.getLife()<=0;
	}
	
	
	public void action(){
		MouseAdapter l = new MouseAdapter(){
			public void mouseMoved(MouseEvent e){
				if(state == RUNNING){
				int x= e.getX();
				int y= e.getY();
				hero.moveTo(x,y);
				}
			}
			public void mouseClicked(MouseEvent e){
				switch (state){
				case START:
					state = RUNNING;
					break;
				case GAME_OVER:
					state = START;
					break;
				}
			}
			public void mouseEntered(MouseEvent e){
				if(state == PAUSE){
				state = RUNNING;
				}
			}
			public void mouseExited(MouseEvent e){
				state = PAUSE;
			}
		};
		
		this.addMouseListener(l);
		this.addMouseMotionListener(l);
		
		Timer timer = new Timer();
		timer.schedule(new TimerTask(){
			public void run(){
				if(state== RUNNING){
				enterAction();
				stepAction();
				shootAction();
				bangAction();
				outOfBoundAction();
				checkGameOverAction();
				}
				repaint();
			}
		},10, 10);
		
	}
	
	
	public void paint(Graphics g){
		g.drawImage(background, 0, 0, null);
		paintHero(g);
		paintFlyingObject(g);
		paintBullet(g);
		paintScore(g);
		paintState(g);
	}
	public void paintHero(Graphics g){
		g.drawImage(hero.image, hero.x, hero.y, null);
		
	}
	public void paintFlyingObject(Graphics g){
		for(int i=0; i<flyings.length; i++){
			FlyingObject f = flyings[i];
			g.drawImage(f.image, f.x, f.y, null);
		}
		
	}
	public void paintBullet(Graphics g){
		for(int i=0; i<bullets.length; i++){
			FlyingObject b = bullets[i];
			g.drawImage(b.image, b.x, b.y, null);
		}
	}
	public void paintScore(Graphics g){
		g.setColor(new Color(0xffffff));
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
		g.drawString("Life: "+ hero.getLife(), 10, 25);
		g.drawString("Score: "+score, 10, 45);
	}
	public void paintState(Graphics g){
		switch(state){
		case START:
			g.drawImage(start, 0, 0, null);
			break;
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;
		case GAME_OVER:
			score = 0; 
			hero = new Hero();
			bullets = new Bullet [] {};
			flyings = new FlyingObject [] {};
			g.drawImage(gameover, 0, 0, null);
			break;
		}
	}
	
	public static void main(String[] args) {
		
		JFrame frame = new JFrame("Fly"); //����
 		ShootGame game = new ShootGame(); //���
 		frame.add(game); //�������ӵ�������
 		frame.setSize(WIDTH, HEIGHT); //���ô��ڴ�С
 		frame.setAlwaysOnTop(true); //����һֱ����
 		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //����Ĭ�Ϲرղ���--�رմ���ʱ�˳�����
 		frame.setLocationRelativeTo(null); //�������λ��Ϊnull����:����
 		frame.setVisible(true); //1.���ô��ڿɼ�  2.�������paint()����
 		
 		game.action();
	}

}
