import java.util.ArrayList;
import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TerrainManager {
	private Image map;
	private ArrayList<ArrayList<Double>> tileMap;
	
	public TerrainManager() {
		try {
			map = new Image("res/sheet2.png");
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	public void render(GameContainer container, Graphics g) {
		for (int i = 0; i < Main.getBlocks().size(); i++) {
			
			map.getSubImage(256+Main.getBlocks().get(i).getTextureX()*32, Main.getBlocks().get(i).getTextureY()*32, 32, 32).draw(Main.getBlocks().get(i).getX()*32, Main.getBlocks().get(i).getY()*32, 1);
		}
		
		
	}
}
