/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.rmi.RemoteException;
import rmi.RMI;

/**
 *
 * @author Fabio R
 */
public class Oponent extends GameObject {

    private final RMI stub;
    private final String user_id;
    private final Handler handler;
    private final Color color;
    
    public Oponent(RMI stub, String user_id, ID id, Handler handler) throws RemoteException {
        super(stub.getX(user_id), stub.getY(user_id), id);
        this.handler = handler;
        this.stub = stub;
        this.user_id = user_id;
	this.color = Color.RED;
    }

    @Override
    public float getX() {
        try {
            return stub.getX(user_id);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
            return -1;
        }
    }

    @Override
    public void setX(int x){
        try {
            stub.setX(user_id, x);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
        }
    }

    public float getY() {
        try {
            return stub.getY(user_id);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
            return -1;
        }
    }

    @Override
    public void setY(int y) {
        try {
            stub.setY(user_id, y);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
        }
    }

    @Override
    public float getVelX() {
        try {
            return stub.getVelX(user_id);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
            return -1;
        }
    }

    @Override
    public void setVelX(int velX) {
        try {
            stub.setVelX(user_id, velX);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
        }
    }

    @Override
    public float getVelY() {
        try {
            return stub.getVelY(user_id);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
            return -1;
        }
    }

    @Override
    public void setVelY(int velY) {
        try {
            stub.setVelY(user_id, velY);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
        }
    }

    @Override
    public void tick() {
        x = this.getX() + this.getVelX();
        y = this.getY() + this.getVelY();
	handler.addObject(new Trail(x, y, ID.Trail, color, 32, 32, 0.03f, handler));
    }

    @Override
    public void render(Graphics g) {
        try{
            float _x = stub.getX(user_id);
            float _y = stub.getY(user_id);
            g.setColor(color);
            g.fillRect((int) _x, (int) _y, 32, 32);
        } catch(RemoteException e) {
            System.err.println("Erro ::: " + e);
        }
    }

    @Override
    public Rectangle getBounds() {
        try{
            float _x = stub.getX(user_id);
            float _y = stub.getY(user_id);
            return new Rectangle((int) _x, (int) _y, 32, 32);
        } catch(RemoteException e) {
            System.err.println("Error ::: " + e.toString());
            return null;
        }
    }
    
}
