// 
// Decompiled by Procyon v0.5.30
// 

package de.thejackimonster.ld22.loadandsave;

import com.mojang.ld22.entity.Chest;
import com.mojang.ld22.level.tile.Tile;
import de.thejackimonster.ld22.matrix.Matrix;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.entity.Furniture;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.item.FurnitureItem;
import com.mojang.ld22.item.ResourceItem;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.entity.Inventory;
import de.thejackimonster.ld22.leveltree.Skill;
import com.mojang.ld22.screen.Menu;
import de.thejackimonster.ld22.file.SystemFile;
import com.mojang.ld22.sound.Sound;
import com.mojang.ld22.Game;

public class LoadLevel extends Thread
{
    public static String path;
    public Game game;
    
    public LoadLevel(final String s, final Game game) {
        LoadLevel.path = s;
        this.game = game;
    }
    
    @Override
    public void run() {
        if (this.game.isApplet) {
            return;
        }
        this.game.isloadingmap = true;
        Sound.test.play();
        final SystemFile file = new SystemFile("world", this.game);
        file.Create();
        final boolean flag = file.isNew;
        file.Close();
        if (!flag && this.game != null) {
            final int d = this.game.player.level.depth;
            this.loadPlayer(this.game);
            this.loadSkills();
            for (int i = 0; i < this.game.levels.length; ++i) {
                this.game.levels[i] = this.getLevel(this.game, i);
            }
            this.game.changeLevel(0);
        }
        else if (flag) {}
        this.game.setMenu(null);
        this.game.isloadingmap = false;
    }
    
    public void loadSkills() {
        if (this.game.isApplet) {
            return;
        }
        final SystemFile file = new SystemFile(String.valueOf(LoadLevel.path) + "_skills", this.game);
        file.Create();
        file.Reset();
        int i = 0;
        while (!file.EndOfFile()) {
            final int x = Integer.parseInt(file.ReadLn());
            if (i < Skill.skills.size() && x > 0) {
                Skill.skills.get(i).Done(Skill.skills.get(i).needPoints());
                while (Skill.skills.get(i).getLevel() < x && !Skill.skills.get(i).isCompleteDone()) {
                    Skill.skills.get(i).LevelUp();
                }
            }
            ++i;
        }
        file.Close();
    }
    
    public void loadPlayer(final Game game) {
        if (game.isApplet) {
            return;
        }
        game.player.inventory.items.clear();
        game.player.inventory = new Inventory();
        final SystemFile file = new SystemFile(String.valueOf(LoadLevel.path) + "_player", game);
        file.Create();
        file.Reset();
        game.player.x = Integer.parseInt(file.ReadLn());
        game.player.y = Integer.parseInt(file.ReadLn());
        game.respawnx = Integer.parseInt(file.ReadLn());
        game.respawny = Integer.parseInt(file.ReadLn());
        game.player.health = Integer.parseInt(file.ReadLn());
        game.player.stamina = Integer.parseInt(file.ReadLn());
        game.player.plevel = Integer.parseInt(file.ReadLn());
        game.player.exp = Integer.parseInt(file.ReadLn());
        game.lightlvl = Integer.parseInt(file.ReadLn());
        game.currentLevel = Integer.parseInt(file.ReadLn());
        while (!file.EndOfFile()) {
            final Item item = Item.items.get(Integer.parseInt(file.ReadLn()));
            if (item instanceof ResourceItem) {
                final ResourceItem resItem = new ResourceItem(((ResourceItem)item).resource);
                game.player.inventory.add(resItem);
            }
            else if (item instanceof FurnitureItem) {
                FurnitureItem furItem = null;
                Entity ent = null;
                try {
                    ent = (Entity)Entity.entities.get(Entity.getIDOfName(item.getName())).getClass().newInstance();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                if (ent != null) {
                    if (ent instanceof Furniture) {
                        final Furniture fur = (Furniture)ent;
                        furItem = new FurnitureItem(fur);
                    }
                }
                else {
                    furItem = (FurnitureItem)item;
                }
                if (furItem == null) {
                    continue;
                }
                game.player.inventory.add(furItem);
            }
            else {
                game.player.inventory.add(item);
            }
        }
        file.Close();
    }
    
    public Level getLevel(final Game game, final int c) {
        if (game.isApplet) {
            return null;
        }
        SystemFile file = new SystemFile(String.valueOf(LoadLevel.path) + String.valueOf(c), game);
        file.Create();
        file.Reset();
        final int w = Integer.parseInt(file.ReadLn());
        final int h = Integer.parseInt(file.ReadLn());
        final int l = Integer.parseInt(file.ReadLn());
        final int p = Integer.parseInt(file.ReadLn());
        Level level = null;
        if (p == 5) {
            level = new Matrix(false, game, w, h, l, null, w / 64, 2, 2, 2, 2);
        }
        else {
            level = new Matrix(false, game, w, h, l, game.player.game.levels[p], w / 64, 2, 2, 2, 2);
        }
        for (int i = 0; i < w; ++i) {
            for (int j = 0; j < h; ++j) {
                level.setTile(i, j, Tile.tiles[Integer.parseInt(file.ReadLn())], 0);
            }
        }
        file.Close();
        final SystemFile file2 = new SystemFile(String.valueOf(LoadLevel.path) + String.valueOf(c) + "_chests", game);
        file2.Create();
        file2.Reset();
        file = new SystemFile(String.valueOf(LoadLevel.path) + String.valueOf(c) + "_entities", game);
        file.Create();
        file.Reset();
        while (!file.EndOfFile()) {
            final int x = Integer.parseInt(file.ReadLn());
            final int y = Integer.parseInt(file.ReadLn());
            final int type = Entity.getIDOfName(file.ReadLn());
            if (Entity.entities.get(type) instanceof Furniture) {
                try {
                    final Entity entity = (Entity)Entity.entities.get(type).getClass().newInstance();
                    entity.x = x;
                    entity.y = y;
                    level.add(entity);
                    if (!(entity instanceof Chest)) {
                        continue;
                    }
                    final Chest chest = (Chest)entity;
                    final SystemFile file3 = new SystemFile(String.valueOf(LoadLevel.path) + String.valueOf(c) + "_chest" + file2.ReadLn(), game);
                    file3.Create();
                    file3.Reset();
                    while (!file3.EndOfFile()) {
                        final Item item = Item.items.get(Integer.parseInt(file3.ReadLn()));
                        if (item instanceof ResourceItem) {
                            final ResourceItem resItem = new ResourceItem(((ResourceItem)item).resource);
                            chest.inventory.add(resItem);
                        }
                        else if (item instanceof FurnitureItem) {
                            FurnitureItem furItem = null;
                            Entity ent = null;
                            try {
                                ent = (Entity)Entity.entities.get(Entity.getIDOfName(item.getName())).getClass().newInstance();
                            }
                            catch (Exception e) {
                                e.printStackTrace();
                            }
                            if (ent != null) {
                                if (ent instanceof Furniture) {
                                    final Furniture fur = (Furniture)ent;
                                    furItem = new FurnitureItem(fur);
                                }
                            }
                            else {
                                furItem = (FurnitureItem)item;
                            }
                            if (furItem == null) {
                                continue;
                            }
                            game.player.inventory.add(furItem);
                        }
                        else {
                            chest.inventory.add(item);
                        }
                    }
                    file3.Close();
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
        }
        file.Close();
        file2.Close();
        return level;
    }
}
