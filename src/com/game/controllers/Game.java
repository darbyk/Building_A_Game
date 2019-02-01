package com.game.controllers;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.game.gameObjects.BouncingEnemy;
import com.game.gameObjects.Block;
import com.game.gameObjects.ID;
import com.game.gameObjects.Player;
import com.game.gameObjects.TrackingEnemy;
import com.game.interfacesAndAbstracts.GameObject;
import com.game.main.HUD;
import com.game.main.Handler;
import com.game.main.Menu;
import com.game.resources.ResourceLoader;
import com.game.resources.ResourceLoader.RESOURCE_TYPES;

public class Game extends Canvas implements Runnable{
	
	private static final long serialVersionUID = -473349850293143017L;
	private Thread thread;
	private boolean isRunning = false;
	private Handler handler;
	private HUD hud;
	private Menu menu;
	private BufferedImage level = null;
	private Random r;
	private Camera camera;
	private Player mainPlayer;
	
	private ResourceLoader resourceLoader;
	
	
	public static final int WINDOW_WIDTH = 640;
	public static final int WINDOW_HEIGHT = WINDOW_WIDTH / 12 * 9;
	
	public static final int GAME_WIDTH = 64 * 32;
	public static final int GAME_HEIGHT = 32 * 32;
	
	public enum STATE {
		Menu,
		Game,
		Help,
		Quit
	};
	
	public STATE gameState = STATE.Menu;
	
	public STATE getGameState()
	{
		return gameState;
	}
	
	public static void main(String args[])
	{
		Game myGame = new Game();
		Window w = new Window(WINDOW_WIDTH, WINDOW_HEIGHT, "Building a game");
		w.setFrame(myGame);
		myGame.start();
	}
	
	public Game()
	{
		r = new Random();
		resourceLoader = new ResourceLoader();
		handler = new Handler(resourceLoader);
		camera = new Camera(0,0);
		
		
		
		BufferedImageLoader loader = new BufferedImageLoader();
		
		level = loader.loadImage("/wizard_level_1_update.png");
		loadLevel(level);
		
		hud = new HUD(camera, mainPlayer);
		
		menu = new Menu(this, handler, resourceLoader.getSpriteSheetByType(RESOURCE_TYPES.WalkingWizard));
		
		this.addKeyListener(new KeyInput(handler, camera));
		this.addMouseListener(new MouseInput(handler, camera, resourceLoader.getSpriteSheetByType(RESOURCE_TYPES.FireBall), this));
		this.addMouseListener(menu);
		
	}
	
	public synchronized void start()
	{
		thread = new Thread(this);
		thread.start();
		isRunning = true;
	}
	
	
	public void run() 
	{
		//Don't have to click in the screen for keyboard to work
		this.requestFocus();
				
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(isRunning)
		{
			long now = System.nanoTime();
			double nsDiff = now - lastTime;
			delta += (now - lastTime) / ns;
			lastTime = now;
			while(delta >= 1){
				tick();
				delta--;
			}
			if(isRunning){
				render();
			}
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
//				System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
	}
	
	private void tick()
	{
		
		
		
		if(gameState == STATE.Game)
		{
			handler.tick();
			for(int i = 0; i < handler.getObjects().size(); i++)
			{
				if(handler.getObjects().get(i).getId() == ID.Player)
				{
					camera.tick(handler.getObjects().get(i));
				}
			}
			hud.tick();			
		}
		else if(gameState == STATE.Menu || gameState == STATE.Help)
		{
			menu.tick();
		}
		else if(gameState == STATE.Quit)
		{
			System.exit(1);
		}
		

	}
	
	private void render()
	{
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null)
		{
			this.createBufferStrategy(3);
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D) g;
		
		
		g.setColor(Color.DARK_GRAY);
		g.fillRect(0,0,WINDOW_WIDTH, WINDOW_HEIGHT);
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		if(gameState == STATE.Game){
			for(int i = 0; i < GAME_WIDTH; i+=32)
			{
				for(int j = 0; j < GAME_HEIGHT; j+=32)
				{
					g.drawImage(resourceLoader.getBufferedImageByType(RESOURCE_TYPES.StoneWall), i, j, null);
				}
			}
		}
		
		
		
		
		
		if(gameState == STATE.Game)
		{
			handler.render(g);
			hud.render(g);
			
		}
		else if(gameState == STATE.Menu || gameState == STATE.Help)
		{
			menu.render(g);
		}
		
		g2d.translate(-camera.getX(), -camera.getY());
		
		
		
		
		g.dispose();
		bs.show();
	}
	
	public void loadLevel(BufferedImage image)
	{
		int w = image.getWidth();
		int h = image.getHeight();
		
		Player playerObject = null;
		
		for(int xx = 0; xx < w; xx++)
		{
			for(int yy = 0; yy < h; yy++)
			{
				int pixel = image.getRGB(xx, yy);
				int red = (pixel >> 16) & 0xff;
				int green = (pixel >> 8) & 0xff;
				int blue = (pixel) & 0xff;
				
				if(red == 255)
				{
					new Block(xx*32, yy*32, ID.Block, handler, resourceLoader.getSpriteSheetByType(RESOURCE_TYPES.WoodWall));
				}
				if(blue == 255)
				{
					playerObject = new Player(xx*32, yy*32, ID.Player, handler, resourceLoader.getSpriteSheetByType(RESOURCE_TYPES.WalkingWizard));
				}
				if(green == 255)
				{
					new TrackingEnemy(xx*32, yy*32, ID.TrackingEnemy, handler, resourceLoader);
				}
				if(red == 250)
				{
					new BouncingEnemy(xx*32, yy*32, ID.BouncingEnemy, handler, resourceLoader.getSpriteSheetByType(RESOURCE_TYPES.WalkingBlueImp));
				}
			}
		}
		mainPlayer = playerObject;
	}
	
	public synchronized void stop()
	{
		try
		{
			thread.join();
			isRunning = false;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static float clamp(float value, int min, int max)
	{
		if(value < min) return min;
		if(value > max) return max;
		return value;
	}
	
	public BufferedImage getLevel()
	{
		return level;
	}
}
