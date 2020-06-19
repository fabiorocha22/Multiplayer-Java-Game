package game;

import game.Handler;
import game.ID;
import java.awt.Color;

/**
 *
 * @author Otavio e Luan
 */
public class FastEnemy extends BasicEnemy {
    
    public FastEnemy(int x, int y, ID id, Handler handler) {
	super(x, y, id, handler);
	
	color = Color.ORANGE;
	velX = 14;
	velY = 4;
    }

}
