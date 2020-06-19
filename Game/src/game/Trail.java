package game;

import game.GameObject;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

/**
 *
 * @author Otavio e Luan
 */
public class Trail extends GameObject {
	
    private float alpha = 1.0f;
    private Handler handler;
    private Color color;
    private int width, height;
    private float life;

    
    
    public Trail(float x, float y, ID id, Color color, int width, int height, float life, Handler handler) {
	super(x, y, id);
	this.color = color;
	this.width = width;
	this.height = height;
	this.life = life;
	this.handler = handler;
    }

    @Override
    public void tick() {
        if (alpha > life)
            alpha -= life - 0.001f;
        else 
            handler.removeObject(this);
    }

    @Override
    public void render(Graphics g) {
	Graphics2D g2d = (Graphics2D) g;
    	g2d.setComposite(makeTransparent(alpha));
		
	g.setColor(color);
	g.fillRect((int) x, (int) y, width, height);
		
	g2d.setComposite(makeTransparent(1));
    }
	
    private AlphaComposite makeTransparent(float alpha) {
	int type = AlphaComposite.SRC_OVER;
	return AlphaComposite.getInstance(type, alpha);
    }

    @Override
    public Rectangle getBounds() {
        return null;
    }
}
