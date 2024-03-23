package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.text.Element;
import javax.swing.text.html.ImageView;

public class Dragon_scene {
	private ArrayList<ImageIcon> ran_BG = new ArrayList<>() ;
	ImageIcon BG1 = new ImageIcon("src\\Image\\background_raining1.png");
	ImageIcon BG2 = new ImageIcon("src\\Image\\background_raining2.png");
	ImageIcon BG3 = new ImageIcon("src\\Image\\background_raining3.png");
	ImageIcon BG4 = new ImageIcon("src\\Image\\background_sunny.png");
	ImageIcon BG5 = new ImageIcon("src\\Image\\background_sunny2.png");
	ImageIcon BG6 = new ImageIcon("src\\Image\\background_sunny3.png");
	
	
	public Dragon_scene() {
		ran_BG = new ArrayList<ImageIcon>();
		ran_BG.add(BG1);
		ran_BG.add(BG2);
		ran_BG.add(BG3);
		ran_BG.add(BG4);
		ran_BG.add(BG5);
		ran_BG.add(BG6);
		
	}
	
	public void draw(Graphics g) {
		Random rand = new Random();
		for (int i = 0; i < ran_BG.size();i++){
			int randomIndex = rand.nextInt(ran_BG.size());
			ImageIcon randomImage = ran_BG.get(randomIndex);
		}
	}
		
	
		public static void main(String[] args) {
			Dragon_scene d = new Dragon_scene();
			d.draw(null);
		}
		
	
}
	

