package game;

import game.ID;
import java.awt.Graphics;
import java.awt.Rectangle;


/**
 *
 * @author Otavio e Luan
 */
public abstract class GameObject {
	
    protected float x, y;
    protected ID id;
    protected float velX, velY;
    
    public GameObject(){};
    
    public GameObject(float x2, float y2, ID id){
    	this.x = x2;
    	this.y = y2;
	this.id = id;
    }
	
    public abstract void tick();

    public abstract void render(Graphics g);

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.x = y;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }
    
    protected void setId(ID id) {
	this.id = id;
    }

    public ID getId() {
	return id;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }

    public float getVelX() {
	return velX;
    }

    public float getVelY() {
	return velY;
    }

    public abstract Rectangle getBounds();
}