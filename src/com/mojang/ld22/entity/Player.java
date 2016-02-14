package com.mojang.ld22.entity;

import java.util.List;

import com.mojang.ld22.Game;
import com.mojang.ld22.entity.Arrow;
import com.mojang.ld22.InputHandler;
import com.mojang.ld22.entity.particle.TextParticle;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.gfx.Font;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Enterhaken;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.Greifhaken;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.PowerGloveItem;
import com.mojang.ld22.item.ToolItem;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.screen.CheatMenu;
import com.mojang.ld22.screen.InventoryMenu;
import com.mojang.ld22.screen.LineMessage;
import com.mojang.ld22.sound.Sound;

import de.thejackimonster.ld22.leveltree.Achievement;
import de.thejackimonster.ld22.leveltree.Skill;
import de.thejackimonster.ld22.leveltree.SpecialSkill;
import de.thejackimonster.ld22.modloader.ModLoader;
import de.thejackimonster.ld22.options.OptionFile;
import de.thejackimonster.ld22.weapons.Boomerang;
import de.thejackimonster.ld22.weapons.BoomerangItem;

public class Player extends Mob {
	private InputHandler input;
	public int attackTime, attackDir;

	public Game game;
	public Inventory inventory = new Inventory();
	public Item Slots [] =  new Item[9]; 
	public boolean energy = false;
	public boolean unlimitedhealth = false;
	public int maxHealth = 10;
	public int plevel = 0;
	public int maxplevel = 20;
	public final int  expperlevel = 200;
	public int skillpoint = 0;
	public int exp = 0;
	public int sight = 2;
	public int color;
	public String username = "player";
	public boolean oneshotresources = false;
	public boolean nodarkness = false;
	public boolean oneshotmobs = false;
	public boolean allowrun = true;
	public boolean isrunning = false;

	
	public boolean ishooking = false;
	public boolean isgrabbing = false;
	public Item attackItem;
	public Item activeItem;
	public int stamina;
	public int staminaRecharge;
	public int staminaRechargeDelay;
	public int score;
	public int maxStamina = 10;
	private int onStairDelay;
	public int invulnerableTime = 0;
	public int tickmessage = 0;

	public Player(Game game, InputHandler input) {
		super("Player");
		if(!game.isApplet) OptionFile.readOpt();
		this.username = OptionFile.name;
		this.color = OptionFile.color;
		this.game = game;
		this.input = input;
		this.energy = false;
		this.unlimitedhealth = false;
		
		x = 24;
		y = 24;
		stamina = maxStamina;

		
		inventory.add(new FurnitureItem(new Workbench()));		
		inventory.add(new PowerGloveItem());
		

		/*inventory.add(Item.wooddoor);
		inventory.add(Item.heartcontainer);
		inventory.add(new ResourceItem(Resource.wood, 9));
		inventory.add(Item.chest);
		inventory.add(Item.torch);
		inventory.add(Item.woodfloor);
		inventory.add(Item.tnt);*/
		//inventory.add(new FurnitureItem(new Torch()));
		//inventory.add(new FurnitureItem(new TNT()));
		//inventory.add(new FurnitureItem(new TNT()));
		//inventory.add(new FurnitureItem(new TNT()));
		//inventory.add(new FurnitureItem(new TNT()));
		/*
		inventory.add(new Enterhaken());
		inventory.add(new Greifhaken());
		inventory.add(new VehicleItem(new Minecart()));
		inventory.add(new VehicleItem(new Boat()));
		*/
		//inventory.add(new ToolItem(ToolType.flintnsteel, 1));
		//inventory.add(new ResourceItem(Resource.glowstone));
		//this.plevel = 20;
		
	
//		inventory.add(new ResourceItem(Item.wooddoor));
		
		//inventory.add(new FurnitureItem(new TNT()));
		//inventory.add(new FurnitureItem(new TNT()));
		//inventory.add(negw FurnitureItem(new TNT()));
		
		//inventory.add(new ToolItem(ToolType.bow, 1));
	}
	
	public void tick() {
		super.tick();

		if(energy) {
			stamina = maxStamina;
		}

		if (invulnerableTime > 0) invulnerableTime--;
		Tile onTile = level.getTile(x >> 4, y >> 4);
		if (onTile == Tile.stairsDown || onTile == Tile.stairsUp) {
			if (onStairDelay == 0) {
				changeLevel((onTile == Tile.stairsUp) ? 1 : -1);
				onStairDelay = 10;
				return;
			}
			onStairDelay = 10;
		} else {
			if (onStairDelay > 0) onStairDelay--;
		}

		if (stamina <= 0 && staminaRechargeDelay == 0 && staminaRecharge == 0) {
			
			if (this.plevel >= 3) {
				staminaRechargeDelay = 30;	
			} else {
			    staminaRechargeDelay = 40;
			}
		}

		if (staminaRechargeDelay > 0) {
			staminaRechargeDelay--;
		}

		if (this.exp > expperlevel) {
			 Sound.lvlup.play();
	         this.exp = 0;
			 this.plevel++;
			 
             game.setMenu(new LineMessage(this, "Level Up!", 1));
			 
		}
		
		
		if (staminaRechargeDelay == 0) {
			staminaRecharge++;
			if (this.plevel >= 3 && staminaRecharge % 2 == 0) {
				staminaRecharge++;
			}
	
			
			if ((isSwimming() && !this.walkoverwater) || (this.isrunning)) {
				staminaRecharge = 0;
			}
			while (staminaRecharge > maxStamina) {
				staminaRecharge -= maxStamina;

				if (stamina < maxStamina) stamina++;
			}
		}

		int xa = 0;
		int ya = 0;
		if (input != null ) {
		
		if (input.up.down && !ishooking) ya--;
		if (input.down.down && !ishooking) ya++;
		if (input.left.down && !ishooking) xa--;
		if (input.right.down && !ishooking) xa++;
		
		if ( (input.up.down) || (input.down.down) ||  (input.left.down) || (input.right.down)) {
		 if (game.mpstate == 2) { 
	        	game.server.updateplayerpositions();
		 } else if (game.mpstate == 1) {
		  //  	game.client.positionupdated = true;
	   	 }
		}
		
		if (input.slot1.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			this.activeItem = Slots[0];
			this.inventory.items.remove(Slots[0]);
		}
		
		if (input.slot2.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[1] != null) {
			 if (this.Slots[1].isDepleted()) Slots[1] = null;
			 this.activeItem = Slots[1];
			 this.inventory.items.remove(Slots[1]);
			}
		}
		if (input.slot3.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[2] != null) {
				 if (this.Slots[2].isDepleted()) Slots[2] = null;
				 this.activeItem = Slots[2];
				 this.inventory.items.remove(Slots[2]);
			}
		}
		
		if (input.slot4.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[3] != null) {
				 if (this.Slots[3].isDepleted()) Slots[3] = null;
				 this.activeItem = Slots[3];
				 this.inventory.items.remove(Slots[3]);
			}}
		if (input.slot5.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[4] != null) {
				 if (this.Slots[4].isDepleted()) Slots[4] = null;
				 this.activeItem = Slots[4];
				 this.inventory.items.remove(Slots[4]);
			}
		}
		
		if (input.slot6.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[5] != null) {
				 if (this.Slots[5].isDepleted()) Slots[5] = null;
				 this.activeItem = Slots[5];
				 this.inventory.items.remove(Slots[5]);
			}
		}
		if (input.slot7.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[6] != null) {
				 if (this.Slots[6].isDepleted()) Slots[6] = null;
				 this.activeItem = Slots[6];
				 this.inventory.items.remove(Slots[6]);
			}
		}
		
		if (input.slot8.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[7] != null) {
				 if (this.Slots[7].isDepleted()) Slots[7] = null;
				 this.activeItem = Slots[7];
				 this.inventory.items.remove(Slots[7]);
			}
		}
		if (input.slot9.clicked) {
			if (this.activeItem != null) this.inventory.items.add(0, this.activeItem);
			if (Slots[8] != null) {
				 if (this.Slots[8].isDepleted()) Slots[8] = null;
				 this.activeItem = Slots[8];
				 this.inventory.items.remove(Slots[8]);
			}
		}
		
	    if (input.run.clicked && this.allowrun) {
		  if (this.isrunning) {
			  this.isrunning = false;
		  } else {
			  this.isrunning = true;
			  Sound.powerup.play();
		  }
	    }
	    
	    if (input.run.clicked && isgrabbing) {
	    	Sound.jump.play();
	    	((Greifhaken) activeItem).grfkend.ispushing = true;
	    }
		
	    if (this.isrunning && !ishooking) {
			if (input.up.down) ya--;
			if (input.down.down) ya++;
			if (input.left.down) xa--;
			if (input.right.down) xa++;	 
			
			
			if (!this.energy) {
				if (stamina > 0) {
					
					  if (!this.energy){
						  
					   if (tickTime % 30 == 0)	{  
						stamina--;
					   }
					  }	
						
					} else {
                      this.isrunning = false;
			 }
			}
	    }
		} else {
			isrunning = true;
		}
		if (isSwimming() && tickTime % 60 == 0) {
			if (stamina > 0) {
				
			  if (!this.energy && !this.walkoverwater){
				stamina--;
			  }	
				
			} else {
			 if(!this.walkoverwater) {
				hurt(this, 1, dir ^ 1);
			 }
			}
		}

		if (staminaRechargeDelay % 2 == 0) {
			move(xa, ya);
		}
if (input != null) {
		if (input.attack.clicked) {
			if (stamina == 0) {

			} else {
				
				if (!this.energy) {
				stamina--;
				}
				
				staminaRecharge = 0;
				attack();
			}
		}
		if (input.menu.clicked) {
			if (!use()) {
				game.setMenu(new InventoryMenu(this));
			}
		}
		
		if (input.cheatmenu.clicked){
		    if (!use()) {
				game.setMenu(new CheatMenu(this));
		    	//game.setMenu(new LineMessage(this,"Test",10));

			}
		}
}	
		if (attackTime > 0) attackTime--;
		
		if(activeItem instanceof ToolItem) {
			ToolType type = ((ToolItem) activeItem).type;
			if(type == ToolType.axe) Achievement.craftAxe.Done(game);
			if(type == ToolType.hammer) Achievement.craftHammer.Done(game);
			if(type == ToolType.hoe) Achievement.craftHoe.Done(game);
			if(type == ToolType.pickaxe) Achievement.craftPickaxe.Done(game);
			if(type == ToolType.shovel) Achievement.craftShovel.Done(game);
			if(type == ToolType.sword) Achievement.craftSword.Done(game);
		}
		
		ModLoader.tickPlayer(this);
	}

	private boolean use() {
		int yo = -2;
		if (dir == 0 && use(x - 8, y + 4 + yo, x + 8, y + 12 + yo)) return true;
		if (dir == 1 && use(x - 8, y - 12 + yo, x + 8, y - 4 + yo)) return true;
		if (dir == 3 && use(x + 4, y - 8 + yo, x + 12, y + 8 + yo)) return true;
		if (dir == 2 && use(x - 12, y - 8 + yo, x - 4, y + 8 + yo)) return true;

		int xt = x >> 4;
		int yt = (y + yo) >> 4;
		int r = 12;
		if (attackDir == 0) yt = (y + r + yo) >> 4;
		if (attackDir == 1) yt = (y - r + yo) >> 4;
		if (attackDir == 2) xt = (x - r) >> 4;
		if (attackDir == 3) xt = (x + r) >> 4;

		if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h) {
			if (level.getTile(xt, yt).use(level, xt, yt, this, attackDir)) return true;
		}

		for(int i = 0; i < Skill.skills.size(); i++) {
			if(Skill.skills.get(i).isCompleteDone()) {
				if(Skill.skills.get(i) instanceof SpecialSkill) {
					((SpecialSkill)Skill.skills.get(i)).playerUse(this);
				}
			}
		}

		return false;
	}

	public void attack() {
		walkDist += 8;
		attackDir = dir;
		attackItem = activeItem;
		boolean done = false;
		
		for(int i = 0; i < Skill.skills.size(); i++) {
			if(Skill.skills.get(i) instanceof SpecialSkill && Skill.skills.get(i).isCompleteDone()) {
				((SpecialSkill)Skill.skills.get(i)).playerUse(this);
			}
		}

		if ((attackItem instanceof BoomerangItem) && this.stamina-2>=0) {
			BoomerangItem boomer = (BoomerangItem)attackItem;
			if (this.stamina-2>=0) {
				if(!energy)stamina -= 2;
			    Sound.arrowshoot.play();
                switch (attackDir) {
                case 0:
                	level.add(new Boomerang(this,0,1,1));
                	break;
                case 1:
                	level.add(new Boomerang(this,0,-1,1));
                	break;
                case 2:
                	level.add(new Boomerang(this,-1,0,1));
                	break;
                case 3:
                	level.add(new Boomerang(this,1,0,1));
                	break;
                default:
                	break;
                }
                inventory.items.remove(boomer);
                boomer = null;
                attackItem = null;
                activeItem = null;
			}
			done = true;
		}


		if ( (attackItem instanceof ToolItem) && this.stamina-3>=0) {

			ToolItem tool = (ToolItem) attackItem;
			if (tool.type == ToolType.bow  && this.stamina-3>=0) {
				
				if(!energy)stamina -= 3;
			    Sound.arrowshoot.play(); 
                switch (attackDir) {
                case 0:
                	level.add(new Arrow(this,0,1,tool.level, done));
                	break;
                case 1:
                	level.add(new Arrow(this,0,-1,tool.level, done));
                	break;
                case 2:
                	level.add(new Arrow(this,-1,0,tool.level, done));
                	break;
                case 3:
                	level.add(new Arrow(this,1,0,tool.level, done));
                	break;
                default:
                	break;
                }
                
                
			done = true;
							
			}
			
		}
		
		if (activeItem instanceof Enterhaken && !ishooking) {
		    Sound.arrowshoot.play(); 
		    ishooking = true;
            switch (attackDir) {
            case 0:
            	level.add(new Enterhakending(this,0,1,1));
            	break;
            case 1:
            	level.add(new Enterhakending(this,0,-1,1));
            	break;
            case 2:
            	level.add(new Enterhakending(this,-1,0,1));
            	break;
            case 3:
            	level.add(new Enterhakending(this,1,0,1));
            	break;
            default:
            	break;
            }
		}    	
		
		
		if (activeItem instanceof Greifhaken && !isgrabbing) {
		    Sound.arrowshoot.play(); 
		    isgrabbing = true;
		    Greifhakending gak = null;
            switch (attackDir) {
            case 0:
                gak = new Greifhakending(this,0,1,1);
            	
            	level.add(gak);
            	break;
            case 1:
                gak = new Greifhakending(this,0,-1,1);
            	
            	level.add(gak);
            	break;
            case 2:
                gak = new Greifhakending(this,-1,0,1);
            	
            	level.add(gak);
            	break;
            case 3:
                gak = new Greifhakending(this,1,0,1);
            	
            	level.add(gak);
            	break;
            default:
            	break;
            }
            ((Greifhaken) activeItem).grfkend = gak; 
			 
	    }

		if (activeItem instanceof Greifhaken) {
			
		   if (this.isgrabbing &&  ((Greifhaken) activeItem).grfkend.stuck) {
			Sound.smallexplosion.play();
            ((Greifhaken) activeItem).grfkend.ispullingwood = true; 	
        	level.setTile(((Greifhaken) activeItem).grfkend.x/16, ((Greifhaken) activeItem).grfkend.y/16, Tile.dirt, 0);
		   }
		}		
		
		if (activeItem != null) {
			attackTime = 10;
			int yo = -2;
			int range = 12;
			if (dir == 0 && interact(x - 8, y + 4 + yo, x + 8, y + range + yo)) done = true;
			if (dir == 1 && interact(x - 8, y - range + yo, x + 8, y - 4 + yo)) done = true;
			if (dir == 3 && interact(x + 4, y - 8 + yo, x + range, y + 8 + yo)) done = true;
			if (dir == 2 && interact(x - range, y - 8 + yo, x - 4, y + 8 + yo)) done = true;
			if (done) return;

			int xt = x >> 4;
			int yt = (y + yo) >> 4;
			int r = 12;
			if (attackDir == 0) yt = (y + r + yo) >> 4;
			if (attackDir == 1) yt = (y - r + yo) >> 4;
			if (attackDir == 2) xt = (x - r) >> 4;
			if (attackDir == 3) xt = (x + r) >> 4;

			if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h) {
				if (activeItem.interactOn(level.getTile(xt, yt), level, xt, yt, this, attackDir)) {
					done = true;
				} else {
					if (level.getTile(xt, yt).interact(level, xt, yt, this, activeItem, attackDir)) {
						done = true;
					}
				}
				if (activeItem !=null) {
					if (activeItem.isDepleted()) {
						for (int i=0;i<9;i++) {
							if (Slots[i] != null ) {
								 if (activeItem.matches(Slots[i])) {
									Slots[i] = null; 
								 }
						     }
						}
					    inventory.items.remove(activeItem);
						
						activeItem = null;
						

					}
				}
			}
		}

		if (done) return;

		if ((activeItem == null || activeItem.canAttack()) ) {
			attackTime = 5;
			int yo = -2;
			int range = 20;
			
			if (dir == 0) hurt(x - 8, y + 4 + yo, x + 8, y + range + yo);
			if (dir == 1) hurt(x - 8, y - range + yo, x + 8, y - 4 + yo);
			if (dir == 3) hurt(x + 4, y - 8 + yo, x + range, y + 8 + yo);
			if (dir == 2) hurt(x - range, y - 8 + yo, x - 4, y + 8 + yo);

			int xt = x >> 4;
			int yt = (y + yo) >> 4;
			int r = 12;
			if (attackDir == 0) yt = (y + r + yo) >> 4;
			if (attackDir == 1) yt = (y - r + yo) >> 4;
			if (attackDir == 2) xt = (x - r) >> 4;
			if (attackDir == 3) xt = (x + r) >> 4;

			if (xt >= 0 && yt >= 0 && xt < level.w && yt < level.h) {
			//
			 if (this.oneshotresources) {
				level.getTile(xt, yt).hurt(level, xt, yt, this, 9999, attackDir);				 
			 }else {
				level.getTile(xt, yt).hurt(level, xt, yt, this, random.nextInt(3) + 1, attackDir);
			 }	
				
			}
		}

	}

	private boolean use(int x0, int y0, int x1, int y1) {
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e != this) if (e.use(this, attackDir)) return true;
		}
		return false;
	}

	private boolean interact(int x0, int y0, int x1, int y1) {
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e != this) if (e.interact(this, activeItem, attackDir)) return true;
		}
		return false;
	}

	public void hurt(int x0, int y0, int x1, int y1) {
		int lastdmg = 0;
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			lastdmg = getAttackDamage(e);
			if (e != this) e.hurt(this, lastdmg, attackDir);
		}
		
	/*	if (lastdmg > 0 && game.mpstate == 1) {
			
			game.atkxcoord[game.atkcount] = x0;
			game.atkycoord[game.atkcount] = y0;
			game.atkx2coord[game.atkcount] = x1;
			game.atky2coord[game.atkcount] = y1;
			game.atkdmg[game.atkcount] = lastdmg;
			game.atkcount++;
			game.atkrefresh = true;
		}		*/
	}

	public int getAttackDamage(Entity e) {
		int dmg;
		
		if (this.plevel == 0) {
			dmg = random.nextInt(3) + 1;
		} else {
			dmg = random.nextInt(4) + 2;
		}
		 
		if (attackItem != null) {
			dmg += attackItem.getAttackDamageBonus(e);
		}
		
		if (this.oneshotmobs) {
		 dmg = 9999;
		}

		for(int i = 0; i < Skill.skills.size(); i++) {
			if(Skill.skills.get(i).isCompleteDone()) {
				if(Skill.skills.get(i) instanceof SpecialSkill) {
					dmg += ((SpecialSkill)Skill.skills.get(i)).getAttack();
				}
			}
		}
		
		return dmg;
	}

	public void render(Screen screen) {
		int xt = 0;
		int yt = 14;
		
		
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
		if (isSwimming() && !this.walkoverwater) {
			yo += 4;
			int waterColor = Color.get(-1, -1, 115, 335);
			if (tickTime / 8 % 2 == 0) {
				waterColor = Color.get(-1, 335, 5, 115);
			}
			screen.render(xo + 0, yo + 3, 5 + 13 * 32, waterColor, 0);
			screen.render(xo + 8, yo + 3, 5 + 13 * 32, waterColor, 1);
		}

		if(this.isrunning && !isSwimming()) {
			int runColor = Color.get(-1, 355, -1, -1);
			if (tickTime / 8 % 2 == 0) {
				runColor = Color.get(-1, 335, -1, -1);
			}
			screen.render(xo + 0, yo + 8, 5 + 13 * 32, runColor, 0);
			screen.render(xo + 8, yo + 8, 5 + 13 * 32, runColor, 1);
			
		}
		
		if (attackTime > 0 && attackDir == 1) {
			screen.render(xo + 0, yo - 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 0);
			screen.render(xo + 8, yo - 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 1);
			if (attackItem != null) {
				attackItem.renderIcon(screen, xo + 4, yo - 4);
			}
		}
		int col = Color.get(-1, 100, 220, 532);
		if(this.color != col) {
			col = this.color;
		}
		if (hurtTime > 0) {
			col = Color.get(-1, 555, 555, 555);
		}

		if (activeItem instanceof FurnitureItem) {
			yt += 2;
		}
		Font.draw(username, screen, xo + 8 - (username.length()/2)*8, yo - 8, Color.get(-1, -1, -1, 555));
		
		screen.render(xo + 8 * flip1, yo + 0, xt + yt * 32, col, flip1);
		screen.render(xo + 8 - 8 * flip1, yo + 0, xt + 1 + yt * 32, col, flip1);
		
		if (!isSwimming() || this.walkoverwater) {
			screen.render(xo + 8 * flip2, yo + 8, xt + (yt + 1) * 32, col, flip2);
			screen.render(xo + 8 - 8 * flip2, yo + 8, xt + 1 + (yt + 1) * 32, col, flip2);
		}

		if (attackTime > 0 && attackDir == 2) {
			screen.render(xo - 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 1);
			screen.render(xo - 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 3);
			if (attackItem != null) {
				attackItem.renderIcon(screen, xo - 4, yo + 4);
			}
		}
		if (attackTime > 0 && attackDir == 3) {
			screen.render(xo + 8 + 4, yo, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 0);
			screen.render(xo + 8 + 4, yo + 8, 7 + 13 * 32, Color.get(-1, 555, 555, 555), 2);
			if (attackItem != null) {
				attackItem.renderIcon(screen, xo + 8 + 4, yo + 4);
			}
		}
		if (attackTime > 0 && attackDir == 0) {
			screen.render(xo + 0, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 2);
			screen.render(xo + 8, yo + 8 + 4, 6 + 13 * 32, Color.get(-1, 555, 555, 555), 3);
			if (attackItem != null) {
				attackItem.renderIcon(screen, xo + 4, yo + 8 + 4);
			}
		}

		if (activeItem instanceof FurnitureItem) {
			Furniture furniture = ((FurnitureItem) activeItem).furniture;
			furniture.x = x;
			furniture.y = yo;
			furniture.render(screen);
			if (((Player) this).game.mpstate == 2) {
				 ((Player) this).game.server.updatefurnature();
			}
		}
	}

	public void touchItem(ItemEntity itemEntity) {
		itemEntity.take(this);
		inventory.add(itemEntity.item);
		ModLoader.PickupItem(this, itemEntity.item);
	}

	public boolean canSwim() {
		return true;
	}

	public boolean findStartPos(Level level) {
		while (true) {
			int x = random.nextInt(level.w);
			int y = random.nextInt(level.h);
			if (level.getTile(x, y) == Tile.grass) {
				this.x = x * 16 + 8;
				this.y = y * 16 + 8;
				this.game.respawnx = this.x;
				this.game.respawny = this.y;
				return true;
			}
		}
	}

	public boolean payStamina(int cost) {
		if (cost > stamina) return false;
		 if (!this.energy) {
		  stamina -= cost;
		 }
		return true;
	}

	public void changeLevel(int dir) {
		game.scheduleLevelChange(dir);
	}

	public int getLightRadius() {
		int r = this.sight;
		if (activeItem != null) {
			if (activeItem instanceof FurnitureItem) {
				int rr = ((FurnitureItem) activeItem).furniture.getLightRadius();
				if (rr > r) r = rr;
			}
		}
		return r;
	}

	protected void die() {
		super.die();
		
		if (!(this instanceof MultiPlayer)) {
		 Sound.playerDeath.play();
		}
	}

	protected void touchedBy(Entity entity) {
		if (!(entity instanceof Player)) {
			entity.touchedBy(this);
		}
	}
	
	public void heal(int heal) {
		if (hurtTime > 0) return;

		level.add(new TextParticle("" + heal, x, y, Color.get(-1, 50, 50, 50)));
		health += heal;
		
		if (health > maxHealth) health = maxHealth;
	}


	protected void doHurt(int damage, int attackDir) {
		if (hurtTime > 0 || invulnerableTime > 0) return;
        if (this.game.mpstate != 1) {
		Sound.playerHurt.play();
		level.add(new TextParticle("" + damage, x, y, Color.get(-1, 504, 504, 504)));
		
		if (level.game.mpstate == 2) {
	     	this.level.game.server.sendTextParticle(x, y, Color.get(-1, 504, 504, 504), damage);
		}
		
		if (this.unlimitedhealth == false) {
		health -= damage;
		}
		
		if (!this.nonockback) {
		 if (attackDir == 0) yKnockback = +6;
		 if (attackDir == 1) yKnockback = -6;
		 if (attackDir == 2) xKnockback = -6;
		 if (attackDir == 3) xKnockback = +6;
		}
		hurtTime = 10;
		invulnerableTime = 30;
        }
	}
	
	public void hurt(int x0, int y0, int x1, int y1,int dmg) {
		List<Entity> entities = level.getEntities(x0, y0, x1, y1);
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (e != this) e.hurt(this, dmg, attackDir);
		}
	}
	
	public void gameWon() {
		level.player.invulnerableTime = 60 * 5;
		game.won();
	}
}