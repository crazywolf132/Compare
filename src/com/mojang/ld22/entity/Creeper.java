package com.mojang.ld22.entity;

import java.util.ArrayList;
import java.util.List;

import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Experience;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.resource.Resource;
import com.mojang.ld22.level.tile.StairsTile;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.level.tile.WaterTile;
import com.mojang.ld22.sound.Sound;

import de.thejackimonster.ld22.leveltree.Achievement;
import de.thejackimonster.ld22.options.OptionFile;

public class Creeper extends Mob {
	private int xa, ya;
	public int lvl;
    private final int range = 1;
	private int randomWalkTime = 0;
	private final int dmg = 3;
	private boolean attack = false;
	private int ticks = 0;

	public Creeper(int lvl) {
		super("Creeper");
		this.lvl = lvl;
		x = random.nextInt(64 * 16);
		y = random.nextInt(64 * 16);
		health = maxHealth = lvl * lvl * 10;
	}

	
	
	public void tick() {
		if (level.game.mpstate != 1) {
		super.tick();
		if(attack) {
			ticks++;
			if(ticks > 5) {
				attack = false;
			}
		}

		if (level.player != null && randomWalkTime == 0) {
			int xd = level.player.x - x;
			int yd = level.player.y - y;
			if (xd * xd + yd * yd < 50 * 50) {
				xa = 0;
				ya = 0;
				if (xd < 0) xa = -1;
				if (xd > 0) xa = +1;
				if (yd < 0) ya = -1;
				if (yd > 0) ya = +1;
			}
		}

		int speed = tickTime & 1;
		if (!move(xa * speed, ya * speed) || random.nextInt(200) == 0) {
			randomWalkTime = 60;
			xa = (random.nextInt(3) - 1) * random.nextInt(2);
			ya = (random.nextInt(3) - 1) * random.nextInt(2);
		}
		if (randomWalkTime > 0) randomWalkTime--;
		}
	}

	public void render(Screen screen) {
		int xt = 8;
		int yt = 16;

		int flip1 = (walkDist >> 3) & 1;
		int flip2 = (walkDist >> 3) & 1;

		if (dir == 1) {
			xt += 2;
		}
		if (dir > 1) {

			flip1 = 0;
			flip2 = ((walkDist >> 4) & 1);
			if (dir == 2) {
				flip1 = 1;
			}
			xt += 4 + ((walkDist >> 3) & 1) * 2;
		}

		int xo = x - 8;
		int yo = y - 11;

		int col = Color.get(-1, 240, 250, 50);

		if (attack) {
			col = Color.get(-1, 555, 555, 555);
		}

		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);
		screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
		screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
	}

	protected void touchedBy(Entity entity) {
		if (entity instanceof Player) {
			attack = true;
			if(ticks > 3) {
				explode(((Player)entity));
			}
		}
	}

	public void explode(Player player) {
		int r = range * 16;
		Sound.playerDeath.play();
		List<Entity> result = new ArrayList<Entity>();
		result = this.level.getEntities(this.x-r, this.y-r, this.x+r, this.y+r);
		for (int i3=0;i3<result.size();i3++) {
			if (result.get(i3) instanceof TNT) {
			   if(!((TNT)result.get(i3)).on) {
				   ((TNT)result.get(i3)).on = true;
				   ((TNT)result.get(i3)).explode(r);
			   }
			} else
		    if (result.get(i3) instanceof Mob) {
		       Mob playa = (Mob) result.get(i3);
		       if (result.get(i3) instanceof Player) {
			       player = (Player)result.get(i3);
			   }
		       if(player != null) {
		    	   result.get(i3).hurt(player, dmg, player.dir*OptionFile.difficulty);
		    	   if(player.health <= 0) {
		    		   Achievement.creeperNoob.Done(player.game);
		    	   }
		       } else {
		    	   result.get(i3).hurt(playa, dmg, playa.dir);
		       }
		   	
		    } else if(result.get(i3) instanceof Furniture) {
			    Furniture furniture = (Furniture) result.get(i3);
			    this.level.add(new ItemEntity(new FurnitureItem(furniture), furniture.x+random.nextInt(100)-50, furniture.y+random.nextInt(100)-50));
			    furniture.remove();
			}
		}
		for (int i=-(r/16);i<=(r/16);i++) {
			for (int i2=-(r/16);i2<=(r/16);i2++) {
				int a = i; int b = i2;
				if(a < 0) a = -a;
				if(b < 0) b = -b;
				int l = Math.round((float)Math.sqrt((a) + (b)));
				if(l+1 < r && level.depth < 1) {
					Tile tile = this.level.getTile(this.x/16+i, this.y/16+i2);
					if(tile != null && (!(tile instanceof StairsTile))) {
						tile.hurt(level, this.x/16+random.nextInt(8)-4, this.y/16+random.nextInt(8)-4, player, 100, 0);
					}
		        	if(random.nextInt(5)==0){
		        		this.level.setTile(this.x/16+i, this.y/16+i2, Tile.hole, 0); 
		        	    level.add(new ItemEntity(new ResourceItem(Resource.dirt), x+random.nextInt(100)-50, y+random.nextInt(100)-50));    		
		        	} else {
		        		if ((!(this.level.getTile(this.x/16+i, this.y/16+i2) instanceof WaterTile)) && (!(tile instanceof StairsTile))) { 
		        	      this.level.setTile(this.x/16+i, this.y/16+i2, Tile.dirt, 0);
		        		}
		        	}
				}
			}
		}
	    this.die();
	}
	

	public boolean canSwim() {
		return false;
	}	
	
	protected void die() {
		int count = random.nextInt(2) + 1;
		for (int i = 0; i < count; i++) {
			
			if (!this.isSwimming()) {
			 level.add(new ItemEntity(new ResourceItem(Resource.cloth), x + random.nextInt(11) - 5, y + random.nextInt(11) - 5));
			}
		}

		if (level.player != null && !this.isSwimming()) {
			level.player.score += 50 * lvl;
			level.add(new ItemEntity(new Experience(level.player, 10*lvl), x + random.nextInt(11) - 5, y + random.nextInt(11) - 5 ));
		}
		this.remove();

	}

}