package de.thejackimonster.ld22.loadandsave;

import java.util.ArrayList;
import java.util.List;

import com.mojang.ld22.Game;
import com.mojang.ld22.entity.Chest;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.Player;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.level.Level;

import de.thejackimonster.ld22.file.SystemFile;
import de.thejackimonster.ld22.leveltree.Skill;

public class SaveLevel extends Thread {

	public static String path;
	public Game game;
	private static List<Chest> chests = new ArrayList<Chest>();

	public SaveLevel(String s,Game game) {
		SaveLevel.path = s;
		this.game = game;
	}

	public void run() {
		if(game.isApplet || game.mpstate==1) return;
		this.game.issavingmap = true;
		this.savePlayer(game.player);
		this.saveSkills();
		for(int i = 0; i < game.levels.length; i++) {
			this.setLevel(game, i);
		}
		this.game.issavingmap = false;
	}
	
	
	public void saveSkills() {
		if(game.isApplet) return;
		SystemFile file = new SystemFile(SaveLevel.path + "_skills", game);
		file.Create();
		file.Rewrite();
		for(int i = 0; i < Skill.skills.size(); i++) {
			file.WriteLn(String.valueOf(Skill.skills.get(i).getLevel()));
		} 
		file.Write();
		file.Close();
	}

	public void savePlayer(Player player) {
		if(game.isApplet) return;
		SystemFile file = new SystemFile(SaveLevel.path + "_player", game);
		file.Create();
		file.Rewrite();
		file.WriteLn(String.valueOf(player.x));
		file.WriteLn(String.valueOf(player.y));
		file.WriteLn(String.valueOf(player.game.respawnx));
		file.WriteLn(String.valueOf(player.game.respawny));
		file.WriteLn(String.valueOf(player.health));
		file.WriteLn(String.valueOf(player.stamina));
		file.WriteLn(String.valueOf(player.plevel));
		file.WriteLn(String.valueOf(player.exp));
		file.WriteLn(String.valueOf(player.game.lightlvl));
		file.WriteLn(String.valueOf(player.game.currentLevel));
		for(int i = 0; i < player.inventory.items.size(); i++) {
			if(player.inventory.items.get(i) != null) {
				if(player.inventory.count(player.inventory.items.get(i)) > 0 && player.inventory.items.get(i) instanceof ResourceItem) {
					for(int j = 0; j < player.inventory.count(player.inventory.items.get(i)); j++) {
						file.WriteLn(String.valueOf(Item.getIDOfName(player.inventory.items.get(i).getName())));
					}
				} else {
					file.WriteLn(String.valueOf(Item.getIDOfName(player.inventory.items.get(i).getName())));
				}
			}
		}
		file.Write();
		file.Close();
	}

	public void setLevel(Game game, int c) {
		if(game.isApplet) return;
		Level level = game.levels[c];
		SystemFile file = new SystemFile(SaveLevel.path + String.valueOf(c), game);
		file.Create();
		file.Rewrite();
		file.WriteLn(String.valueOf(level.w));
		file.WriteLn(String.valueOf(level.h));
		file.WriteLn(String.valueOf(level.depth));
		file.WriteLn(String.valueOf(level.depth + 4));
		for(int i = 0; i < level.w; i++) {
			for(int j = 0; j < level.h; j++) {
				file.WriteLn(String.valueOf(level.getTile(i, j).id));
			}
		}
		file.Write();
		file.Close();
		SystemFile file1 = new SystemFile(SaveLevel.path + String.valueOf(c) + "_chests", game);
		file1.Create();
		file1.Rewrite();
		int i1 = 0;
		file = new SystemFile(SaveLevel.path + String.valueOf(c) + "_entities", game);
		file.Create();
		file.Rewrite();
		for(int i = 0; i < level.entities.size(); i++) {
			if(level.entities.get(i) instanceof Furniture) {
				Furniture furniture = (Furniture) level.entities.get(i);
				file.WriteLn(String.valueOf(furniture.x));
				file.WriteLn(String.valueOf(furniture.y));
				file.WriteLn(furniture.name);
				if(level.entities.get(i) instanceof Chest) {
					Chest chest = (Chest) level.entities.get(i);
					file1.WriteLn(String.valueOf(i1));
					SystemFile file2 = new SystemFile(SaveLevel.path + String.valueOf(c) + "_chest" + String.valueOf(i1), game);
					file2.Create();
					file2.Rewrite();
					for(int j = 0; j < chest.inventory.items.size(); j++) {
						if(chest.inventory.items.get(j) != null) {
							if(chest.inventory.count(chest.inventory.items.get(j)) > 0 && chest.inventory.items.get(j) instanceof ResourceItem) {
								for(int k = 0; k < chest.inventory.count(chest.inventory.items.get(j)); k++) {
									file2.WriteLn(String.valueOf(Item.getIDOfName(chest.inventory.items.get(j).getName())));
								}
							} else {
								file2.WriteLn(String.valueOf(Item.getIDOfName(chest.inventory.items.get(j).getName())));
							}
						}
					}
					file2.Write();
					file2.Close();
					i1++;
				}
			}
		}
		file.Write();
		file.Close();
		file1.Write();
		file1.Close();
	}
}
