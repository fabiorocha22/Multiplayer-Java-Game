package game;

import game.Game;
import game.Handler;
import game.ID;
import game.Trail;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Otavio e Luan
 */
public class EnemyBossBullet extends GameObject {

    private Handler handler;
    protected Color color;
    Random r = new Random();
    
    public EnemyBossBullet(int x, int y, ID id, Handler handler) {
	super(x, y, id);
	this.handler = handler;
	velX = (r.nextInt(5 - -5) + -5);
	velY = 5;
	this.color = Color.BLUE;
    }

    @Override
    public Rectangle getBounds() {
	return new Rectangle((int) x, (int) y, 16, 16);
    }

    @Override
    public void tick() {
        x += velX;
	y += velY;
		
	if (y >= Game.HEIGHT) handler.removeObject(this);
		
	handler.addObject(new Trail(x, y, ID.Trail, color, 16, 16, 0.03f, handler));
    }

    @Override
    public void render(Graphics g) {
    	g.setColor(color);
    	g.fillRect((int) x, (int) y, 16, 16);
    }
}
