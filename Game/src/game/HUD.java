package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 *
 * @author Otavio e Luan
 */
public class HUD {
    
    public static boolean STATE_RAT = true;
    public static float HEALTH = 200;
    private float greenValue = 255;
    private int score = 0;
    private int level = 1;
	
    public void tick() {
	HEALTH = Game.clamp(HEALTH, 0, 100);
	greenValue = Game.clamp(greenValue, 0, 255);
	greenValue = HEALTH * 2;
	score++;
    }

    public void render(Graphics g) {
	g.setColor(new Color(100, (int) greenValue, 0));
	g.fillRect(15, 15, (int) (HEALTH * 2), 10);
	g.setColor(Color.white);
	g.drawRect(15, 15, 200, 10);
	g.drawString("Level: " + level, 15, 40);
	g.drawString("Score: " + score, 15, 55);
        Font font = new Font("Arial", 1, 15);
	g.setFont(font);
        g.setColor(Color.cyan);
        if(!STATE_RAT){
            g.drawString("YOU ARE THE CAT", Game.WIDTH - 150, 40);
            g.drawString("Take the RAT!!!", Game.WIDTH - 140, 60);
        }
        else{
	    g.drawString("YOU ARE THE RAT", Game.WIDTH - 150, 40);
	    g.drawString("Run away!!!", Game.WIDTH - 120, 60);
        }
    }

    public void score(int score) {
	this.score = score;
    }

    public int getScore() {
	return score;
    }

    public void setLevel(int level) {
    	this.level = level;
    }

    public int getLevel() {
	return level;
    }
}
