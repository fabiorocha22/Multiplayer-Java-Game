package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;
import javax.swing.JFrame;

/**
 *
 * @author Otavio e Luan
 */

public class Game extends Canvas implements Runnable {
    
    public enum GameState {
        NONE, SINGLE, MULTI
    }
    
    private JFrame superFrame;
    
    private GameState onGameState;

    public static final int WIDTH = 800,

    HEIGHT = WIDTH / 16 * 9;

    private Thread thread;
    private boolean running = false;

    private final HUD hud;
    private final Random r;
    private final Handler handler;

    public Spawn spawn;
    private final Menu menu;

    public enum STATE {
        Menu,
        Game
    };
    
    public STATE gameState = STATE.Menu;

    public Game(JFrame frame) {
    	hud = new HUD();
	r = new Random();
	handler = new Handler(this);
        onGameState = GameState.NONE;
	spawn = new Spawn(handler, hud, onGameState);
	menu = new Menu(this, handler);
	super.addKeyListener(new KeyInput(handler, this));
	super.addMouseListener(menu);
        superFrame = frame;
    }

    public JFrame getSuperFrame() {
        return superFrame;
    }

    public GameState getOnGameState() {
        return onGameState;
    }

    public void setOnGameState(GameState onGameState) {
        spawn.setGameState(onGameState);
        this.onGameState = onGameState;
    }

    public synchronized void start() {
        thread = new Thread(this);
	thread.start();
	running = true;
    }

    public synchronized void stop() {
	try {
            thread.join();
            running = false;
	} catch (InterruptedException e) {
        }
    }

    @Override
    public void run() {
	this.requestFocus();
	long lastTime = System.nanoTime();
	double amountOfTicks = 60.0;
	double ns = 1000000000 / amountOfTicks;
	double delta = 0;
	long timer = System.currentTimeMillis();
	int frames = 0;
	while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
		delta--;
            }
            if (running) {
                render();
            }
            frames++;
            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
		frames = 0;
            }
	}
	stop();
    }

    private void tick() {
	handler.tick();

        if (gameState == STATE.Game) {
            hud.tick();
            spawn.tick();
	}
	else if (gameState == STATE.Menu) {
            menu.tick();
	}
    }

    private void render() {
    	BufferStrategy bs = this.getBufferStrategy();
	if (bs == null) {
            this.createBufferStrategy(3);
		return;
	}

	Graphics g = bs.getDrawGraphics();
	g.setColor(Color.BLACK);
	g.fillRect(0, 0, WIDTH, HEIGHT);
	handler.render(g);
	if (gameState == STATE.Game) {
            hud.render(g);
	}
	else if (gameState == STATE.Menu) {
            menu.render(g);
	}

	g.dispose();
	bs.show();
    }
    
    public static float clamp(float x, float min, float max) {
	if (x >= max)
            return x = max;
	else if (x <= min)
            return x = min;
	else
            return x;
    }
   
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        Game game = new Game(frame);

        frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.add(game);
        frame.setVisible(true);
        game.start();
    }

}
