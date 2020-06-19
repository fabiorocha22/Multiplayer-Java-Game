package game;

import game.FastEnemy;
import game.SmartEnemy;
import game.EnemyBoss;
import game.BasicEnemy;
import java.util.Random;

/**
 *
 * @author Otavio e Luan
 */
public class Spawn {
    private final Handler handler;
    private final HUD hud;
    private final Random r;
    private int scoreKeep = 0;
    private Game.GameState gameState;

    public Spawn(Handler handler, HUD hud, Game.GameState gameState) {
	r = new Random();
	this.handler = handler;
	this.hud = hud;
        this.gameState = gameState;
    }

    public void setGameState(Game.GameState gameState) {
        this.gameState = gameState;
    }
	
    public void tick() {
        switch(gameState){
            case SINGLE:
               scoreKeep++;
                if (scoreKeep >= 400) {
                    scoreKeep = 0;
                    hud.setLevel(hud.getLevel() + 1);
                    switch (hud.getLevel()) {
                        case 2:
                            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
                            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
                            break;
                        case 3:
                            handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
                            break;
                        case 4:
                            handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
                            break;
                        case 5:
                            handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
                            break;
                        case 6:
                            handler.clearEnemies();
                            handler.addObject(new EnemyBoss(Game.WIDTH / 2, -96, ID.EnemyBoss, handler));
                            break;
                        case 7:
                            handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.BasicEnemy, handler));
                            break;
                        case 8:
                            handler.addObject(new FastEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.FastEnemy, handler));
                            break;
                        case 9:
                            handler.addObject(new SmartEnemy(r.nextInt(Game.WIDTH - 50), r.nextInt(Game.HEIGHT - 50), ID.SmartEnemy, handler));
                            break;

                        default:
                            break;
                    }
                }
                break;
            case MULTI:
                break;
            default:
                break;
        }
        
	
    }
}
