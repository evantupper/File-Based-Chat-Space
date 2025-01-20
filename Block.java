import org.newdawn.slick.Image;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class Block {
	int width;
	int height;
	int x;
	int y;
	int textureIDX;
	int textureIDY;
	Image sprite;
	Shape hitbox;
	
	public Block(int x, int y, int textureIDX, int textureIDY) {
		this.width = 32;
		this.height = 32;
		
		this.x = x;
		this.y = y;
		this.textureIDX = textureIDX;
		this.textureIDY = textureIDY;
		
		hitbox = new Rectangle(x*32,y*32,32,32);
	}
	
	public void update() {
		
	}
	
	public Shape getHitbox() {
		return hitbox;
		
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getTextureX() {
		return textureIDX;
	}
	
	public int getTextureY() {
		return textureIDY;
	}
}
