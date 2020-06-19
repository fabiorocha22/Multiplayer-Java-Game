package game;

import game.Game;
import game.Handler;
import game.ID;
import game.Trail;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 *
 * @author Otavio e Luan
 */

public class SmartEnemy extends GameObject {

  private Handler handler;
  protected Color color;
  private GameObject player;
  
  public SmartEnemy(int x, int y, ID id, Handler handler) {
    super(x, y, id);
    this.color = Color.GREEN;
    this.handler = handler;

    for (int i = 0; i < handler.object.size(); i++) {
        if (handler.object.get(i).getId() == ID.Player) {
            player = handler.object.get(i);
        }
    }

  }
  
  @Override
  public Rectangle getBounds() {
    return new Rectangle((int) x, (int) y, 16, 16);
  }

  @Override
  public void tick() {
    x += velX;
    y += velY;

    float diffX = x - player.getX() - 8;
    float diffY = y - player.getY() - 8;
    float distance = (float) Math.sqrt(((x - player.getX()) * (x - player.getX())) + ((y - player.getY()) * (y - player.getY())));

    velX = ((-2 / distance) * diffX);
    velY = ((-2 / distance) * diffY);

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
