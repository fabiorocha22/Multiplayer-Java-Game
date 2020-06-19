/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servergame;

import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import rmi.RMI;
/**
 *
 * @author Fabio R
 */
public class Server extends UnicastRemoteObject implements RMI {
    
    LinkedList<GameObject> objects;
    boolean multiplayers;
    boolean multiplayerSwitch;
    GameObject p1 = null;
    GameObject p2 = null;
    
    public Server() throws RemoteException {
        super();
        objects = new LinkedList<>();
        multiplayers = false;
    }

    @Override
    public String instance_game_object(float x, float y) throws RemoteException {
        String user_id = UUID.randomUUID().toString();
        GameObject g = new GameObject(user_id, x, y);
        objects.add(g);
        return user_id;
    }

    @Override
    public void setX(String user_id, float x) throws RemoteException {
        objects.stream().filter((obj) -> (obj.getUser_id().equals(user_id))).forEachOrdered((obj) -> {
            obj.setX(x);
        });
    }

    @Override
    public void setY(String user_id, float y) throws RemoteException {
        objects.stream().filter((obj) -> (obj.getUser_id().equals(user_id))).forEachOrdered((obj) -> {
            obj.setY(y);
        });
    }

    @Override
    public float getX(String user_id) throws RemoteException {
        for (GameObject obj : objects) {
            if(obj.getUser_id().equals(user_id))
                return obj.getX();
        }
        return -1;
    }

    @Override
    public float getY(String user_id) throws RemoteException {
        for (GameObject obj : objects) {
            if(obj.getUser_id().equals(user_id))
                return obj.getY();
        }
        return -1;
    }

    @Override
    public void setVelX(String user_id, int velX) throws RemoteException {
        objects.stream().filter((obj) -> (obj.getUser_id().equals(user_id))).forEachOrdered((obj) -> {
            obj.setVelX(velX);
        });
    }

    @Override
    public void setVelY(String user_id, int velY) throws RemoteException {
        objects.stream().filter((obj) -> (obj.getUser_id().equals(user_id))).forEachOrdered((obj) -> {
            obj.setVelY(velY);
        });
    }

    @Override
    public float getVelX(String user_id) throws RemoteException {
        for (GameObject obj : objects) {
            if(obj.getUser_id().equals(user_id))
                return obj.getVelX();
        }
        return -1;
    }

    @Override
    public float getVelY(String user_id) throws RemoteException {
        for (GameObject obj : objects) {
            if(obj.getUser_id().equals(user_id))
                return obj.getVelY();
        }
        return -1;
    }

    @Override
    public void confirmMultiplayer(String user_id) throws RemoteException {
        objects.stream().filter((obj) -> (obj.getUser_id().equals(user_id))).forEachOrdered((obj) -> {
            obj.setMultiState(MultiplayerEnum.ACCEPTED);
        });
    }

    @Override
    public void negateMultiplayer(String user_id) throws RemoteException {
        objects.stream().filter((obj) -> (obj.getUser_id().equals(user_id))).forEachOrdered((obj) -> {
            obj.setMultiState(MultiplayerEnum.NEGATED);
        });
    }

    @Override
    public String getMyEnemyID(String user_id) throws RemoteException {
        for (GameObject obj : objects) {
            if(!obj.getUser_id().equals(user_id))
                return obj.getUser_id();
        }
        return new String();
    }

    @Override
    public boolean iAmRat(String user_id) throws RemoteException {
        if(p1 != null && p2 != null){
            if(p1.getUser_id().equals(user_id))
                return multiplayerSwitch;
            else return !multiplayerSwitch;
        }
        return false;
    }

    @Override
    public void lose(String user_id) throws RemoteException {
        this.objects.forEach((obj) -> {
            if(!obj.getUser_id().equals(user_id)){
                obj.setWonGame(true);
            }
            else if(obj.getUser_id().equals(user_id)){
                obj.setWonGame(false);
            }
        });
    }
    
    @Override
    public boolean won(String user_id) throws RemoteException {
        for (GameObject obj : objects) {
            if(obj.getUser_id().equals(user_id)){
                return obj.isWonGame();
            }
        }
        return false;
    }

    @Override
    public boolean askingForMultiplayer(String user_id) throws RemoteException {
        for (GameObject obj : objects) {
            if(obj.getUser_id().equals(user_id))
                switch(obj.getMultiState()){
                    case ASKING:
                        return true;
                    default:
                        return false;
                }
        }
        return false;
    }
    
    @Override
    @SuppressWarnings("empty-statement")
    public boolean startMultiplayerGame(String user_id) throws RemoteException {
        if(this.objects.size() < 2){
            return false;
        }
        else {
            GameObject player1 = null;
            GameObject player2 = null;
            for(GameObject obj : this.objects){
                if(!obj.getUser_id().equals(user_id)){
                    player2 = obj;
                }
                else if(obj.getUser_id().equals(user_id)){
                    player1 = obj;
                }
            }
            if(player1 == null || player2 == null){
                return false;
            }
            else {
                player1.multiplayer();
                player2.askForGame();
                while(player2.getMultiState() == MultiplayerEnum.ASKING){
                    System.out.println(player2.getMultiState().toString());
                };
                if(player2.getMultiState() == MultiplayerEnum.NEGATED){
                    player2.setMultiState(MultiplayerEnum.DEFAULT);
                    player1.setMultiState(MultiplayerEnum.DEFAULT);
                    return false;
                }
                else {
                    player2.multiplayer();
                    player1.setState(StateEnum.CAT);
                    player2.setState(StateEnum.RAT);
                    multiplayers = true;
                    multiplayerSwitch = false;
                    p1 = player1;
                    p2 = player2;
                    ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                    Runnable periodicTask = new Runnable() {
                        @Override
                        public void run() {
                            if(multiplayers == true){
                                multiplayerSwitch = !multiplayerSwitch;
                            }
                        }
                    };
                    executor.scheduleAtFixedRate(periodicTask, 0, 7, TimeUnit.SECONDS);
                    return true;
                }
            }
        }
    }
    
    public static void main(String[] args) {
        try {
            Server obj = new Server();            
            Registry registry = LocateRegistry.createRegistry(1010);
            registry.rebind("server", obj);
            System.out.println("Server ready!");
        } catch (RemoteException e) {
            System.err.println("Server exception: " + e.toString());
        }
    }
    
}
