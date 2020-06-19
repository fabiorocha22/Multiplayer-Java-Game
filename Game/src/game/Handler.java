package game;

import java.awt.Graphics;
import java.util.LinkedList;

/**
 *
 * @author Otavio e Luan
 */
public class Handler {
    
    LinkedList<GameObject> object;
    public Game game;

    public Handler(Game g) {
        this.object = new LinkedList<>();
        game = g;
    }
	
    public void tick() {
	for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
		
            tempObject.tick();
	}
    }

    public void render(Graphics g) {
	for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
			
            tempObject.render(g);
	}
    }
	
    public void clearEnemies() {
	for (int i = 0; i < object.size(); i++) {
            GameObject tempObject = object.get(i);
            if (tempObject.getId() != ID.Player) 
		removeObject(tempObject);
            i = 0;
	}
    }

    public void addObject(GameObject object) {
	this.object.add(object);
    }

    public void removeObject(GameObject object) {
	this.object.remove(object);
    }
}
