package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import game.Game.STATE;

/**
 *
 * @author Otavio e Luan
 */
public class Menu extends MouseAdapter{
    
    class ElementObject {
        private final int x;
        private final int y;
        private final int width;
        private final int heigh;

        public ElementObject(int x, int y, int width, int heigh) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.heigh = heigh;
        }
        

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getWidth() {
            return width;
        }

        public int getHeigh() {
            return heigh;
        }
        
        
    }

    private final Game game;
    private final Handler handler;
    private final Random r;
    private final ElementObject menu_string;
    private final ElementObject singlePlayer;
    private final ElementObject singlePlayer_string;
    private final ElementObject multiPlayer;
    private final ElementObject multiPlayer_string;
    private final ElementObject quit;
    private final ElementObject quit_string;

    public Menu(Game game, Handler handler) {
	r = new Random();
	this.game = game;
	this.handler = handler;
        menu_string = new ElementObject((Game.WIDTH / 2) - 96, 75, 0, 0);
        singlePlayer = new ElementObject((Game.WIDTH / 2) - 120, 140, 200, 60);
        singlePlayer_string = new ElementObject((Game.WIDTH / 2) - 113, 177, 0, 0);
        multiPlayer = new ElementObject((Game.WIDTH / 2) - 120, 210, 200, 60);
        multiPlayer_string = new ElementObject((Game.WIDTH / 2) - 95, 250, 0, 0);
        quit = new ElementObject((Game.WIDTH / 2) - 120, 300, 200, 60);
        quit_string = new ElementObject((Game.WIDTH / 2) - 50, 177 + 80 + 80, 0, 0);
    }

    @Override
    public void mousePressed(MouseEvent e) {
	int mx = e.getX();
	int my = e.getY();
	if (game.gameState == STATE.Menu) {
            if (mouseOver(mx, my, singlePlayer.getX(), singlePlayer.getY(), 
                    singlePlayer.getWidth(), singlePlayer.getHeigh())) {
                game.gameState = STATE.Game;
                handler.addObject(new Player(Game.WIDTH / 2 - 16, Game.HEIGHT / 
                        2 - 16, ID.Player, handler));
		handler.addObject(new BasicEnemy(r.nextInt(Game.WIDTH), 
                        r.nextInt(Game.HEIGHT), ID.BasicEnemy, handler));
                game.setOnGameState(Game.GameState.SINGLE);
            }

            if(mouseOver(mx, my, multiPlayer.getX(), multiPlayer.getY(), 
                    multiPlayer.getWidth(), multiPlayer.getHeigh())){
                game.gameState = STATE.Game;
                Player my_player; 
                my_player = new Player(0, Game.HEIGHT / 2 - 16, 
                        ID.Player, handler);
                if(!my_player.startMultiplayerGame()){
                    System.exit(0);
                }
                else{
                    handler.addObject(my_player);
                    my_player.instanceEnemy();
                    game.setOnGameState(Game.GameState.MULTI);
                }
            }
            
            if (mouseOver(mx, my, quit.getX(), quit.getY(), quit.getWidth(), 
                    quit.getHeigh())) {
            	System.exit(0);
            }
	}
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    private boolean mouseOver(int mx, int my, int x, int y, int width, int height) {
    	if (mx > x && mx < x + width) {
                return my > y && my < y + height;
	} else
            return false;
    }

    public void tick() {

    }
    
    public void render(Graphics g) {
        Font font = new Font("Arial", 1, 50);
	Font font2 = new Font("Arial", 1, 30);
	g.setFont(font);
	g.setColor(Color.WHITE);
        
	g.drawString("MENU", menu_string.getX(), menu_string.getY());
	g.setFont(font2);
	g.drawRect(singlePlayer.getX(), singlePlayer.getY(), 
                singlePlayer.getWidth(), singlePlayer.getHeigh());
	g.drawString("Single Player", singlePlayer_string.getX(), 
                singlePlayer_string.getY());
	g.drawRect(multiPlayer.getX(), multiPlayer.getY(), 
                multiPlayer.getWidth(), multiPlayer.getHeigh());
	g.drawString("Multiplayer", multiPlayer_string.getX(), 
                multiPlayer_string.getY());
	g.drawRect(quit.getX(), quit.getY(), quit.getWidth(), quit.getHeigh());
	g.drawString("Quit", quit_string.getX(), quit_string.getY());
    }

}
