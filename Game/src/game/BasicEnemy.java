package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Otavio e Luan
 */
public class BasicEnemy extends GameObject {

    private Handler handler;
    protected Color color;
	
    public BasicEnemy(int x, int y, ID id, Handler handler) {
        super(x, y, id);
	this.handler = handler;
	velX = 5;
	velY = 5;
	this.color = Color.RED;
    }
    
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, 16, 16);
    }
    
    @Override
    public void tick() {
    	x += velX;
    	y += velY;
        
	if (y <= 0 || y >= Game.HEIGHT - 32) {
            velY *= -1;
        }
	if (x <= 0 || x >= Game.WIDTH - 16) {
            velX *= -1;
	}
		
	handler.addObject(new Trail(x, y, ID.Trail, color, 16, 16, 0.03f, handler));
    }

    @Override
    public void render(Graphics g) {
	g.setColor(color);
	g.fillRect((int) x, (int) y, 16, 16);
    }
}
