import java.util.Random;

//import java.awt.font.*;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Particle {
	private double x;
	private double y;
	private Image img;
	private boolean isAlive = true;
	private Color color;
	
	private final boolean shouldRotate;
	private final int LIFESPAN;
	private double LIFETIME;
	private float scale;
	
	public Particle(String text, double x, double y, Color color, int LIFESPAN, boolean shouldRotate, float scale) {
		this.x = x;
		this.y = y;
		this.LIFESPAN = LIFESPAN;
		this.shouldRotate = shouldRotate;
		this.scale = scale;
		
		this.color = color;
		LIFETIME = LIFESPAN;
		
		try {
			img = new Image("res\\masks for  particles\\"+text+".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
	}
	
	public boolean isAlive() {
		return isAlive;
	}
	
	public void update() {
		if (LIFETIME > 0)
			LIFETIME--;
		if (LIFETIME == 0)
			isAlive=false;
	}
	
	public void render(GameContainer container, Graphics g) {
		Image per = img.getScaledCopy((float)(LIFETIME/LIFESPAN*scale));
		
		per.setImageColor(color.getRed()/255.0f, color.getGreen()/255.0f, color.getBlue()/255.0f, color.getAlpha()/255.0f);
		
		if (shouldRotate)
			per.rotate((float)LIFETIME*2);
		per.draw((int)x-per.getWidth()/2, (int)y-per.getHeight()/2, color);
		//per.drawCentered((int)x, (int)y);
		
	
		
	}
}
