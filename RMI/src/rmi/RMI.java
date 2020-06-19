package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author Fabio R
 */
public interface RMI extends Remote {
    public String instance_game_object(float x, float y) 
            throws RemoteException;
    public void setX(String user_id, float x) throws RemoteException;
    public void setY(String user_id, float y) throws RemoteException;
    public float getX(String user_id) throws RemoteException;
    public float getY(String user_id) throws RemoteException;
    public void setVelX(String user_id, int velX) throws RemoteException;
    public void setVelY(String user_id, int velY) throws RemoteException;
    public float getVelX(String user_id) throws RemoteException;
    public float getVelY(String user_id) throws RemoteException;
    public boolean startMultiplayerGame(String user_id) throws RemoteException;
    public boolean askingForMultiplayer(String user_id) throws RemoteException;
    public void confirmMultiplayer(String user_id) throws RemoteException;
    public void negateMultiplayer(String user_id) throws RemoteException;
    public String getMyEnemyID(String user_id) throws RemoteException;
    public boolean iAmRat(String user_id) throws RemoteException;
    public void lose(String user_id) throws RemoteException;
    public boolean won(String user_id) throws RemoteException;
}
