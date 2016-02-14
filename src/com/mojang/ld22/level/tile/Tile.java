// 
// Decompiled by Procyon v0.5.30
// 

package com.mojang.ld22.level.tile;

import com.mojang.ld22.entity.Player;
import de.thejackimonster.ld22.story.dialog.NPC;
import com.mojang.ld22.entity.Mob;
import com.mojang.ld22.entity.Entity;
import com.mojang.ld22.gfx.Color;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.item.HeartContainer;
import com.mojang.ld22.item.ToolType;
import com.mojang.ld22.item.resource.Resource;
import java.util.Random;

public class Tile
{
    public static int tickCount;
    protected Random random;
    public static Tile[] tiles;
    public static Tile grass;
    public static Tile rock;
    public static Tile woodplanktile;
    public static Tile water;
    public static Tile flower;
    public static Tile tree;
    public static Tile dirt;
    public static Tile sand;
    public static Tile woodfloor;
    public static Tile hole;
    public static Tile cactus;
    public static Tile cactusSapling;
    public static Tile woodendoor;
    public static Tile farmland;
    public static Tile wheat;
    public static Tile lava;
    public static Tile stoneplank;
    public static Tile carpet;
    public static Tile glowstone;
    public static Tile cloud;
    public static Tile treeSapling;
    public static Tile ironOre;
    public static Tile goldOre;
    public static Tile gemOre;
    public static Tile cloudCactus;
    public static Tile hardRock;
    public static Tile presentOre;
    public static Tile bowOre;
    public static Tile heartcontainerOre;
    public static Tile antimobfield;
    public static Tile stairsDown;
    public static Tile stairsUp;
    public static Tile infiniteFall;
    public static Tile glowstoneOre;
    public final byte id;
    public int breakDown;
    public boolean connectsToGrass;
    public boolean connectsToSand;
    public boolean connectsToLava;
    public boolean connectsToWater;
    
    static {
        Tile.tickCount = 0;
        Tile.tiles = new Tile[256];
        Tile.grass = new GrassTile(0);
        Tile.rock = new RockTile(1);
        Tile.woodplanktile = new WoodplankTile(2);
        Tile.water = new WaterTile(3);
        Tile.flower = new FlowerTile(4);
        Tile.tree = new TreeTile(5);
        Tile.dirt = new DirtTile(6);
        Tile.sand = new SandTile(7);
        Tile.woodfloor = new Woodfloor(8);
        Tile.hole = new HoleTile(9);
        Tile.cactus = new CactusTile(10);
        Tile.cactusSapling = new SaplingTile(25, Tile.sand, Tile.cactus);
        Tile.woodendoor = new Woodendoor(11);
        Tile.farmland = new FarmTile(12);
        Tile.wheat = new WheatTile(13);
        Tile.lava = new LavaTile(14);
        Tile.stoneplank = new BrickwallTile(15);
        Tile.carpet = new CarpetTile(16);
        Tile.glowstone = new GlowStoneTile(17);
        Tile.cloud = new CloudTile(18);
        Tile.treeSapling = new SaplingTile(19, Tile.grass, Tile.tree);
        Tile.ironOre = new OreTile(20, Resource.ironOre, 0);
        Tile.goldOre = new OreTile(21, Resource.goldOre, 0);
        Tile.gemOre = new OreTile(22, Resource.gem, 0);
        Tile.cloudCactus = new CloudCactusTile(23);
        Tile.hardRock = new HardRockTile(24);
        Tile.presentOre = new OreTile(26, Resource.apple, 10);
        Tile.bowOre = new ItemgiftTile(27, ToolType.bow, 4);
        Tile.heartcontainerOre = new FreeitemgiftTile(28, new HeartContainer(), 1);
        Tile.antimobfield = new AntiMob(29);
        Tile.stairsDown = new StairsTile(30, false);
        Tile.stairsUp = new StairsTile(31, true);
        Tile.infiniteFall = new InfiniteFallTile(32);
        Tile.glowstoneOre = new OreTile(33, Resource.glowstoneOre, 1);
    }
    
    public Tile(final int id) {
        this.random = new Random();
        this.breakDown = 0;
        this.connectsToGrass = false;
        this.connectsToSand = false;
        this.connectsToLava = false;
        this.connectsToWater = false;
        this.id = (byte)id;
        if (Tile.tiles[id] != null) {
            throw new RuntimeException("Duplicate tile ids!");
        }
        Tile.tiles[id] = this;
    }
    
    public void render(final Screen screen, final Level level, final int x, final int y) {
        if (level.fireTicks[x][y] > 0 && this.canBurn()) {
            int xp = 0;
            final int yp = 832;
            if (level.game.menu == null) {
                xp = this.random.nextInt(5) * 2;
            }
            screen.render(x * 16, y * 16, xp + yp, Color.get(-1, 500, 520, 550), 0);
            screen.render(x * 16 + 8, y * 16, xp + yp + 1, Color.get(-1, 500, 520, 550), 0);
            screen.render(x * 16, y * 16 + 8, xp + yp + 32, Color.get(-1, 500, 520, 550), 0);
            screen.render(x * 16 + 8, y * 16 + 8, xp + yp + 32 + 1, Color.get(-1, 500, 520, 550), 0);
        }
    }
    
    public void renderDmg(final Screen screen, final Level level, final int x, final int y, final int dmg1, final int dmg2, final int dmg3) {
        int h = 0;
        final int col = Color.get(-1, 111, 111, 111);
        this.breakDown = dmg3;
        if (level.getData(x, y) > dmg3) {
            h = 4;
            screen.render(x * 16, y * 16, 640 + h, col, 0);
            screen.render(x * 16 + 8, y * 16, 641 + h, col, 0);
            screen.render(x * 16, y * 16 + 8, 672 + h, col, 0);
            screen.render(x * 16 + 8, y * 16 + 8, 673 + h, col, 0);
        }
        else if (level.getData(x, y) > dmg2) {
            h = 2;
            screen.render(x * 16, y * 16, 640 + h, col, 0);
            screen.render(x * 16 + 8, y * 16, 641 + h, col, 0);
            screen.render(x * 16, y * 16 + 8, 672 + h, col, 0);
            screen.render(x * 16 + 8, y * 16 + 8, 673 + h, col, 0);
        }
        else if (level.getData(x, y) > dmg1) {
            h = 0;
            screen.render(x * 16, y * 16, 640 + h, col, 0);
            screen.render(x * 16 + 8, y * 16, 641 + h, col, 0);
            screen.render(x * 16, y * 16 + 8, 672 + h, col, 0);
            screen.render(x * 16 + 8, y * 16 + 8, 673 + h, col, 0);
        }
    }
    
    public boolean mayPass(final Level level, final int x, final int y, final Entity e) {
        return true;
    }
    
    public int getLightRadius(final Level level, final int x, final int y) {
        if (level.fireTicks[x][y] > 0 && this.canBurn()) {
            return this.random.nextInt(3) + 2;
        }
        return 0;
    }
    
    public void hurt(final Level level, final int x, final int y, final Mob source, final int dmg, final int attackDir) {
    }
    
    public void bumpedInto(final Level level, final int xt, final int yt, final Entity entity) {
        if (xt >= 0 && xt < level.fireTicks.length && yt >= 0 && yt < level.fireTicks[xt].length && level.fireTicks[xt][yt] > 0 && this.canBurn() && entity instanceof Mob && !(entity instanceof NPC)) {
            ((Mob)entity).hurt(this, xt, yt, 1);
        }
    }
    
    public void tick(final Level level, final int xt, final int yt) {
        if (level.fireTicks[xt][yt] > 0) {
            final int[] array = level.fireTicks[xt];
            --array[yt];
            final int val = level.getData(xt, yt);
            if (val > this.breakDown + this.breakDown / 4) {
                level.setTile(xt, yt, Tile.dirt, 0);
                level.fireTicks[xt][yt] = 0;
            }
            else {
                level.setData(xt, yt, val + 4);
            }
            if (level.fireTicks[xt][yt] % 5 == 0) {
                switch (this.random.nextInt(4)) {
                    case 0: {
                        if (xt - 1 >= 0) {
                            level.fireTicks[xt - 1][yt] = level.fireTicks[xt][yt];
                            break;
                        }
                        break;
                    }
                    case 1: {
                        if (xt + 1 < level.w) {
                            level.fireTicks[xt + 1][yt] = level.fireTicks[xt][yt];
                            break;
                        }
                        break;
                    }
                    case 2: {
                        if (yt - 1 >= 0) {
                            level.fireTicks[xt][yt - 1] = level.fireTicks[xt][yt];
                            break;
                        }
                        break;
                    }
                    case 3: {
                        if (yt + 1 < level.h) {
                            level.fireTicks[xt][yt + 1] = level.fireTicks[xt][yt];
                            break;
                        }
                        break;
                    }
                }
            }
        }
        else if (level.fireTicks[xt][yt] < 0 || !this.canBurn()) {
            level.fireTicks[xt][yt] = 0;
        }
    }
    
    public boolean canBurn() {
        return false;
    }
    
    public void steppedOn(final Level level, final int xt, final int yt, final Entity entity) {
    }
    
    public boolean interact(final Level level, final int xt, final int yt, final Player player, final Item item, final int attackDir) {
        return false;
    }
    
    public boolean use(final Level level, final int xt, final int yt, final Player player, final int attackDir) {
        return false;
    }
    
    public boolean connectsToLiquid() {
        return this.connectsToWater || this.connectsToLava;
    }
}
