/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servergame;

/**
 *
 * @author Fabio R
 */
public class GameObject {
    private String user_id;
    private float x;
    private float y;
    private float velX;
    private float velY;
    private StateEnum state;
    private MultiplayerEnum multiState;
    private boolean wonGame;

    public GameObject(String user_id, float x, float y) {
        this.user_id = user_id;
        this.x = x;
        this.y = y;
        this.state = StateEnum.DEFAULT;
        this.multiState = MultiplayerEnum.DEFAULT;
    }
    
    public String getUser_id() {
        return user_id;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        // System.out.println("alterou X para " + this.x);
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        // System.out.println("alterou Y para " + this.y);
    }

    public float getVelX() {
        return velX;
    }

    public void setVelX(float velX) {
        this.velX = velX;
        // System.out.println("alterou velX para " + this.velX);
    }

    public float getVelY() {
        return velY;
    }

    public void setVelY(float velY) {
        this.velY = velY;
        // System.out.println("alterou velY para " + this.velY);
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public MultiplayerEnum getMultiState() {
        return multiState;
    }

    public void setMultiState(MultiplayerEnum multiState) {
        this.multiState = multiState;
    }
    
    public void multiplayer(){
        this.multiState = MultiplayerEnum.ACCEPTED;
        
    }
    
    public void askForGame(){
        this.multiState = MultiplayerEnum.ASKING;
    }

    public boolean isWonGame() {
        return wonGame;
    }

    public void setWonGame(boolean wonGame) {
        this.wonGame = wonGame;
    }
}
