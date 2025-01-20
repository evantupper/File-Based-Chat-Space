

import java.util.Random;

//import java.awt.font.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class TextParticle {
	private double x;
	private double y;
	private String text;
	private boolean isAlive = true;
	
	private double velocityX;
	private double velocityY;
	
	private final int LIFESPAN = 120;
	private int LIFETIME;
	
	private Color color;
	
	public TextParticle(String text, double x, double y, Color color) {
		this.x = x+new Random().nextDouble()*6-3;
		this.y = y+new Random().nextDouble()*6-3;
		LIFETIME = LIFESPAN;
		
		velocityX = new Random().nextDouble()*2-1;
		velocityY = new Random().nextDouble()*2-1-1.6;
		
		this.text = text;
		this.color = color;
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void update() {
		LIFETIME--;
		if (LIFETIME <= 0) {
			isAlive = false;
		}
		velocityY+=0.02;
		
		x+=velocityX/2;
		y+=velocityY/2;
	}
	
	public void render(GameContainer container, Graphics g) {
		g.setColor(color);
		
		//g.setFont(new TrueTypeFont(new Font("TimesRoman", 1, 10), true));
		
		g.drawString(text, (float)x, (float)y);
		
		//new TrueTypeFont(new Font("Verdana", Font.PLAIN, 10), true).drawString((float)x, (float)y, text, color);
	}
}
