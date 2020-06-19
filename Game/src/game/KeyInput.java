package game;

import game.GameObject;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


/**
 *
 * @author Otavio e Luan
 */
public class KeyInput extends KeyAdapter {
	
    private final Handler handler;
    private final Game game;
    private final boolean[] keyDown = new boolean[4];
      
    public KeyInput(Handler handler, Game game) {
	this.handler = handler;
	this.game = game;
	keyDown[0] = false;
	keyDown[1] = false;
	keyDown[2] = false;
	keyDown[3] = false;
    }
 
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();	
		
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
                if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP   ){ tempObject.setVelY(-5); keyDown[0] = true; }
                if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN ) { tempObject.setVelY(5); keyDown[1] = true; }
                if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT ) { tempObject.setVelX(-5); keyDown[2] = true; }
                if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) { tempObject.setVelX(5); keyDown[3] = true; }
                if (key == KeyEvent.VK_ESCAPE) {
                    System.exit(0);
                }
            }
        }
    }
	
    @Override
    public void keyReleased(KeyEvent e) {
	int key = e.getKeyCode();
	
	for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ID.Player) {
            	if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP   ) keyDown[0] = false; // tempObject.setVelY(0);
		if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN ) keyDown[1] = false; // tempObject.setVelY(0);
		if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT ) keyDown[2] = false; // tempObject.setVelX(0);
		if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) keyDown[3] = false; // tempObject.setVelX(0);
			
		if (!keyDown[0] && !keyDown[1]) tempObject.setVelY(0); // vertical movement
		if (!keyDown[2] && !keyDown[3]) tempObject.setVelX(0); // horizontal movement
            }
	}
    }
}
