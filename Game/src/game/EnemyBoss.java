package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

/**
 *
 * @author Otavio e Luan
 */
public class EnemyBoss extends GameObject {

    private Handler handler;
    protected Color color;
    private int timer = 80;
    private int timer2 = 50;
    Random r = new Random();
    
    public EnemyBoss(int x, int y, ID id, Handler handler) {
	super(x, y, id);
	this.handler = handler;

	velX = 0;
	velY = 2;

	this.color = Color.BLUE;
    }
    
    @Override
    public Rectangle getBounds() {
    	return new Rectangle((int) x, (int) y, 64, 64);
    }

    @Override
    public void tick() {
	x += velX;
	y += velY;

	if (timer <= 0)
            velY = 0;
        else
            timer--;

	if (timer <= 0)
            timer2--;
	if (timer2 <= 0) {
            if (velX == 0)
                velX = 2;
			
	velX *= 1.0015;
	velX = Game.clamp(velX, -10, 10);

	int spawn = r.nextInt(10);
	if (spawn == 0 && timer <= 0)
            handler.addObject(new EnemyBossBullet((int) x + 32, (int) y + 32, ID.BasicEnemy, handler));
	}

	if (x <= 0 || x >= Game.WIDTH - 64)
            velX *= -1;

        handler.addObject(new Trail(x, y, ID.Trail, color, 64, 64, 0.08f, handler));
    }

    @Override
    public void render(Graphics g) {
        g.setColor(color);
	g.fillRect((int) x, (int) y, 64, 64);
    }
    
    @Override
    public float getX(){
        return this.x;
    }
}
