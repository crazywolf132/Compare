package de.thejackimonster.ld22.story.dialog;

import java.util.ArrayList;
import java.util.List;

import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.level.tile.Tile;

public class NPC extends Mob {

	private int color;
	private int xp, yp;
	public String npcName;
	public boolean canWalk;
	private int xa, ya;
	public DialogMenu dialog;
	private boolean following;
	private Player follows;

	public NPC(String npc, boolean flag, int col, DialogMenu dm) {
		super("NPC");
		this.removed = true;
		this.npcName = npc;
		this.xp = 0;
		this.yp = 14;
		this.color = col;
		this.canWalk = flag;
		this.dialog = dm;
	}

	public NPC(String npc, boolean flag, int xt, int yt, int col, DialogMenu dm) {
		super("NPC");
		this.removed = true;
		this.npcName = npc;
		this.xp = xt;
		this.yp = yt;
		this.color = col;
		this.canWalk = flag;
		this.dialog = dm;
	}

	public void tick() {
		super.tick();
		int speed = tickTime & 1;
		
		if(canWalk) {
			if(following && follows != null) {
				int range = (((follows.x/16-this.x/16)*(follows.x/16-this.x/16))+((follows.y/16-this.y/16)*(follows.y/16-this.y/16)));
				List<Entity> ents = level.getEntities(follows.x - 32, follows.y - 32, follows.x + 32, follows.y + 32);
				List<Mob> mobs = new ArrayList<Mob>();
				
				if(ents.size() > 0) {
					for(int i = 0; i < ents.size(); i++) {
						if(ents.get(i) instanceof Mob && (!(ents.get(i) instanceof Player)) && (!(ents.get(i) instanceof NPC))) {
							if(!mobs.contains(((Mob)ents.get(i)))) {
								mobs.add(((Mob)ents.get(i)));
							}
						}
					}
				}
				
				if(range > 6*6) {
					for(int i = -1; i < 2; i++) {
						for(int j = -1; j < 2; j++) {
							if(i == j && j == 0 && i == 0) {
							} else {
								if(level.getTile(i+follows.x/16, j+follows.y/16).mayPass(level, i+follows.x/16, j+follows.y/16, this)) {
									this.x = follows.x + i*16;
									this.y = follows.y + j*16;
								}
							}
						}
					}
				}
				
				int xc = follows.x;
				int yc = follows.y;
				if(mobs.size() > 0) {
					xc = mobs.get(0).x;
					yc = mobs.get(0).y;
					if(mobs.get(0).removed || mobs.get(0).health <= 0) {
						mobs.remove(0);
					} else {
						int range1 = (((xc/16-this.x/16)*(xc/16-this.x/16))+((yc/16-this.y/16)*(yc/16-this.y/16)));
						if(range1 <= 1*1) {
							mobs.get(0).hurt(follows, 1, this.dir);
						}
					}
				}
				if(xc > this.x) xa = 1;
				else if(xc < this.x) xa = -1;
				else xa = 0;
				
				if(yc > this.y) ya = 1;
				else if(yc < this.y) ya = -1;
				else ya = 0;
				if(xc == follows.x && yc == follows.y) {
					if(range <= 1*1) {
						xa = 0;
						ya = 0;
					}
				}
			}
			if ((!move(xa * speed, ya * speed) || random.nextInt(200) == 0)) {
				xa = (random.nextInt(3) - 1) * random.nextInt(2);
				ya = (random.nextInt(3) - 1) * random.nextInt(2);
			}
		}
	}

	public void follow(Player player, boolean flag) {
		following = flag;
		if(flag) {
			follows = player;
		} else {
			follows = null;
		}
	}

	public boolean canSwim() {
		return false;
	}

	public void hurt(Mob mob, int damage, int attackDir) {
		if(mob instanceof Player) {
			talk((Player)mob);
		}
	}

	public void hurt(Tile tile, int x, int y, int damage) {
	}

	public void talk(Player player) {
		player.game.setMenu(this.dialog);
	}

	public void render(Screen screen) {
		int xt = xp;
		int yt = yp;

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

		int col = color;

		Font.draw(this.npcName, screen, xo-this.npcName.length()/2, yo-8, Color.get(-1, -1, -1, 555));
		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);
		screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
		screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
	}

	protected void die() {
	}

	public int getLightRadius() {
		return 1;
	}

}
