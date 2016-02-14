package com.mojang.ld22;


import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;
import java.util.List;


import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.MultiPlayer;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.gfx.SpriteSheet;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.screen.DeadMenu;
import com.mojang.ld22.screen.LevelTransitionMenu;
import com.mojang.ld22.screen.Menu;
import com.mojang.ld22.screen.TitleMenu;
import com.mojang.ld22.screen.WonMenu;
import com.mojang.ld22.screen.SplashMenu;
import com.mojang.ld22.screen.FreeMenu;

import de.thejackimonster.ld22.leveltree.Skill;
import de.thejackimonster.ld22.leveltree.mod_leveltree;
import de.thejackimonster.ld22.loadandsave.SaveLevel;
import de.thejackimonster.ld22.matrix.Matrix;
import de.thejackimonster.ld22.modloader.ModLoader;
import de.thejackimonster.ld22.modloader.UseMods;
import de.thejackimonster.ld22.story.dialog.NPC;

import de.zelosfan.ld22.server.Server;


public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;
	
	public final String VERSION = "1.1.0";
	public static final String NAME = "InfinityTale";
	public static final int HEIGHT = 180;
	public static final int WIDTH = 260;
	public static final int SCALE = 3;
	public static final double CONNECTIONREFRESH = 1;
	public static final int SERVERSOCKET = 4000; 
	public static String CLIENTCONNECTHOST = "localhost";
	
	public int respawnx = 0;
	public int respawny = 0;
	
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	private boolean running = false;
	public Player player;
	public Player player2;
	
	public Player multiplayer[] = new Player[256];
	public int multiplayercount;
	
	public Screen screen;
	private Screen lightScreen;
	public InputHandler input = new InputHandler(this);

	public boolean firsttime  = true;

	private int[] colors = new int[256];
	private int tickCount = 0;
	public int gameTime = 0;
	


	public boolean dcimage;
	
	public Level level;
	public Level[] levels = new Level[6];
	public int currentLevel = 3;
	public int[] mapoptions;
	public int lightlvl = 8;
	public boolean rising = false;

	public int mpstate = 0; // 0=No mp;1=Client;2=Server
	
	public boolean testmode = false; //stops unfocus

	public Menu menu;
	public int playerDeadTime;
	private int pendingLevelChange;
	private int wonTimer = 0;
	
	public boolean issavingmap = false;
	public boolean isloadingmap = false;
	
	public boolean autosave = true;
	
	public boolean hasWon = false;
	public boolean isApplet = false;

	public Server server;
	public Runnable client;

	
	
	// Client vars
	   public int playerid = 0;
	   public boolean newdrophelper = false;
	   public boolean positionupdated = true;
	   public boolean mapupdated = false;
	   public boolean tookdrops = false;
	   public boolean setpos = false;
	   public boolean multiplayerrespawned = false;
	   public int atkcount = 0;
	   public int[] atkxcoord = new int[100];
	   public int[] atkycoord = new int[100];
	   public int[] atkx2coord = new int[100];
	   public int[] atky2coord = new int[100];
	   public int[] atkdmg = new int[100];
	   public boolean atkrefresh = false;
	   public boolean mobdied = false;
	   public int[] clientRequests = new int[255];
	   public int requestcount = 0;
	   public int packagecps = 0;
	//End of Client var

	public void setMenu(Menu menu) {
		this.menu = menu;
		if (menu != null) menu.init(this, input);
	}

	public void start(boolean flag) {
		running = true;
		isApplet = flag;
		new Thread(this).start();
	}

	public void stop() {
		running = false;
	}

	public void resetGame() {
		playerDeadTime = 0;
		wonTimer = 0;
		gameTime = 0;
		hasWon = false;

		levels = new Level[6];
		currentLevel = 3;

		if (!firsttime) {
		 levels[5] = new Matrix(false,this,this.mapoptions[0]*64, this.mapoptions[0]*64, -4, null,this.mapoptions[0],this.mapoptions[1],this.mapoptions[2],this.mapoptions[3],this.mapoptions[4]);		
		 levels[4] = new Matrix(false,this,this.mapoptions[0]*64, this.mapoptions[0]*64, 1, null,this.mapoptions[0],this.mapoptions[1],this.mapoptions[2],this.mapoptions[3],this.mapoptions[4]);
		 levels[3] = new Matrix(false,this,this.mapoptions[0]*64, this.mapoptions[0]*64, 0, levels[4],this.mapoptions[0],this.mapoptions[1],this.mapoptions[2],this.mapoptions[3],this.mapoptions[4]);
		 levels[2] = new Matrix(false,this,this.mapoptions[0]*64, this.mapoptions[0]*64, -1, levels[3],this.mapoptions[0],this.mapoptions[1],this.mapoptions[2],this.mapoptions[3],this.mapoptions[4]);
		 levels[1] = new Matrix(false,this,this.mapoptions[0]*64, this.mapoptions[0]*64, -2, levels[2],this.mapoptions[0],this.mapoptions[1],this.mapoptions[2],this.mapoptions[3],this.mapoptions[4]);
		 levels[0] = new Matrix(false,this,this.mapoptions[0]*64, this.mapoptions[0]*64, -3, levels[1],this.mapoptions[0],this.mapoptions[1],this.mapoptions[2],this.mapoptions[3],this.mapoptions[4]);
		} else
		{
			levels[5] = new Matrix(false,this,128, 128, -4, null,2,2,2,2,2);
			levels[4] = new Matrix(false,this,128, 128, 1, null,2,2,2,2,2);
			levels[3] = new Matrix(false,this,128, 128, 0, levels[4],2,2,2,2,2);
			levels[2] = new Matrix(false,this,128, 128, -1, levels[3],2,2,2,2,2);
			levels[1] = new Matrix(false,this,128, 128, -2, levels[2],2,2,2,2,2);
			levels[0] = new Matrix(false,this,128, 128, -3, levels[1],2,2,2,2,2);			
		}
		
		level = levels[currentLevel];
		player = new Player(this, input);
		
		if (mpstate != 0) {
			 player2 = new MultiPlayer(this);
			 level.add(player2);
		 
		 if (mpstate == 2){
			 player2.x = player.x;
			 player2.y = player.y;
		 }
		 
		}
		
		player.findStartPos(level);
		
		respawnx = player.x;
		respawny = player.y;

		level.add(player);

		
		
		for (int i = 0; i < 5; i++) {
			levels[i].trySpawn(4000);
		}
		firsttime = false;
	}

	private void init() {
		int pp = 0;
		for (int r = 0; r < 6; r++) {
			for (int g = 0; g < 6; g++) {
				for (int b = 0; b < 6; b++) {
					int rr = (r * 255 / 5);
					int gg = (g * 255 / 5);
					int bb = (b * 255 / 5);
					int mid = (rr * 30 + gg * 59 + bb * 11) / 100;

					int r1 = ((rr + mid * 1) / 2) * 230 / 255 + 10;
					int g1 = ((gg + mid * 1) / 2) * 230 / 255 + 10;
					int b1 = ((bb + mid * 1) / 2) * 230 / 255 + 10;
					colors[pp++] = r1 << 16 | g1 << 8 | b1;

				}
			}
		}
		try {
			screen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icons.png"))), this);
			lightScreen = new Screen(WIDTH, HEIGHT, new SpriteSheet(ImageIO.read(Game.class.getResourceAsStream("/icons.png"))), this);
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		resetGame();
		setMenu(new SplashMenu());
	}

	public void run() {
		long lastTime = System.nanoTime();
		double unprocessed = 0;
		double nsPerTick = 1000000000.0 / 60;
		int frames = 0;
		int ticks = 0;
		long lastTimer1 = System.currentTimeMillis();

		init();

		while (running) {
			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;
			boolean shouldRender = true;
			
			if (unprocessed >= CONNECTIONREFRESH) {
				if (this.mpstate == 1) {	
				 if (client != null) {	
					 new Thread(client).start();
				 }
				}
			}
			
			
			while (unprocessed >= 1) {
				ticks++;
				try {
					tick();
				} catch (Exception e) {
					e.printStackTrace();
				}
				unprocessed -= 1;
				shouldRender = true;
			}


			
			try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			if (shouldRender) {
				frames++;
				render();
			}

			if (System.currentTimeMillis() - lastTimer1 > 1000) {
				lastTimer1 += 1000;
			    System.out.println("[GAME]"+ticks + " ticks, " + frames + " fps, "+packagecps+" pps");
				frames = 0;
				ticks = 0;
				packagecps = 0;

			}
		}
	}

	public void tick() throws Exception {
		tickCount++;
		
	/**	if (tickCount % CONNECTIONREFRESH == 0) {
			if (this.mpstate == 1) {	
			 if (client != null) {	
				 new Thread(client).start();
			 }
			}
		}
		*/
		
		if (!hasFocus()) {
			input.releaseAll();
		} else {
			if (!player.removed && !hasWon) gameTime++;
			
			input.tick();
			
			
			
			if (menu != null && (!(menu instanceof FreeMenu))) {
				menu.tick();
			} else {
				if(menu != null && (menu instanceof FreeMenu)) {
					menu.tick();
				}
				if (player.removed) {
					playerDeadTime++;
					if (playerDeadTime > 60) {
						setMenu(new DeadMenu());
					}
				} else {
					if (pendingLevelChange != 0) {
						setMenu(new LevelTransitionMenu(pendingLevelChange));
						pendingLevelChange = 0;
					}
				}
				if (wonTimer > 0) {
					if (--wonTimer == 0) {
						setMenu(new WonMenu());
					}
				}
				level.tick();
				Tile.tickCount++;
				
				
				if (tickCount % 2400 == 0 && mpstate != 1) {
					if (rising) {
						lightlvl++;
						
						if (lightlvl >= 8) {
							rising = false;
						}
						
					    if (this.lightlvl == 8 && (!this.isApplet) && autosave) {
					     SaveLevel save = new SaveLevel("world", this);
					     save.start();
					    }
						
					} else {
						lightlvl--;
						
						if (lightlvl <= -2) {
							rising = true;
						}
					}
					
				    if (this.mpstate == 2) {
				    	this.server.updatemap();
				    }
				    

				    
					if(lightlvl == 4 && rising && level.depth == 0) {
						for(int i = 0; i < level.entities.size(); i++) {
							if(level.entities.get(i) instanceof Mob) {
								if(!(level.entities.get(i) instanceof Player) && !(level.entities.get(i) instanceof NPC)) {
									level.entities.get(i).remove();
								}
							}
						}	
					 }
					 if(lightlvl <= 3 && level.depth == 0) {
						level.trySpawn(1000);
					} else if(lightlvl > 4 && level.depth == 0) {
						level.trySpawn(200);
					}
				}
			}
		}
		ModLoader.tickGame(this);
	}

	public void changeLevel(int dir) {
		level.remove(player);
		if (mpstate == 2) level.remove(player2);
		currentLevel += dir;
		level = levels[currentLevel];
		player.x = (player.x >> 4) * 16 + 8;
		if (mpstate == 2) player2.x = (player.x >> 4) * 16 + 8;
	    player.y = (player.y >> 4) * 16 + 8;
	    if (mpstate == 2) player2.x = (player.x >> 4) * 16 + 8;
		level.add(player);
		if (mpstate == 2) level.add(player2);
	}

	public void changeLevel(int dir,boolean bol) {
		level.remove(player);
		if (mpstate == 2) level.remove(player2);
		currentLevel = dir;
		level = levels[dir];
		player.x = (player.x >> 4) * 16 + 8;
		if (mpstate == 2) player2.x = (player.x >> 4) * 16 + 8;
	    player.y = (player.y >> 4) * 16 + 8;
	    if (mpstate == 2) player2.x = (player.x >> 4) * 16 + 8;
		level.add(player);
		if (mpstate == 2) level.add(player2);
		
		if (dir == 5){
			level.trySpawn(1000);
		}
	}	
	
	public void render() {
	BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}

		
		if (mpstate != 0) {
			player2.render(screen);
		}
		
		int xScroll = player.x - screen.w / 2;
		int yScroll = player.y - (screen.h - 8) / 2;
		if (xScroll < 16) xScroll = 16;
		if (yScroll < 16) yScroll = 16;
		if (xScroll > level.w * 16 - screen.w - 16) xScroll = level.w * 16 - screen.w - 16;
		if (yScroll > level.h * 16 - screen.h - 16) yScroll = level.h * 16 - screen.h - 16;
		if (currentLevel > 3) {
			int col = Color.get(20, 20, 121, 121);
			for (int y = 0; y < 34; y++)
				for (int x = 0; x < 40; x++) {
					screen.render(x * 8 - ((xScroll / 4) & 7), y * 8 - ((yScroll / 4) & 7), 0, col, 0);
				}
		}
		
		int ll = lightlvl;

		int gl = level.grassSet;
		int dl = level.dirtSet;
		int sl = level.sandSet;

		if(ll < 1 && level.depth == 0) {
			gl -= 111;
		}

		level.grassColor = gl;
		level.dirtColor = dl;
		level.sandColor = sl;

		level.renderBackground(screen, xScroll, yScroll);
		level.renderSprites(screen, xScroll, yScroll);

		if (currentLevel < 3) {
			lightScreen.clear(0);
			
			level.renderLight(lightScreen, xScroll, yScroll,0);			
		    
			if (!player.nodarkness) {
			 screen.overlay(lightScreen, xScroll, yScroll);
		    }
		}

		
		if (lightlvl < 3) {
	 	 lightScreen.clear(0);

	 	 level.monsterDensity = 4+lightlvl;
	  	 level.renderLight(lightScreen, xScroll, yScroll,lightlvl*7+2);
	  	 if(!player.nodarkness) {
	  	  screen.overlay(lightScreen, xScroll, yScroll);
	  	 }
		}
		
		if ( !(menu instanceof TitleMenu) )  {
		 renderGui();
		} else {
		 menu.render(screen);
		// level.remove(player);
		}
		int rw = screen.w-16;
		if (dcimage){
        screen.render(rw-0, 0, 28*32, Color.get(-1, 90, 80, 90), 0);
        screen.render(rw+8, 0, 1+28*32, Color.get(-1, 90, 80, 90), 0);
        screen.render(rw-0, 8, 29*32, Color.get(-1, 90, 80, 90), 0);
        screen.render(rw+8, 8, 1+29*32, Color.get(-1, 90, 80, 90), 0);
		}
		if (isloadingmap){
	        screen.render(rw-0, 0, 2+28*32, Color.get(-1, 440, 150, 550), 0);
	        screen.render(rw+8, 0, 3+28*32, Color.get(-1, 440, 150, 550), 0);
	        screen.render(rw-0, 8, 2+29*32, Color.get(-1, 440, 150, 550), 0);
	        screen.render(rw+8, 8, 3+29*32, Color.get(-1, 440, 150, 550), 0);
		}
    	if (issavingmap){
	        screen.render(rw-0, 0, 4+28*32, Color.get(-1, 111, 222, 999), 0);
	        screen.render(rw+8, 0, 5+28*32, Color.get(-1, 111, 222, 999), 0);
	        screen.render(rw-0, 8, 4+29*32, Color.get(-1, 111, 222, 999), 0);
	        screen.render(rw+8, 8, 5+29*32, Color.get(-1, 111, 222, 999), 0);
		}
		if (!hasFocus() && !testmode) renderFocusNagger();

		for (int y = 0; y < screen.h; y++) {
			for (int x = 0; x < screen.w; x++) {
				int cc = screen.pixels[x + y * screen.w];
				if (cc < 255) pixels[x + y * WIDTH] = colors[cc];
			}
		}

		int ww = WIDTH * 3;
		int hh = HEIGHT * 3;
		int xo = (getWidth() - ww) / 2;
		int yo = (getHeight() - hh) / 2;
		
		if(bs != null) {
			Graphics g = bs.getDrawGraphics();
			g.fillRect(0, 0, getWidth(), getHeight());
			
			g.drawImage(image, xo, yo, ww, hh, null);
			g.dispose();
			
			bs.show();
		}
		
		
	}

	private void renderGui() {


		for (int i = 0; i < player.maxHealth; i++) {
			if (i < player.health)
				screen.render(i * 8,0, 0 + 12 * 32, Color.get(-1, 200, 500, 533), 0);
			else
				screen.render(i * 8,0, 0 + 12 * 32, Color.get(-1, 100, 000, 000), 0);
		}
		for (int i = 0; i < player.maxStamina; i++) {
			if (player.staminaRechargeDelay > 0) {
				if (player.staminaRechargeDelay / 4 % 2 == 0)
					screen.render(i * 8, 8, 1 + 12 * 32, Color.get(-1, 555, 000, 000), 0);
				else
					screen.render(i * 8,  8, 1 + 12 * 32, Color.get(-1, 110, 000, 000), 0);
			} else {
				if (i < player.stamina)
					screen.render(i * 8, 8, 1 + 12 * 32, Color.get(-1, 220, 550, 553), 0);
				else
					screen.render(i * 8, 8, 1 + 12 * 32, Color.get(-1, 110, 000, 000), 0);
			}
		}
		for (int i = 1; i < 11; i++) {
			if (player.exp >= player.expperlevel/10*i)
				screen.render((i-1) * 8,14, 2 + 12 * 32, Color.get(-1, 90, 0, 0), 0);
			else
				screen.render((i-1) * 8,14, 2 + 12 * 32, Color.get(-1, 8, 000, 000), 0);
		}
		Font.draw(String.valueOf(player.plevel), screen, 4*8 + 4, 22, Color.get(-1, 111, 222, 333));
		if (player.activeItem != null) {
			
				for (int x = 0; x <= player.activeItem.getName().length(); x++) {
					screen.render(x * 8+80, screen.h - 8, 0 + 12 * 32, Color.get(0, 0, 0, 0), 0);
				}
			
			player.activeItem.renderInventory(screen, 10 * 8, screen.h - 8);
		}

		for (int i = 0;i < 9;i++) {
			screen.render(i * 8, screen.h - 8, 0 + 11 * 32, Color.get(1, 2, 3, 4), 0);
			if (player.Slots[i] != null) {
			  
			  player.Slots[i].renderIcon(screen, 0+i*8, screen.h-8);
			}
		}
		if(Skill.weapon1.isCompleteDone()) {
			String msg = "Use the";
			if(mod_leveltree.coolDownSkill > 0) {
				msg = "Wait " + String.valueOf(mod_leveltree.coolDownSkill) + "s for";
			}
			Font.draw(msg, screen, screen.w - msg.length()*8, 4, Color.get(-1, -1, -1, 441));
			String msg1 = "Power-Attack!";
			Font.draw(msg1, screen, screen.w - msg1.length()*8, 12, Color.get(-1, -1, -1, 441));
		}

		//start Minimap
		if (screen.pixels != null) {

		byte[] map = level.tiles;
		int rng = 12;
		int ys=-1;
		int xs=0;
		int bl=0;
        for (int y = (player.y/16)-rng; y < (player.y/16)+rng ; y++) {
	    	ys++;
	    	xs = screen.w-rng*2-1;
			for (int x = (player.x/16)-rng; x < (player.x/16)+rng; x++) {
				xs++;
				int si = (xs + (ys * (screen.w)));
			    int i = x + y * 128;
				if (i < 16384 && i > 0 && si>0) {

					screen.pixels[si] = Color.get(555);
			    	if (level.tiles[i] == Tile.water.id) screen.pixels[si] = 9;
					if (level.tiles[i] == Tile.grass.id) screen.pixels[si] = 30;
					if (level.tiles[i] == Tile.rock.id) screen.pixels[si] = Color.get(444);
					if (level.tiles[i] == Tile.dirt.id) screen.pixels[si] = Color.get(310);
					if (level.tiles[i] == Tile.hole.id) screen.pixels[si] = Color.get(210);
					if (level.tiles[i] == Tile.sand.id) screen.pixels[si] = Color.get(540);
					if (level.tiles[i] == Tile.tree.id) screen.pixels[si] = 20;
					if (level.tiles[i] == Tile.cactus.id) screen.pixels[si] = 20;
					if (level.tiles[i] == Tile.lava.id) screen.pixels[si] = Color.get(300);
					if (level.tiles[i] == Tile.ironOre.id) screen.pixels[si] = Color.get(555);
					if (level.tiles[i] == Tile.goldOre.id) screen.pixels[si] = Color.get(555);
	
					List<Entity> entitylist = level.getEntities(x*16-4, y*16-4, x*16+4, y*16+4);
					for(int i2 = 0; i2 < entitylist.size(); i2++) {					
					   Entity hit = entitylist.get(i2);
					   if(hit != null) {
						   if (hit instanceof Player) {
							   screen.pixels[si] = Color.get(5);						   
						   }else if (hit instanceof Mob) {
							   screen.pixels[si] = Color.get(500);
						   }
					   }				
					}
					
					

					
				
				} else {
					screen.pixels[si] = 9;
				}
				if (xs == screen.w-rng*2 || ys == 0 || ys == rng*2-1 || xs == screen.w-rng*2+rng*2-1) {
					 screen.pixels[si] = Color.get(555);	
				}
			}
	    }

		}
		//end Minimap
		
		
		if (menu != null) {
			menu.render(screen);
		}
	}

	private void renderFocusNagger() {
		String msg = "Click to focus!";
		int xx = (WIDTH - msg.length() * 8) / 2;
		int yy = (HEIGHT - 8) / 2;
		int w = msg.length();
		int h = 1;

		screen.render(xx - 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
		screen.render(xx + w * 8, yy - 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
		screen.render(xx - 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
		screen.render(xx + w * 8, yy + 8, 0 + 13 * 32, Color.get(-1, 1, 5, 445), 3);
		for (int x = 0; x < w; x++) {
			screen.render(xx + x * 8, yy - 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
			screen.render(xx + x * 8, yy + 8, 1 + 13 * 32, Color.get(-1, 1, 5, 445), 2);
		}
		for (int y = 0; y < h; y++) {
			screen.render(xx - 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 0);
			screen.render(xx + w * 8, yy + y * 8, 2 + 13 * 32, Color.get(-1, 1, 5, 445), 1);
		}

		if ((tickCount / 20) % 2 == 0) {
			Font.draw(msg, screen, xx, yy, Color.get(5, 333, 333, 333));
		} else {
			Font.draw(msg, screen, xx, yy, Color.get(5, 555, 555, 555));
		}
	}

	public void scheduleLevelChange(int dir) {
		pendingLevelChange = dir;
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.setMinimumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setMaximumSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		game.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));

		JFrame frame = new JFrame(Game.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(game, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		ModLoader.addGame(game);

		UseMods.addMods();
		ModLoader.loadAllMods();
		game.start(false);
	}

	public void won() {
		wonTimer = 60 * 3;
		hasWon = true;
	}
}