// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level;

import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Trotuh;
import de.thejackimonster.ld22.story.dialog.NPC;
import de.thejackimonster.ld22.story.dialog.NPCManager;
import com.mojang.ld22.entity.Skeleton;
import com.mojang.ld22.entity.Creeper;
import com.mojang.ld22.entity.Zombie;
import com.mojang.ld22.entity.Slime;
import de.thejackimonster.ld22.options.OptionFile;
import java.util.Collections;
import com.mojang.ld22.screen.TitleMenu;
import java.util.Collection;
import com.mojang.ld22.gfx.Screen;
import de.thejackimonster.ld22.modloader.ModLoader;
import com.mojang.ld22.entity.AirWizard;
import com.mojang.ld22.level.tile.Tile;
import com.mojang.ld22.level.levelgen.LevelGen;
import java.util.ArrayList;
import com.mojang.ld22.entity.Player;
import java.util.Comparator;
import com.mojang.ld22.entity.Entity;
import java.util.List;
import com.mojang.ld22.Game;
import java.util.Random;
import java.io.Serializable;

public class Level implements Serializable
{
    public Random random;
    public int w;
    public int h;
    public Game game;
    public byte[] tiles;
    public byte[] data;
    public byte[] biomes;
    public int[][] fireTicks;
    public List<Entity>[] entitiesInTiles;
    public int grassSet;
    public int dirtSet;
    public int sandSet;
    public int grassColor;
    public int dirtColor;
    public int sandColor;
    public int depth;
    public int monsterDensity;
    public int tickcount;
    public List<Entity> entities;
    public Comparator<Entity> spriteSorter;
    public List<Entity> rowSprites;
    public Player player;
    
    public Level(final Game game, final int w, final int h, final int level, final Level parentLevel, final int mapsize, final int waterc, final int grassc, final int rockc, final int treec) {
        this.random = new Random();
        this.monsterDensity = 8;
        this.tickcount = 0;
        this.entities = new ArrayList<Entity>();
        this.spriteSorter = new Comparator<Entity>() {
            @Override
            public int compare(final Entity e0, final Entity e1) {
                if (e0 == null || e1 == null) {
                    return 0;
                }
                if (e1.y < e0.y) {
                    return 1;
                }
                if (e1.y > e0.y) {
                    return -1;
                }
                return 0;
            }
        };
        this.rowSprites = new ArrayList<Entity>();
        this.game = game;
        if (level < 0) {
            this.dirtSet = 222;
        }
        else if (level == 1) {
            this.dirtSet = 444;
        }
        else {
            this.grassSet = 141;
            this.dirtSet = 322;
            this.sandSet = 550;
        }
        if (level == -4) {
            this.dirtSet = 500;
        }
        this.depth = level;
        this.w = w;
        this.h = h;
        this.fireTicks = new int[w][h];
        this.grassColor = this.grassSet;
        this.dirtColor = this.dirtSet;
        this.sandColor = this.sandSet;
        byte[][] maps;
        if (level == 0) {
            final byte[][] biomeMap1 = LevelGen.createAndValidateTopMap(w, h, mapsize, waterc, grassc, rockc, treec);
            final byte[][] biomeMap2 = maps = new byte[][] { biomeMap1[0], biomeMap1[1] };
            this.biomes = biomeMap1[2];
        }
        else if (level < 0) {
            maps = LevelGen.createAndValidateUndergroundMap(w, h, -level);
            this.monsterDensity = 4;
        }
        else {
            maps = LevelGen.createAndValidateSkyMap(w, h);
            this.monsterDensity = 4;
        }
        if (this.biomes != null) {
            for (int i = 0; i < this.biomes.length; ++i) {}
        }
        this.tiles = maps[0];
        this.data = maps[1];
        int x = this.random.nextInt(w);
        int y = this.random.nextInt(w);
        for (int j = 0; j <= 1; ++j) {
            x = this.random.nextInt(w);
            y = this.random.nextInt(w);
            this.setTile(x, y, Tile.heartcontainerOre, 0);
        }
        if (level == -3) {
            x = w / 2;
            y = w / 2;
            this.setTile(x, y, Tile.bowOre, 0);
            this.setTile(x - 1, y, Tile.lava, 0);
            this.setTile(x + 1, y, Tile.lava, 0);
            this.setTile(x, y - 1, Tile.lava, 0);
            this.setTile(x, y + 1, Tile.lava, 0);
            this.setTile(x - 1, y - 1, Tile.lava, 0);
            this.setTile(x - 1, y + 1, Tile.lava, 0);
            this.setTile(x + 1, y - 1, Tile.lava, 0);
            this.setTile(x + 1, y + 1, Tile.lava, 0);
        }
        if (parentLevel != null) {
            for (y = 0; y < h; ++y) {
                for (x = 0; x < w; ++x) {
                    if (parentLevel.getTile(x, y) == Tile.stairsDown) {
                        this.setTile(x, y, Tile.stairsUp, 0);
                        if (level == 0) {
                            this.setTile(x - 1, y, Tile.hardRock, 0);
                            this.setTile(x + 1, y, Tile.hardRock, 0);
                            this.setTile(x, y - 1, Tile.hardRock, 0);
                            this.setTile(x, y + 1, Tile.hardRock, 0);
                            this.setTile(x - 1, y - 1, Tile.hardRock, 0);
                            this.setTile(x - 1, y + 1, Tile.hardRock, 0);
                            this.setTile(x + 1, y - 1, Tile.hardRock, 0);
                            this.setTile(x + 1, y + 1, Tile.hardRock, 0);
                        }
                        else {
                            this.setTile(x - 1, y, Tile.antimobfield, 0);
                            this.setTile(x + 1, y, Tile.antimobfield, 0);
                            this.setTile(x, y - 1, Tile.antimobfield, 0);
                            this.setTile(x, y + 1, Tile.antimobfield, 0);
                            this.setTile(x - 1, y - 1, Tile.antimobfield, 0);
                            this.setTile(x - 1, y + 1, Tile.antimobfield, 0);
                            this.setTile(x + 1, y - 1, Tile.antimobfield, 0);
                            this.setTile(x + 1, y + 1, Tile.antimobfield, 0);
                        }
                    }
                }
            }
        }
        this.entitiesInTiles = (List<Entity>[])new ArrayList[w * h];
        for (int j = 0; j < w * h; ++j) {
            this.entitiesInTiles[j] = new ArrayList<Entity>();
        }
        if (level == 1) {
            final AirWizard aw = new AirWizard();
            aw.x = w * 8;
            aw.y = h * 8;
            this.add(aw);
        }
        for (y = 0; y < h; ++y) {
            for (x = 0; x < w; ++x) {
                ModLoader.LevelGenerate(this, this.random, x, y);
            }
        }
    }
    
    public void renderBackground(final Screen screen, final int xScroll, final int yScroll) {
        final int xo = xScroll >> 4;
        final int yo = yScroll >> 4;
        final int w = screen.w + 15 >> 4;
        final int h = screen.h + 15 >> 4;
        screen.setOffset(xScroll, yScroll);
        for (int y = yo; y <= h + yo; ++y) {
            for (int x = xo; x <= w + xo; ++x) {
                this.getTile(x, y).render(screen, this, x, y);
            }
        }
        screen.setOffset(0, 0);
    }
    
    public void renderSprites(final Screen screen, final int xScroll, final int yScroll) {
        final int xo = xScroll >> 4;
        final int yo = yScroll >> 4;
        final int w = screen.w + 15 >> 4;
        final int h = screen.h + 15 >> 4;
        screen.setOffset(xScroll, yScroll);
        for (int y = yo; y <= h + yo; ++y) {
            for (int x = xo; x <= w + xo; ++x) {
                if (x >= 0 && y >= 0 && x < this.w) {
                    if (y < this.h) {
                        this.rowSprites.addAll(this.entitiesInTiles[x + y * this.w]);
                    }
                }
            }
            if (this.rowSprites.size() > 0) {
                this.sortAndRender(screen, this.rowSprites);
            }
            this.rowSprites.clear();
        }
        screen.setOffset(0, 0);
    }
    
    public void renderLight(final Screen screen, final int xScroll, final int yScroll, final int bonussight) {
        final int xo = xScroll >> 4;
        final int yo = yScroll >> 4;
        final int w = screen.w + 15 >> 4;
        final int h = screen.h + 15 >> 4;
        screen.setOffset(xScroll, yScroll);
        for (int r = 4, y = yo - r; y <= h + yo + r; ++y) {
            for (int x = xo - r; x <= w + xo + r; ++x) {
                if (x >= 0 && y >= 0 && x < this.w) {
                    if (y < this.h) {
                        final List<Entity> entities = this.entitiesInTiles[x + y * this.w];
                        for (int i = 0; i < entities.size(); ++i) {
                            final Entity e = entities.get(i);
                            int lr = e.getLightRadius();
                            if (e instanceof Player && bonussight > 0) {
                                lr += bonussight;
                            }
                            if (lr > 0) {
                                screen.renderLight(e.x - 1, e.y - 4, lr * 8);
                            }
                        }
                        final int lr2 = this.getTile(x, y).getLightRadius(this, x, y);
                        if (lr2 > 0) {
                            screen.renderLight(x * 16 + 8, y * 16 + 8, lr2 * 8);
                        }
                    }
                }
            }
        }
        screen.setOffset(0, 0);
    }
    
    public void sortAndRender(final Screen screen, final List<Entity> list) {
        if (!(this.game.menu instanceof TitleMenu)) {
            Collections.sort(list, this.spriteSorter);
            for (int i = 0; i < list.size(); ++i) {
                list.get(i).render(screen);
            }
        }
    }
    
    public Tile getTile(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h) {
            return Tile.rock;
        }
        return Tile.tiles[this.tiles[x + y * this.w]];
    }
    
    public void setTile(final int x, final int y, final Tile t, final int dataVal) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h || t == null) {
            return;
        }
        if (this.game != null) {
            if (this.game.mpstate == 2 && this.game.server != null) {
                this.game.server.updatetile(x, y);
            }
            if (this.game.mpstate == 1 && this.game.client != null) {
                this.game.mapupdated = true;
            }
        }
        this.tiles[x + y * this.w] = t.id;
        this.data[x + y * this.w] = (byte)dataVal;
    }
    
    public int getData(final int x, final int y) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h) {
            return 0;
        }
        return this.data[x + y * this.w] & 0xFF;
    }
    
    public void setData(final int x, final int y, final int val) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h) {
            return;
        }
        this.data[x + y * this.w] = (byte)val;
    }
    
    public void add(final Entity entity) {
        if (entity instanceof Player) {
            this.player = (Player)entity;
        }
        entity.removed = false;
        this.entities.add(entity);
        entity.init(this);
        this.insertEntity(entity.x >> 4, entity.y >> 4, entity);
    }
    
    public void remove(final Entity e) {
        this.entities.remove(e);
        final int xto = e.x >> 4;
        final int yto = e.y >> 4;
        this.removeEntity(xto, yto, e);
    }
    
    public void insertEntity(final int x, final int y, final Entity e) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h) {
            return;
        }
        this.entitiesInTiles[x + y * this.w].add(e);
    }
    
    public void removeEntity(final int x, final int y, final Entity e) {
        if (x < 0 || y < 0 || x >= this.w || y >= this.h) {
            return;
        }
        this.entitiesInTiles[x + y * this.w].remove(e);
    }
    
    public void trySpawn(int count) {
        count /= 2;
        count += count * OptionFile.difficulty;
        for (int i = 0; i < count; ++i) {
            Mob mob = null;
            int minLevel = 1;
            int maxLevel = 1;
            if (this.depth < 0) {
                maxLevel = -this.depth + 1;
            }
            if (this.depth > 0) {
                maxLevel = (minLevel = 4);
            }
            final int lvl = this.random.nextInt(maxLevel - minLevel + 1) + minLevel;
            final int c = this.random.nextInt(7);
            if (c == 0 || c == 1) {
                mob = new Slime(lvl);
            }
            else if (c == 2 || c == 3) {
                mob = new Zombie(lvl);
            }
            else if (c == 4) {
                mob = new Creeper(lvl);
            }
            else if (c == 5) {
                mob = new Skeleton(lvl);
            }
            else if (c == 6 && !this.game.firsttime) {
                for (int j = 0; j < NPCManager.npcList.size(); ++j) {
                    if (NPCManager.npcList.get(j).removed && mob == null) {
                        mob = NPCManager.npcList.get(j);
                        j = NPCManager.npcList.size();
                    }
                }
            }
            else {
                mob = new Slime(lvl);
            }
            if (this.depth == -4) {
                final int ra = this.random.nextInt(2);
                if (ra == 1) {
                    mob = new Trotuh(lvl);
                }
                else if (ra == 0) {
                    mob = new Skeleton(lvl);
                }
            }
            if (mob != null && mob.findStartPos(this)) {
                this.add(mob);
            }
        }
    }
    
    public boolean getRightBiome(final int x, final int y, final Mob mob) {
        if (x <= 0 || y <= 0) {
            return false;
        }
        final int xx = (int)(x / Math.sqrt(this.biomes.length));
        final int yy = (int)(y / Math.sqrt(this.biomes.length));
        final int xr = (int)(x % Math.sqrt(this.biomes.length));
        final int yr = (int)(y % Math.sqrt(this.biomes.length));
        if (xr >= 0 && yr >= 0 && xx < Math.sqrt(this.biomes.length) && yy < Math.sqrt(this.biomes.length)) {
            final byte bio = this.biomes[xx + yy * (int)Math.sqrt(this.biomes.length)];
            if (bio == 0) {
                return false;
            }
            if (bio == 1) {
                if (mob instanceof Slime) {
                    return true;
                }
                if (mob instanceof NPC) {
                    return true;
                }
            }
            if (bio == 2) {
                if (mob instanceof Slime) {
                    return true;
                }
                if (mob instanceof NPC) {
                    return true;
                }
            }
            if (bio == 3) {
                if (mob instanceof Skeleton) {
                    return true;
                }
                if (mob instanceof Creeper) {
                    return true;
                }
            }
            if (bio == 4) {
                return false;
            }
            if (bio == 5) {
                return false;
            }
            if (bio == 6) {
                if (mob instanceof Creeper) {
                    return true;
                }
                if (mob instanceof Zombie) {
                    return true;
                }
            }
            if (bio == 7) {
                if (mob instanceof Skeleton) {
                    return true;
                }
                if (mob instanceof Zombie) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void tick() {
        ++this.tickcount;
        if (this.tickcount % this.monsterDensity == 0 && this.game.lightlvl < 4) {
            this.trySpawn(1);
        }
        for (int i = 0; i < this.w * this.h / 50; ++i) {
            final int xt = this.random.nextInt(this.w);
            final int yt = this.random.nextInt(this.w);
            this.getTile(xt, yt).tick(this, xt, yt);
        }
        for (int i = 0; i < this.entities.size(); ++i) {
            if (this.entities.get(i) != null) {
                final Entity e = this.entities.get(i);
                final int xto = e.x >> 4;
                final int yto = e.y >> 4;
                e.tick();
                if (e.removed) {
                    this.entities.remove(i--);
                    this.removeEntity(xto, yto, e);
                }
                else {
                    final int xt2 = e.x >> 4;
                    final int yt2 = e.y >> 4;
                    if (xto != xt2 || yto != yt2) {
                        this.removeEntity(xto, yto, e);
                        this.insertEntity(xt2, yt2, e);
                    }
                }
            }
        }
    }
    
    public List<Entity> getEntities(final int x0, final int y0, final int x1, final int y1) {
        final List<Entity> result = new ArrayList<Entity>();
        final int xt0 = (x0 >> 4) - 1;
        final int yt0 = (y0 >> 4) - 1;
        final int xt2 = (x1 >> 4) + 1;
        for (int yt2 = (y1 >> 4) + 1, y2 = yt0; y2 <= yt2; ++y2) {
            for (int x2 = xt0; x2 <= xt2; ++x2) {
                if (x2 >= 0 && y2 >= 0 && x2 < this.w) {
                    if (y2 < this.h) {
                        final List<Entity> entities = this.entitiesInTiles[x2 + y2 * this.w];
                        for (int i = 0; i < entities.size(); ++i) {
                            if (entities.get(i) != null) {
                                final Entity e = entities.get(i);
                                if (e.intersects(x0, y0, x1, y1)) {
                                    result.add(e);
                                }
                            }
                        }
                    }
                }
            }
        }
        return result;
    }
}
