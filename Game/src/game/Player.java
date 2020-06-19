package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rmi.RMI;

/**
 *
 * @author Otavio e Luan
 */
public class Player extends GameObject{

    private enum PlayerSolicitationEnum {
        DEFAULT, YES, NO
    }
    
    Handler handler;
    protected RMI stub;
    protected String user_id;
    private PlayerSolicitationEnum result;
    private boolean setupDone;
  
    public Player(int x, int y, ID id, Handler handler) {
        this.handler = handler;
        setupDone = false;
        try {
            result = PlayerSolicitationEnum.DEFAULT;
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1010);
            stub = (RMI) registry.lookup("server");
            user_id = stub.instance_game_object(x, y);
            super.setId(id);
            setupDone = true;
        } catch (NotBoundException | RemoteException e) {
            this.on_error(e);
        }
    }

    public RMI getStub() {
        return stub;
    }
    
    @Override
    public void setX(int x) {
        try {
            stub.setX(user_id, (float) x);
        } catch (RemoteException e){
            this.on_error(e);
        }
    }

    @Override
    public void setY(int y) {
        try {
            stub.setY(user_id, (float) y);
        } catch (RemoteException e){
            this.on_error(e);
        }
    }
    
    @Override
    public float getX(){
        try{
            return stub.getX(user_id);
        } catch(RemoteException e) {
            on_error(e);
            return -1;
        }
    }
    
    @Override
    public float getY(){
        try{
            return stub.getY(user_id);
        } catch(RemoteException e) {
            on_error(e);
            return -1;
        }
    }

    @Override
    public void setVelX(int velX) {
        try {
            stub.setVelX(user_id, velX);
        } catch (RemoteException e){
            this.on_error(e);
        }
    }

    @Override
    public void setVelY(int velY) {
        try {
            stub.setVelY(user_id, velY);
        } catch (RemoteException e){
            this.on_error(e);
        }
    }
    
    @Override
    public float getVelX(){
        try{
            return stub.getVelX(user_id);
        } catch(RemoteException e) {
            on_error(e);
            return -1;
        }
    }
    
    @Override
    public float getVelY(){
        try{
            return stub.getVelY(user_id);
        } catch(RemoteException e) {
            on_error(e);
            return -1;
        }
    }
    
    @SuppressWarnings("empty-statement")
    public boolean startMultiplayerGame(){
        try{
            return stub.startMultiplayerGame(user_id);
        }
        catch (RemoteException e){
            on_error(e);
            return false;
        }
    }

    @Override
    public Rectangle getBounds() {
        try{
            float _x = stub.getX(user_id);
            float _y = stub.getY(user_id);
            return new Rectangle((int) _x, (int) _y, 32, 32);
        } catch(RemoteException e) {
            on_error(e);
            return null;
        }
    }
    
    public void instanceEnemy(){
        try {
            String myEnemyID = stub.getMyEnemyID(user_id);
            Oponent oponent = new Oponent(stub, myEnemyID, 
                    ID.Oponent, handler);
            handler.addObject(oponent);
        } catch (RemoteException ex) {
            System.err.println("Erro ::: " + ex);
        }
    }

    @Override
    public void tick() {
        try{
            if(stub.askingForMultiplayer(user_id)){
                switch(result){
                    case DEFAULT:
                        int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja começar um jogo multiplayer?");
                        if(dialogResult == JOptionPane.YES_OPTION)
                            result = PlayerSolicitationEnum.YES;
                        else
                            result = PlayerSolicitationEnum.NO;
                        break;
                    case YES:
                        HUD.HEALTH = 200;
                        stub.confirmMultiplayer(user_id);
                        handler.clearEnemies();
                        instanceEnemy();
                        handler.game.setOnGameState(Game.GameState.MULTI);
                        break;
                    case NO:
                        stub.negateMultiplayer(user_id);
                        break;
                }
            }
            
            float _x = stub.getX(user_id);
            float _y = stub.getY(user_id);

            float _velX = stub.getVelX(user_id);
            float _velY = stub.getVelY(user_id);

            _x += _velX;
            _y += _velY;

            stub.setX(user_id, Game.clamp(_x, 0, Game.WIDTH - 37));
            stub.setY(user_id, Game.clamp(_y, 0, Game.HEIGHT - 59));
            handler.addObject(new Trail(_x, _y, ID.Trail, Color.white, 32, 32, 0.2f, handler));

            collision();
        } catch(RemoteException e) {
            on_error(e);
        }
    }
    
    private void endGame(String text){
        Object[] options = {"OK"};
        JOptionPane.showOptionDialog(null, 
                text, "FIM DE JOGO", JOptionPane.PLAIN_MESSAGE, 
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
    }

    private void collision() {
        try {
            if(stub.won(user_id)){
                endGame("Você ganhou!");
                System.exit(0);
            }
            if(HUD.HEALTH == 0){
                stub.lose(user_id);
                endGame("Você perdeu!");
                System.exit(0);
            }
        } catch (RemoteException ex) {
            System.out.println("Erro ::: " + ex);
        }
        for (int i = 0; i < handler.object.size(); i++) {
            GameObject tempObject = handler.object.get(i);

            if (null != tempObject.getId()) 
                switch (tempObject.getId()) {
                    case Oponent:
                        try {
                            System.out.println("HUD = " + HUD.STATE_RAT);
                            if(stub.iAmRat(user_id)){
                                HUD.STATE_RAT = true;
                                if(getBounds().intersects(tempObject.getBounds())){
                                    HUD.HEALTH -= 0.5;
                                }
                            }
                            else {
                                HUD.STATE_RAT = false;
                                System.out.println("Gato");
                            }
                        } catch (RemoteException ex) {
                            System.err.println("Erro ::: " + ex);
                        }   break;
                    case BasicEnemy:
                        if (getBounds().intersects(tempObject.getBounds())) {
                            HUD.HEALTH -= 2;
                        }   break;
                    case FastEnemy:
                        if (getBounds().intersects(tempObject.getBounds())) {
                            HUD.HEALTH -= 1;
                        }   break;
                    case SmartEnemy:
                        if (getBounds().intersects(tempObject.getBounds())) {
                            HUD.HEALTH -= 3;
                        }   break;
                    case EnemyBoss:
                        if (getBounds().intersects(tempObject.getBounds())) {
                            HUD.HEALTH -= 5;
                        }   break;
                    default:
                        break;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        try{
            float _x = stub.getX(user_id);
            float _y = stub.getY(user_id);
            g.setColor(Color.WHITE);
            g.fillRect((int) _x, (int) _y, 32, 32);
        } catch(RemoteException e) {
            on_error(e);
        }
    }

    
    private void on_error(Exception e){
        System.err.println("Client exception: " + e.toString());
    }
}
